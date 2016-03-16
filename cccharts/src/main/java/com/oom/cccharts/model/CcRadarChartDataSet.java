package com.oom.cccharts.model;

import java.util.ArrayList;

/**
 * Created by 小白杨 on 2016/3/14.
 */
public class CcRadarChartDataSet {
    CcRadarChartLabel labelData;
    ArrayList< CcRadarChartData > dataSet;

    public CcRadarChartDataSet() {
        dataSet = new ArrayList<>();
    }

    public CcRadarChartDataSet( ArrayList< CcRadarChartData > dataSet ) {
        this.dataSet = dataSet;
    }

    public CcRadarChartLabel getLabelData() {
        return labelData;
    }

    public void setLabelData( CcRadarChartLabel labelData ) {
        this.labelData = labelData;
    }

    public ArrayList< CcRadarChartData > getDataSet() {
        return dataSet;
    }

    public void setDataSet( ArrayList< CcRadarChartData > dataSet ) {
        this.dataSet = dataSet;
    }

    public void addRadarChartData( CcRadarChartData data ) {
        if ( null != dataSet && data != null ) {
            dataSet.add( data );
        }
    }
}
