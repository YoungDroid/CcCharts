package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import com.oom.cccharts.model.CcBarChartData;
import com.oom.cccharts.model.CcBarChartDataEntity;
import com.oom.cccharts.model.CcBarChartLabel;
import com.oom.cccharts.model.CcBarChartLabelEntity;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcBarChart extends CcChart {

    private float maxLabelWidth;
    private float labelPadding;

    private CcBarChartLabel label;

    public CcBarChart( Context context ) {
        super( context );
    }

    public CcBarChart( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public CcBarChart( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @Override
    public void init( Context context ) {
        super.init( context );
        maxLabelWidth = 0;
        labelPadding = 32;
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
    }

    @Override
    public void drawChart( Canvas canvas ) {
        initBarChart();
        drawLabel( canvas );
        checkValue();
        drawValue( canvas );
    }

    private void drawValue( Canvas canvas ) {
        mPaint.setStrokeCap( Cap.BUTT );
        mPaint.setStyle( Style.STROKE );
        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            CcBarChartData data = ( CcBarChartData ) dataSet.getChartDatas().get( i );
            mPaint.setColor( data.getStrokeColor() );
            mPaint.setStrokeWidth( data.getStrokeWidth() );
            mPaint.setAlpha( data.getAlpha() );
            for ( int j = 0; j < data.getEntities().size(); j++ ) {
                CcBarChartDataEntity dataEntity = ( CcBarChartDataEntity ) data.getEntities().get( j );
                CcBarChartLabelEntity labelEntity = ( CcBarChartLabelEntity ) label.getLabelData().get( j );
                float drawValueStartX = maxLabelWidth + labelPadding + 2;
                float drawValueStartY = labelEntity.getY() - labelEntity.getBounds().height() / 3.0f + ( 2.0f * i - dataSet.getChartDatas().size() + 1 ) / 2.0f * data.getStrokeWidth();
                float drawValueEndX = drawValueStartX + dataEntity.getValue() / label.getMaxValue() * ( getWidth() - drawValueStartX );
                float drawValueEndY = drawValueStartY;
                canvas.drawLine( drawValueStartX, drawValueStartY, drawValueEndX, drawValueEndY, mPaint );
            }
        }
    }

    private void checkValue() {

        for ( int i = 0; i < dataSet.getChartDatas().size(); i++ ) {
            dataSet.getChartDatas().get( i ).setStrokeWidth( (getHeight() * 80 / 100 / dataSet.getChartDatas().size() / dataSet.getChartDatas().get( i ).getEntities().size()) );
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

    private void drawLabel( Canvas canvas ) {
        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            CcBarChartLabelEntity entity = ( CcBarChartLabelEntity ) label.getLabelData().get( i );
            float drawLabelX = maxLabelWidth - entity.getBounds().width();
            float drawLabelY = ( getHeight() * ( 1 + 2 * i ) ) / ( 2 * label.getLabelData().size() ) + entity.getBounds().height() / 3;
            entity.setX( drawLabelX );
            entity.setY( drawLabelY );
            entity.setLocation( drawLabelX, drawLabelY );
            canvas.drawText( entity.getWords(), drawLabelX, drawLabelY, entity.getPaint() );
        }

        mPaint.setColor( Color.GRAY );
        mPaint.setStrokeWidth( 3 );
        mPaint.setStyle( Style.STROKE );
        mPaint.setStrokeCap( Cap.ROUND );
        canvas.drawLine( maxLabelWidth + labelPadding, 0, maxLabelWidth + labelPadding, getHeight(), mPaint );
    }

    private void initBarChart() {
        label = ( CcBarChartLabel ) dataSet.getLabelData();
        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
            maxLabelWidth = label.getLabelData().get( i ).getBounds().width() > maxLabelWidth ? label.getLabelData().get( i ).getBounds().width() : maxLabelWidth;
        }
    }
}
