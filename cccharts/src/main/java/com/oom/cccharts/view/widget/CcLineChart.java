package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.oom.cccharts.model.CcLineChartData;
import com.oom.cccharts.model.CcLineChartDataEntity;
import com.oom.cccharts.model.CcLineChartLabel;
import com.oom.cccharts.model.CcLineChartLabelEntity;
import com.oom.cccharts.model.CcLineChartLabelEntity.CcLabelChartType;
import com.oom.cccharts.utils.PointUtils;

/**
 * Created by CcYang on 2016/3/21.
 */
public class CcLineChart extends CcChart {

    public static final int INTERVAL_END_Y = 32;
    private CcLineChartLabel label;

    private float maxValue = Integer.MAX_VALUE, averageValue, minValue = Integer.MAX_VALUE;
    private float valueStartX = 0;

    private boolean showBackground = false;

    private CcLineChartOrientation orientation;

    public enum CcLineChartOrientation {
        Horizontal, Vertical
    }

    private float chartHeight;
    private float chartWidth;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float startDistance = 1f, newDistance = 1f;
    private boolean isScale = false;
    private float scaleRatio = 0.01f;
    private float translateRatioX = 0.01f;
    private float translateRatioY = 0.01f;
    private float touchDownX = 0, touchDownY = 0, touchMoveX = 0, touchMoveY = 0, touchUpX = 0, touchUpY = 0;

    public CcLineChart( Context context ) {
        super( context );
    }

    public CcLineChart( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public CcLineChart( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public CcLineChartOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation( CcLineChartOrientation orientation ) {
        this.orientation = orientation;
    }

    @Override
    public void init( Context context ) {
        super.init( context );
        orientation = CcLineChartOrientation.Horizontal;

        this.setOnTouchListener( new OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                switch ( event.getAction() & MotionEvent.ACTION_MASK ) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownX = event.getX();
                        touchDownY = event.getY();
                        startPoint.set( event.getX(), event.getY() );
                        Log.e( "ACTION_DOWN", "\t" + touchDownX + "\t" + touchDownY );
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        midPoint.set( event.getX( 1 ), event.getY( 1 ) );
                        startDistance = PointUtils.calculate2PointLength( startPoint.x, startPoint.y, midPoint.x, midPoint.y );
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchMoveX = event.getX();
                        touchMoveY = event.getY();
                        Log.e( "ACTION_MOVE", "\t" + touchMoveX + "\t" + touchMoveY );
                        if ( event.getPointerCount() > 1 ) {
                            startPoint.set( event.getX( 0 ), event.getY( 0 ) );
                            midPoint.set( event.getX( 1 ), event.getY( 1 ) );
                             newDistance = PointUtils.calculate2PointLength( startPoint.x, startPoint.y, midPoint.x, midPoint.y );
                            if ( newDistance > startDistance ) {
                                chartWidth = chartWidth + scaleRatio * chartWidth;
                                chartHeight = chartHeight + scaleRatio * chartHeight;
                            } else if ( newDistance < startDistance ) {
                                chartWidth = chartWidth - scaleRatio * chartWidth;
                                chartHeight = chartHeight - scaleRatio * chartHeight;
                                if ( chartWidth < getWidth() ) {
                                    chartWidth = getWidth();
                                }
                                if ( chartHeight < getHeight() ) {
                                    chartHeight = getHeight();
                                }
                                if ( chartWidth == getWidth() && chartHeight == getHeight() ) {
                                    isScale = false;
                                }
                            }
                            isScale = true;
                            invalidate();
                        } else {
                            if ( isScale ) {
                                if ( touchMoveX - touchDownX < 0 ) {
                                    Log.e( "translate", "left\t" + touchMoveX + "\t" + touchDownX );
                                    valueStartX = valueStartX - translateRatioX * getWidth();
                                } else if ( touchMoveX - touchDownX > 0 ) {
                                    Log.e( "translate", "right\t" + touchMoveX + "\t" + touchDownX );
                                    valueStartX = valueStartX + translateRatioX * getWidth();
                                }
                                invalidate();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        touchUpX = event.getX();
                        touchUpY = event.getY();
                        Log.e( "ACTION_UP", "\t" + touchUpX + "\t" + touchUpY );

                        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
                            int result = label.getLabelData().get( i ).checkClick( touchUpX, touchUpY );
                            if ( result != -1 && label.getListener() != null ) {
                                label.getListener().onLabelClickListener( label.getLabelData().get( result ), result );
                                return true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                }
                return false;
            }
        } );
        this.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {

            }
        } );
    }

