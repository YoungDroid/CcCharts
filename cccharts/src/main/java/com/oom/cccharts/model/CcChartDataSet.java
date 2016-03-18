package com.oom.cccharts.model;

import java.util.ArrayList;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChartDataSet {
    CcChartLabel labelData;
    ArrayList< CcChartData > chartDatas;

    public CcChartDataSet() {
        chartDatas = new ArrayList<>();
    }

    public CcChartLabel getLabelData() {
        return labelData;
    }

    public void setLabelData( CcChartLabel labelData ) {
        this.labelData = labelData;
    }

    public ArrayList< CcChartData > getChartDatas() {
        return chartDatas;
    }

    public void setChartDatas( ArrayList< CcChartData > chartDatas ) {
        this.chartDatas = chartDatas;
    }

    public void addChartData( CcChartData data ) {
        if ( null != chartDatas && data != null ) {
            chartDatas.add( data );
        }
    }
}
