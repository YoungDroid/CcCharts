package com.oom.cccharts.model;

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
        this.type = CcLabelChartType.Other;
    }

    public CcLabelChartType getType() {
        return type;
    }

    public void setType( CcLabelChartType type ) {
        this.type = type;
    }
}
