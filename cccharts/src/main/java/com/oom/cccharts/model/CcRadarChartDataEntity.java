package com.oom.cccharts.model;

/**
 * Created by 小白杨 on 2016/3/14.
 */
public class CcRadarChartDataEntity {
    float value;
    int index;

    public CcRadarChartDataEntity( float value, int index ) {
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
