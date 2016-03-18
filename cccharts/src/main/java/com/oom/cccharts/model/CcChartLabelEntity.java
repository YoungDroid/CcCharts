package com.oom.cccharts.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChartLabelEntity {

    public static final int DEFAULT_TEXT_SIZE = 40;
    public static final int DEFAULT_TEXT_STROKE = 2;

    protected int index;
    protected float x, y;
    protected float touchStartX, touchStartY, touchEndX, touchEndY;
    protected String words;
    protected Paint paint;
    protected Rect bounds;

    public CcChartLabelEntity( String words ) {
        this.words = words.equals( "" ) ? "ErrorData" : words;

        x = 0;
        y = 0;
        paint = new Paint();
        paint.setColor( Color.GRAY );
        paint.setStyle( Style.FILL_AND_STROKE );
        paint.setStrokeWidth( DEFAULT_TEXT_STROKE );
        paint.setTextSize( DEFAULT_TEXT_SIZE );
        bounds = new Rect();
        paint.getTextBounds( this.words, 0, this.words.length(), bounds );
    }

    public int getIndex() {
        return index;
    }

    public void setIndex( int index ) {
        this.index = index;
    }

    public void setX( float x ) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY( float y ) {
        this.y = y;
    }

    public float getY() {
        return y;
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

    public Rect getBounds() {
        calculateTextBounds();
        return bounds;
    }

    public void setTextSize( int size ) {
        paint.setTextSize( size );
    }

    public void setTextColor( int color ) {
        paint.setColor( color );
    }

    public void setLocation( float x, float y ) {
        this.touchStartX = x;
        this.touchStartY = y;
    }

    public int checkClick( float touchX, float touchY ) {
        calculateTouchArea();
        return touchX >= touchStartX && touchX <= touchEndX && touchY <= touchStartY && touchY >= touchEndY ? index : -1;
    }

    private void calculateTouchArea() {
        touchEndX = x + getBounds().width();
        touchEndY = y - getBounds().height();
    }

    private void calculateTextBounds() {
        paint.getTextBounds( words, 0, words.length(), bounds );
    }
}
