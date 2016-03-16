package com.oom.cccharts.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class CcRadarChartLabelEntity {
    int index;
    float x, y;
    float touchStartX, touchStartY, touchEndX, touchEndY;
    String words;
    Paint paint;
    Rect bounds;

    public CcRadarChartLabelEntity() {
        x = 0;
        y = 0;
        words = "Error data";
        paint = new Paint();
        paint.setColor( Color.RED );
        paint.setStyle( Style.FILL );
        paint.setTextSize( 40 );
        bounds = new Rect();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex( int index ) {
        this.index = index;
    }

    public float getX() {
        return x;
    }

    public void setX( float x ) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY( float y ) {
        this.y = y;
    }

    public float getTouchStartX() {
        return touchStartX;
    }

    public void setTouchStartX( float touchStartX ) {
        this.touchStartX = touchStartX;
    }

    public float getTouchStartY() {
        return touchStartY;
    }

    public void setTouchStartY( float touchStartY ) {
        this.touchStartY = touchStartY;
    }

    public float getTouchEndX() {
        return touchEndX;
    }

    public void setTouchEndX( float touchEndX ) {
        this.touchEndX = touchEndX;
    }

    public float getTouchEndY() {
        return touchEndY;
    }

    public void setTouchEndY( float touchEndY ) {
        this.touchEndY = touchEndY;
    }

    public String getWords() {
        return words;
    }

    public void setWords( String words ) {
        this.words = words;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint( Paint paint ) {
        this.paint = paint;
    }

    public Rect getBounds() {
        return bounds;
    }

    public void setBounds( Rect bounds ) {
        this.bounds = bounds;
    }

    public void setTextSize( int size ) {
        paint.setTextSize( size );
    }

    public void setTextColor( int color ) {
        paint.setColor( color );
    }

    public void setTouchArea( float startX, float startY, float endX, float endY ) {
        touchStartX = startX;
        touchStartY = startY;
        touchEndX = endX;
        touchEndY = endY;
    }

    public int checkClick( float touchX, float touchY ) {
        return touchX >= touchStartX && touchX <= touchEndX && touchY <= touchStartY && touchY >= touchEndY ? index : -1;
    }
}
