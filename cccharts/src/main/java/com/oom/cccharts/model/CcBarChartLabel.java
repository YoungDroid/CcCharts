package com.oom.cccharts.model;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcBarChartLabel extends CcChartLabel {
    float minValue;
    float maxValue;

    public CcBarChartLabel() {
        super();
        minValue = 0;
        maxValue = 100;
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
}
