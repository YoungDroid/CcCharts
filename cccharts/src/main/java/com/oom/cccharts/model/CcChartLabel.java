package com.oom.cccharts.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChartLabel {

    int index;
    ArrayList< CcChartLabelEntity > labelData;
    OnLabelClickListener listener;

    public CcChartLabel() {
        index = 0;
        labelData = new ArrayList<>();
    }

    public ArrayList< CcChartLabelEntity > getLabelData() {
        return labelData;
    }

    public void setLabelData( ArrayList< CcChartLabelEntity > labelData ) {
        this.labelData = labelData;
    }

    public void addEntity( CcChartLabelEntity entity ) {
        if ( null != labelData && entity != null ) {
            entity.setIndex( index++ );
            labelData.add( entity );
        }
    }

    public interface OnLabelClickListener {
        void onLabelClickListener( CcChartLabelEntity label, int index );
    }

    public OnLabelClickListener getListener() {
        return listener;
    }

    public void setOnLabelClickListener( OnLabelClickListener listener ) {
        this.listener = listener;
    }
}