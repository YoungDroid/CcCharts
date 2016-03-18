package com.oom.cccharts.model;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChartDataEntity {
    protected float value;
    protected int index;

    public CcChartDataEntity( float value, int index ) {
        this.value = value;
        this.index = index;
    }

    public float getValue() {
        return value;
    }

    public void setValue( float value ) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex( int index ) {
        this.index = index;
    }
}
