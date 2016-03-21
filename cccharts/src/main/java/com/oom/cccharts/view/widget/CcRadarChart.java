package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;

import com.oom.cccharts.model.CcRadarChartData;
import com.oom.cccharts.model.CcRadarChartLabel;
import com.oom.cccharts.model.CcRadarChartLabel.CcStyle;

public class CcRadarChart extends CcChart {

    private float radiusBig;
    private float centerX;
    private float centerY;
    private float duration;
    private float mToLarge;

    private CcAnimation animation;

    public enum CcAnimation {
        ToLarge
    }

    private CcRadarChartLabel label;

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
    }

    @Override
    public void init() {
        super.init();
        duration = 1000;
        mToLarge = 1;
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
    }

    @Override
    public void drawChart( Canvas canvas ) {
        initRadarChart();
        drawLabel( canvas );
        if ( label.getStyle() == CcStyle.Circle ) {
            drawBackgroundCircle( canvas );
        } else if ( label.getStyle() == CcStyle.Rect ) {
            drawBackgroundRect( canvas );
        }

        if ( dataSet.getChartDatas() == null || dataSet.getChartDatas().isEmpty() ) {
            // data error
        } else {
            drawValuesAnimation( canvas );
        }
    }

    private void drawValuesAnimation( Canvas canvas ) {
        checkValue();
        drawValues( canvas );
    }

    private void drawValues( Canvas canvas ) {
        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            CcRadarChartData data = ( CcRadarChartData ) dataSet.getChartDatas().get( i );
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
        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            for ( int j = 0; j < dataSet.getChartDatas().get( i ).getEntities().size(); j++ ) {
                if ( dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() > label.getMaxValue() ) {
                    label.setMaxValue( dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() + 10 );
                }
                if ( dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() < label.getMinValue() ) {
                    label.setMinValue( dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() + 10 );
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
            float drawWordsY = label.getLabelData().get( i ).getY() >= centerY-5 && label.getLabelData().get( i ).getY() <= centerY+5 ? label.getLabelData().get( i ).getY() + mTextBounds.height() / 2 : label.getLabelData().get( i ).getY() < centerY ? label.getLabelData().get( i ).getY() - mTextBounds.height() * 0.2f : label.getLabelData().get( i ).getY() + mTextBounds.height();
            label.getLabelData().get( i ).setLocation( drawWordsX, drawWordsY );
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
        label = ( CcRadarChartLabel ) dataSet.getLabelData();
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
}
