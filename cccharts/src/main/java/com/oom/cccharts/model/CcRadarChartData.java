package com.oom.cccharts.model;

import android.graphics.Color;

import java.util.ArrayList;

public class CcRadarChartData {

    int fillColor;
    int alpha;
    int strokeWidth;
    int strokeColor;
    boolean drawFill;
    ArrayList< CcRadarChartDataEntity > entities;

    public CcRadarChartData() {
        fillColor = Color.LTGRAY;
        alpha = 75;
        strokeWidth = 4;
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

    public ArrayList< CcRadarChartDataEntity > getEntities() {
        return entities;
    }

    public void setEntities( ArrayList< CcRadarChartDataEntity > entities ) {
        this.entities = entities;
    }

    public void addEntity( CcRadarChartDataEntity entity ) {
        if ( entities != null && entity != null ) {
            entities.add( entity );
        }
    }
}
