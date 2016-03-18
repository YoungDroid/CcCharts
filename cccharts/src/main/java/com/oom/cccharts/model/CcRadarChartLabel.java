package com.oom.cccharts.model;

import android.graphics.Color;

public class CcRadarChartLabel extends CcChartLabel {
    int labelCount;
    float minValue;
    float maxValue;
    int labelColor;
    float labelStrokeWidth;

    CcStyle style;

    public enum CcStyle {
        Circle, Rect
    }

    public CcRadarChartLabel() {
        super();
        labelCount = 2;
        style = CcStyle.Circle;
        minValue = 0;
        maxValue = 100;
        labelColor = Color.GRAY;
        labelStrokeWidth = 4;
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

    public int getLabelCount() {
        return labelCount;
    }

    public void setLabelCount( int labelCount ) {
        this.labelCount = labelCount;
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
}
