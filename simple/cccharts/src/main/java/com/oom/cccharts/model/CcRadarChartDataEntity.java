package com.oom.cccharts.model;

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