    @Override
    public void drawChart( Canvas canvas ) {
        initLineChart();
        drawLabel( canvas );
        checkValue();
        drawValue( canvas );
    }

    private void checkValue() {
        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            for ( int j = 0; j < dataSet.getChartDatas().get( i ).getEntities().size(); j++ ) {
                CcLineChartDataEntity entity = ( CcLineChartDataEntity ) dataSet.getChartDatas().get( i ).getEntities().get( j );
                if ( orientation == CcLineChartOrientation.Horizontal ) {
                    if ( entity.getValue() > label.getMaxValueY() ) {
                        label.setMaxValueY( entity.getValue() );
                    }
                    if ( entity.getValue() < label.getMinValueY() ) {
                        label.setMinValueY( entity.getValue() );
                    }
                    if ( entity.getOtherAxis() > label.getMaxValueX() ) {
                        label.setMaxValueX( entity.getOtherAxis() );
                    }
                    if ( entity.getOtherAxis() < label.getMinValueX() ) {
                        label.setMinValueX( entity.getOtherAxis() );
                    }
                }
            }
        }
    }

    private void drawValue( Canvas canvas ) {

        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            CcLineChartData data = ( CcLineChartData ) dataSet.getChartDatas().get( i );
            for ( int j = 0; j < data.getEntities().size(); j++ ) {
                CcLineChartDataEntity entity = ( CcLineChartDataEntity ) data.getEntities().get( j );
                if ( orientation == CcLineChartOrientation.Horizontal ) {
                    float realX = valueStartX + getValueX( entity.getOtherAxis(), chartWidth - valueStartX );
                    entity.setRealX( realX == valueStartX ? realX + entity.getRadius() * 3 / 2 : realX == chartWidth ? chartWidth - entity.getRadius() * 3 / 2 : realX );
                    float realY = getValueY( entity.getValue(), chartHeight );
                    entity.setRealY( realY == 0 ? realY + entity.getRadius() * 3 / 2 : realY == chartHeight ? chartHeight - entity.getRadius() * 3 / 2 : realY );
                }
            }
        }

        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            CcLineChartData data = ( CcLineChartData ) dataSet.getChartDatas().get( i );
            mPaint.setColor( Color.parseColor( "#7cb5ec" ) );
            mPaint.setStyle( Style.STROKE );
            mPaint.setStrokeWidth( data.getStrokeWidth() );
            mPaint.setStrokeCap( Cap.ROUND );
            mPaint.setAntiAlias( true );
            float startX = 0, startY = 0, endX = 0, endY = 0;
            for ( int j = 0; j < data.getEntities().size(); j++ ) {
                CcLineChartDataEntity entity = ( CcLineChartDataEntity ) data.getEntities().get( j );
                if ( j == 0 ) {
                    startX = entity.getRealX();
                    startY = entity.getRealY();
                } else {
                    endX = entity.getRealX();
                    endY = entity.getRealY();
                    float pointLength = PointUtils.calculate2PointLength( startX, startY, endX, endY );
                    float drawStartX = PointUtils.getPathResult( startX, endX, entity.getRadius(), pointLength );
                    float drawStartY = PointUtils.getPathResult( startY, endY, entity.getRadius(), pointLength );
                    float drawEndX = PointUtils.getPathResult( startX, endX, pointLength - entity.getRadius(), pointLength );
                    float drawEndY = PointUtils.getPathResult( startY, endY, pointLength - entity.getRadius(), pointLength );

                    canvas.drawLine( drawStartX, drawStartY, drawEndX, drawEndY, mPaint );
                    startX = entity.getRealX();
                    startY = entity.getRealY();
                }
                canvas.drawCircle( entity.getRealX(), entity.getRealY(), entity.getRadius(), mPaint );
            }
        }
    }

    private void drawLabel( Canvas canvas ) {
        for ( int i = 0; i < label.getLabelCount(); i++ ) {
            if ( orientation == CcLineChartOrientation.Horizontal ) {
                float intervalY = chartHeight / ( label.getLabelCount() - 1 ) * 0.8f;
                mPaint.setColor( Color.GRAY );
                mPaint.setStrokeWidth( 2 );
                mPaint.setStyle( Style.FILL );
                mPaint.setAntiAlias( true );
                canvas.drawLine( valueStartX, i * intervalY + intervalY * 0.8f, chartWidth - INTERVAL_END_Y, i * intervalY + intervalY * 0.8f, mPaint );
            }
        }

        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            CcLineChartLabelEntity entity = ( CcLineChartLabelEntity ) label.getLabelData().get( i );
            if ( entity.getType() == CcLabelChartType.Other ) continue;
            canvas.drawText( entity.getWords(), entity.getX(), entity.getY() + entity.getBounds().height() / 2, entity.getPaint() );
            mPaint.setColor( Color.RED );
            mPaint.setStrokeWidth( 4 );
            mPaint.setStyle( Style.FILL );
            mPaint.setAntiAlias( true );
            canvas.drawLine( valueStartX, entity.getY(), chartWidth - INTERVAL_END_Y, entity.getY(), mPaint );
        }
    }

    private void initLineChart() {
        label = ( CcLineChartLabel ) dataSet.getLabelData();
        if ( !isScale ) {
            chartWidth = getWidth();
            chartHeight = getHeight();
            for ( int i = 0; i < label.getLabelData().size(); i++ ) {
                valueStartX = label.getLabelData().get( i ).getBounds().width() > valueStartX ? label.getLabelData().get( i ).getBounds().width() * 3 / 2 : valueStartX;
            }
        }

        int count = 0;
        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            for ( int j = 0; j < dataSet.getChartDatas().get( i ).getEntities().size(); j++ ) {
                maxValue = dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() > maxValue ? dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() : maxValue;
                minValue = dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() < minValue ? dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue() : minValue;
                averageValue += dataSet.getChartDatas().get( i ).getEntities().get( j ).getValue();
                count++;
            }
        }
        averageValue /= count;

        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            CcLineChartLabelEntity entity = ( CcLineChartLabelEntity ) label.getLabelData().get( i );
            entity.setX( 0 );
            if ( entity.getType() == CcLabelChartType.Average ) {
                entity.setY( getValueY( averageValue, chartHeight ) );
            } else if ( entity.getType() == CcLabelChartType.Max ) {
                entity.setY( getValueY( maxValue, chartHeight ) );
            } else if ( entity.getType() == CcLabelChartType.Min ) {
                entity.setY( getValueY( minValue, chartHeight ) );
            }
        }
    }

    private float getValueX( float value, float height ) {
        if ( orientation == CcLineChartOrientation.Horizontal ) {
            return value / ( label.getMaxValueX() - label.getMinValueX() ) * height;
        } else {
            return value / ( label.getMaxValueY() - label.getMinValueY() ) * height;
        }
    }

    private float getValueY( float value, float height ) {
        if ( orientation == CcLineChartOrientation.Horizontal ) {
            return height - value / ( label.getMaxValueY() - label.getMinValueY() ) * height;
        } else {
            return value / ( label.getMaxValueX() - label.getMinValueX() ) * height;
        }
    }
}
