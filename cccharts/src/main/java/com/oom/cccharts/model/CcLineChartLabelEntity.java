package com.oom.cccharts.model;

import android.graphics.Color;

/**
 * Created by CcYang on 2016/3/21.
 */
public class CcLineChartLabelEntity extends CcChartLabelEntity {

    private CcLabelChartType type;

    public enum CcLabelChartType {
        Max, Average, Min, Other
    }

    public CcLineChartLabelEntity( String words ) {
        super( words );
        this.paint.setTextSize( 58 );
        this.paint.setColor( Color.BLACK );
        this.type = CcLabelChartType.Other;
    }

    public CcLabelChartType getType() {
        return type;
    }

    public void setType( CcLabelChartType type ) {
        this.type = type;
    }
}
