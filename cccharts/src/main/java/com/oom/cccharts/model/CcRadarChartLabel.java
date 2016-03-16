package com.oom.cccharts.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by 小白杨 on 2016/3/15.
 */
public class CcRadarChartLabel {

    ArrayList< CcRadarChartLabelEntity > labelData;
    int labelCount;
    int labelColor;
    float labelStrokeWidth;
    float minValue;
    float maxValue;
    int index;

    CcStyle style;

    public enum CcStyle {
        Circle, Rect
    }

    public CcRadarChartLabel() {
        index = 0;
        labelData = new ArrayList<>();
        labelCount = 2;
        labelColor = Color.GRAY;
        labelStrokeWidth = 4;
        minValue = 0;
        maxValue = 100;
        style = CcStyle.Circle;
    }

    public int getLabelCount() {
        return labelCount;
    }

    public void setLabelCount( int labelCount ) {
        this.labelCount = labelCount;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor( int labelColor ) {
        this.labelColor = labelColor;
    }

    public float getLabelStrokeWidth() {
        return labelStrokeWidth;
    }

    public void setLabelStrokeWidth( float labelStrokeWidth ) {
        this.labelStrokeWidth = labelStrokeWidth;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue( float minValue ) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue( float maxValue ) {
        this.maxValue = maxValue;
    }

    public CcStyle getStyle() {
        return style;
    }

    public void setStyle( CcStyle style ) {
        this.style = style;
    }

    public ArrayList< CcRadarChartLabelEntity > getLabelData() {
        return labelData;
    }

    public void setLabelData( ArrayList< CcRadarChartLabelEntity > labelData ) {
        this.labelData = labelData;
    }

    public void addEntity( CcRadarChartLabelEntity entity ) {
        if ( labelData != null && entity != null ) {
            entity.setIndex( index++ );
            labelData.add( entity );
        }
    }
}
