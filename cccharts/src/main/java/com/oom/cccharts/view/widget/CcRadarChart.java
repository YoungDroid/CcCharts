package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oom.cccharts.model.CcRadarChartData;
import com.oom.cccharts.model.CcRadarChartDataSet;
import com.oom.cccharts.model.CcRadarChartLabel;
import com.oom.cccharts.model.CcRadarChartLabel.CcStyle;
import com.oom.cccharts.model.CcRadarChartLabelEntity;

public class CcRadarChart extends View {

    private Paint mPaint;

    private float radiusBig;
    private float centerX;
    private float centerY;

    private float mTextSize = 30;
    private Rect mTextBounds;
    private String mText = "You need some useful data.";

    private OnLabelClickListener listener;
    private CcRadarChartDataSet dataSet;

    private CcRadarChartLabel label;

    private CcAnimation animation;
    private float duration;
    private float mToLarge;

    public enum CcAnimation {
        ToLarge
    }

    public void setAnimation( CcAnimation animation, float duration ) {
        this.animation = animation;
        this.duration = duration;
        ccAnimate();
    }

    public void setDuration( float duration ) {
        this.duration = duration;
    }

    public CcRadarChart( Context context ) {
        this( context, null );
    }

    public CcRadarChart( Context context, AttributeSet attrs ) {
        this( context, attrs, 0 );
    }

    public CcRadarChart( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        mPaint = new Paint();
        mPaint.setTextSize( mTextSize );
        mTextBounds = new Rect();
        mPaint.getTextBounds( mText, 0, mText.length(), mTextBounds );
        duration = 1000;
        mToLarge = 1;

        this.setOnTouchListener( new OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                float touchX, touchY;
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        touchX = event.getX();
                        touchY = event.getY();
                        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
                            int result = label.getLabelData().get( i ).checkClick( touchX, touchY );
                            if ( result != -1 && listener != null ) {
                                listener.onLabelClickListener( label.getLabelData().get( result ), result );
                                return true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;
            }
        } );
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        if ( dataSet == null ) {
            canvas.drawText( mText, getWidth() / 2 - mTextBounds.width() / 2, getHeight() / 2, mPaint );
        } else {
            label = dataSet.getLabelData();
        }

