package com.oom.cccharts.model;

/**
 * Created by CcYang on 2016/3/21.
 */
public class CcLineChartDataEntity extends CcChartDataEntity {
    float radius;
    private float otherAxis;
    private float realX, realY;

    public CcLineChartDataEntity( float value, float otherAxis, int index ) {
        super( value, index );
        this.otherAxis = otherAxis;
        this.radius = 10;
    }

    public float getOtherAxis() {
        return otherAxis;
    }

    public void setOtherAxis( float otherAxis ) {
        this.otherAxis = otherAxis;
    }

    public float getRealX() {
        return realX;
    }

    public void setRealX( float realX ) {
        this.realX = realX;
    }

    public float getRealY() {
        return realY;
    }

    public void setRealY( float realY ) {
        this.realY = realY;
    }
    public float getRadius() {
        return radius;
    }

    public void setRadius( float radius ) {
        this.radius = radius;
    }
}
