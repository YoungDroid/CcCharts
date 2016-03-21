package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

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

    private CcLineChartLabel label;

    private float maxValue = Integer.MAX_VALUE, averageValue, minValue = Integer.MAX_VALUE;
    private float valueStartX = 0;

    private CcLineChartOrientation orientation;

    public enum CcLineChartOrientation {
        Horizontal, Vertical
    }

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
    public void init() {
        super.init();
        orientation = CcLineChartOrientation.Horizontal;
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
                        label.setMaxValueY( entity.getValue() + 10 );
                    }
                    if ( entity.getValue() < label.getMinValueY() ) {
                        label.setMinValueY( entity.getValue() + 10 );
                    }
                    if ( entity.getOtherAxis() > label.getMaxValueX() ) {
                        label.setMaxValueX( entity.getOtherAxis() + 10 );
                    }
                    if ( entity.getOtherAxis() < label.getMinValueX() ) {
                        label.setMinValueX( entity.getOtherAxis() + 10 );
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
                entity.setRealX( valueStartX * 3 / 2 + getValueX( entity.getOtherAxis(), getWidth() - valueStartX ) );
                entity.setRealY( getValueY( entity.getValue(), getHeight() ) );
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
        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            CcLineChartLabelEntity entity = ( CcLineChartLabelEntity ) label.getLabelData().get( i );
            if ( entity.getType() == CcLabelChartType.Other ) continue;
            canvas.drawText( entity.getWords(), entity.getX(), entity.getY() + entity.getBounds().height() / 2, entity.getPaint() );
            mPaint.setColor( Color.RED );
            canvas.drawLine( valueStartX, entity.getY(), getWidth() - 32, entity.getY(), mPaint );
        }
    }

    private void initLineChart() {
        label = ( CcLineChartLabel ) dataSet.getLabelData();
        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            valueStartX = label.getLabelData().get( i ).getBounds().width() > valueStartX ? label.getLabelData().get( i ).getBounds().width() * 3 / 2 : valueStartX;
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
                entity.setY( getValueY( averageValue, getHeight() ) );
            } else if ( entity.getType() == CcLabelChartType.Max ) {
                entity.setY( getValueY( maxValue, getHeight() ) );
            } else if ( entity.getType() == CcLabelChartType.Min ) {
                entity.setY( getValueY( minValue, getHeight() ) );
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
