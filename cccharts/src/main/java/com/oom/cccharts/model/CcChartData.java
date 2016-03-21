package com.oom.cccharts.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChartData {

    protected int fillColor;
    protected int alpha;
    protected int strokeWidth;
    protected int strokeColor;
    protected boolean drawFill;
    protected ArrayList< CcChartDataEntity > entities;

    public CcChartData() {
        fillColor = Color.LTGRAY;
        alpha = 75;
        strokeWidth = 5;
        strokeColor = Color.BLACK;
        drawFill = false;
        entities = new ArrayList<>();
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor( int fillColor ) {
        this.fillColor = fillColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth( int strokeWidth ) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor( int color ) {
        this.strokeColor = color;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha( int alpha ) {
        this.alpha = alpha;
    }

    public boolean isDrawFill() {
        return drawFill;
    }

    public void setDrawFill( boolean drawFill ) {
        this.drawFill = drawFill;
    }

    public ArrayList< CcChartDataEntity > getEntities() {
        return entities;
    }

    public void setEntities( ArrayList< CcChartDataEntity > entities ) {
        this.entities = entities;
    }

    public void addEntity( CcChartDataEntity entity ) {
        if ( entities != null && entity != null ) {
            entities.add( entity );
        }
    }
}