        if ( label == null || label.getLabelData().size() < 2 ) {
            mPaint.setColor( Color.RED );
            mPaint.setStyle( Style.FILL );
            canvas.drawText( mText, getWidth() / 2 - mTextBounds.width() / 2, getHeight() / 2, mPaint );
        } else {
            initRadarChart();
            drawLabel( canvas );
            if ( label.getStyle() == CcStyle.Circle ) {
                drawBackgroundCircle( canvas );
            } else if ( label.getStyle() == CcStyle.Rect ) {
                drawBackgroundRect( canvas );
            }

            if ( dataSet.getDataSet() == null || dataSet.getDataSet().isEmpty() ) {
                // data error
            } else {
                drawValuesAnimation( canvas );
            }
        }
    }

    private void drawValuesAnimation( Canvas canvas ) {
        checkValue();
        drawValues( canvas );
    }

    private void drawValues( Canvas canvas ) {
        for ( int i = 0; i < dataSet.getDataSet().size(); i++ ) {
            CcRadarChartData data = dataSet.getDataSet().get( i );
            Path path = new Path();
            float value = ( data.getEntities().get( 0 ).getValue() - label.getMinValue() ) * mToLarge;
            float length = label.getMaxValue() - label.getMinValue();
            float drawX = getPathX( centerX, label.getLabelData().get( 0 ).getX(), value, length );
            float drawY = getPathY( centerY, label.getLabelData().get( 0 ).getY(), value, length );
            path.moveTo( drawX, drawY );
            for ( int j = 1; j < data.getEntities().size(); j++ ) {
                value = ( data.getEntities().get( j ).getValue() - label.getMinValue() ) * mToLarge;
                drawX = getPathX( centerX, label.getLabelData().get( j ).getX(), value, length );
                drawY = getPathY( centerY, label.getLabelData().get( j ).getY(), value, length );
                path.lineTo( drawX, drawY );
            }
            path.close();

            mPaint.setColor( data.getStrokeColor() );
            mPaint.setStrokeWidth( data.getStrokeWidth() );
            mPaint.setStrokeCap( Cap.ROUND );
            mPaint.setAntiAlias( true );
            mPaint.setStyle( Style.STROKE );
            canvas.drawPath( path, mPaint );

            if ( data.isDrawFill() ) {
                mPaint.setColor( data.getFillColor() );
                mPaint.setAlpha( data.getAlpha() );
                mPaint.setAntiAlias( true );
                mPaint.setStyle( Style.FILL );
                canvas.drawPath( path, mPaint );
            }
        }
    }

    private void checkValue() {
        for ( int i = 0; i < dataSet.getDataSet().size(); i++ ) {
            for ( int j = 0; j < dataSet.getDataSet().get( i ).getEntities().size(); j++ ) {
                if ( dataSet.getDataSet().get( i ).getEntities().get( j ).getValue() > dataSet.getLabelData().getMaxValue() ) {
                    dataSet.getLabelData().setMaxValue( dataSet.getDataSet().get( i ).getEntities().get( j ).getValue() + 10 );
                }
                if ( dataSet.getDataSet().get( i ).getEntities().get( j ).getValue() < dataSet.getLabelData().getMinValue() ) {
                    dataSet.getLabelData().setMinValue( dataSet.getDataSet().get( i ).getEntities().get( j ).getValue() + 10 );
                }
            }
        }
    }

    private float getPathX( float x1, float x2, float v, float r ) {
        return x1 + ( x2 - x1 ) * v / r;
    }

    private float getPathY( float y1, float y2, float v, float r ) {
        return y1 + ( y2 - y1 ) * v / r;
    }

    private void drawLabel( Canvas canvas ) {
        initLabel();

        float angle = 360 / label.getLabelData().size();

        float startX, startY, drawX, drawY;
        if ( label.getLabelData().size() % 2 == 0 ) {
            startX = getWidth() / 2 - radiusBig;
            startY = getHeight() / 2;
            for ( int i = 1; i < label.getLabelData().size(); i++ ) {
                drawX = centerX - cosAngleRadius( angle * i );
                drawY = centerY - sinAngleRadius( angle * i );
                canvas.drawLine( centerX, centerY, drawX, drawY, mPaint );
                label.getLabelData().get( i ).setX( drawX );
                label.getLabelData().get( i ).setY( drawY );
            }
        } else {
            startX = getWidth() / 2;
            startY = getHeight() / 2 - radiusBig;
            for ( int i = 1; i < label.getLabelData().size(); i++ ) {
                drawX = centerX + sinAngleRadius( angle * i );
                drawY = centerY - cosAngleRadius( angle * i );
                canvas.drawLine( centerX, centerY, drawX, drawY, mPaint );
                label.getLabelData().get( i ).setX( drawX );
                label.getLabelData().get( i ).setY( drawY );
            }
        }

        drawX = startX;
        drawY = startY;
        label.getLabelData().get( 0 ).setX( drawX );
        label.getLabelData().get( 0 ).setY( drawY );
        canvas.drawLine( centerX, centerY, drawX, drawY, mPaint );

        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            mText = label.getLabelData().get( i ).getWords();
            mTextBounds = label.getLabelData().get( i ).getBounds();
            label.getLabelData().get( i ).getPaint().getTextBounds( mText, 0, mText.length(), mTextBounds );
            float drawWordsX = label.getLabelData().get( i ).getX() == centerX ? label.getLabelData().get( i ).getX() - mTextBounds.width() / 2 : label.getLabelData().get( i ).getX() < centerX ? label.getLabelData().get( i ).getX() - mTextBounds.width() * 1.1f : label.getLabelData().get( i ).getX();
            float drawWordsY = label.getLabelData().get( i ).getY() == centerY ? label.getLabelData().get( i ).getY() + mTextBounds.height() / 2 : label.getLabelData().get( i ).getY() < centerY ? label.getLabelData().get( i ).getY() - mTextBounds.height() * 0.2f : label.getLabelData().get( i ).getY() + mTextBounds.height();
            label.getLabelData().get( i ).setTouchArea( drawWordsX, drawWordsY, drawWordsX + mTextBounds.width(), drawWordsY - mTextBounds.height() );
            canvas.drawText( mText, drawWordsX, drawWordsY, label.getLabelData().get( i ).getPaint() );
        }
    }


    private void initLabel() {
        mPaint.setAntiAlias( true );
        mPaint.setStrokeWidth( label.getLabelStrokeWidth() );
        mPaint.setStrokeCap( Cap.ROUND );
        mPaint.setAntiAlias( true );
        mPaint.setStyle( Style.STROKE );
        mPaint.setColor( label.getLabelColor() );
    }

    private void drawBackgroundCircle( Canvas canvas ) {

        for ( int i = 0; i < label.getLabelCount(); i++ ) {
            canvas.drawCircle( centerX, centerY, radiusBig * ( i + 1 ) / label.getLabelCount(), mPaint );
        }
    }

    private void drawBackgroundRect( Canvas canvas ) {

        for ( int i = 0; i < label.getLabelCount(); i++ ) {
            Path path = new Path();
            float labelCount = radiusBig * ( i + 1 ) / label.getLabelCount();
            float drawX = getPathX( centerX, label.getLabelData().get( 0 ).getX(), labelCount, radiusBig );
            float drawY = getPathY( centerY, label.getLabelData().get( 0 ).getY(), labelCount, radiusBig );
            path.moveTo( drawX, drawY );
            for ( int j = 1; j < label.getLabelData().size(); j++ ) {
                drawX = getPathX( centerX, label.getLabelData().get( j ).getX(), labelCount, radiusBig );
                drawY = getPathY( centerY, label.getLabelData().get( j ).getY(), labelCount, radiusBig );
                path.lineTo( drawX, drawY );
            }
            path.close();
            canvas.drawPath( path, mPaint );
        }
    }

    private void initRadarChart() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radiusBig = ( centerX > centerY ? centerY : centerX ) * 3 / 4;
    }

    public void setData( CcRadarChartDataSet dataSet ) {
        this.setData( dataSet, null );
    }

    public void setData( CcRadarChartDataSet dataSet, CcAnimation animation ) {
        this.setData( dataSet, animation, 1000 );
    }

    public void setData( CcRadarChartDataSet dataSet, CcAnimation animation, final float duration ) {
        this.dataSet = dataSet;
        this.animation = animation;
        this.duration = duration;
        if ( animation != null ) {
            ccAnimate();
        }
    }

    private void ccAnimate() {
        if ( animation == CcAnimation.ToLarge ) {
            mToLarge = 0;
            new Thread() {
                @Override
                public void run() {
                    while ( true ) {
                        if ( mToLarge >= 1 ) {
                            break;
                        }
                        mToLarge += 1.0 / ( duration / 20.0 );
                        postInvalidate();
                        try {
                            Thread.sleep( 20 );
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private float sinAngleRadius( float angle ) {
        return ( float ) ( Math.sin( angle * Math.PI / 180.0f ) * radiusBig - 0.5 );
    }

    private float cosAngleRadius( float angle ) {
        return ( float ) ( Math.cos( angle * Math.PI / 180.0f ) * radiusBig - 0.5 );
    }

    public interface OnLabelClickListener {
        void onLabelClickListener( CcRadarChartLabelEntity label, int index );
    }

    public void setOnLabelClickListener( OnLabelClickListener listener ) {
        this.listener = listener;
    }
}
