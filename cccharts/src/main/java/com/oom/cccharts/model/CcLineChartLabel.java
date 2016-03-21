package com.oom.cccharts.model;

/**
 * Created by CcYang on 2016/3/21.
 */
public class CcLineChartLabel extends CcChartLabel {
    float minValueX;
    float maxValueX;
    float minValueY;
    float maxValueY;

    public CcLineChartLabel() {
        super();
        minValueX = 0;
        maxValueX = 100;
        minValueY = 0;
        maxValueY = 100;
    }

    public float getMinValueX() {
        return minValueX;
    }

    public void setMinValueX( float minValueX ) {
        this.minValueX = minValueX;
    }

    public float getMaxValueX() {
        return maxValueX;
    }

    public void setMaxValueX( float maxValueX ) {
        this.maxValueX = maxValueX;
    }

    public float getMinValueY() {
        return minValueY;
    }

    public void setMinValueY( float minValueY ) {
        this.minValueY = minValueY;
    }

    public float getMaxValueY() {
        return maxValueY;
    }

    public void setMaxValueY( float maxValueY ) {
        this.maxValueY = maxValueY;
    }
}
