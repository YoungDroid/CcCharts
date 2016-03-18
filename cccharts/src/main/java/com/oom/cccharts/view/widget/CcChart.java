package com.oom.cccharts.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oom.cccharts.model.CcChartData;
import com.oom.cccharts.model.CcChartDataSet;
import com.oom.cccharts.model.CcChartLabel;

/**
 * Created by CcYang on 2016/3/18.
 */
public class CcChart extends View {
    protected Paint mPaint;
    protected Rect mTextBounds;
    protected int mTextColor;
    protected float mTextSize;
    protected float mTextStrokeWidth;
    protected String mText;

    protected CcChartDataSet dataSet;
    protected CcChartData data;
    protected CcChartLabel label;

    public CcChart( Context context ) {
        super( context );
        init();
    }

    public CcChart( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public CcChart( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        init();
    }

    public void init() {
        mText = "You need some useful data.";
        mTextColor = Color.RED;
        mTextSize = 48;
        mTextStrokeWidth = 1;

        mPaint = new Paint();
        mPaint.setColor( mTextColor );
        mPaint.setTextSize( mTextSize );
        mPaint.setStyle( Style.FILL_AND_STROKE );
        mPaint.setStrokeCap( Cap.ROUND );
        mPaint.setStrokeWidth( mTextStrokeWidth );
        mTextBounds = new Rect();
        mPaint.getTextBounds( mText, 0, mText.length(), mTextBounds );

        this.setOnTouchListener( new OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                float touchX, touchY;
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        touchX = event.getX();
                        touchY = event.getY();
                        for ( int i = 0; i < label.getLabelData().size(); i++ ) {
                            int result = label.getLabelData().get( i ).checkClick( touchX, touchY );
                            if ( result != -1 && label.getListener() != null ) {
                                label.getListener().onLabelClickListener( label.getLabelData().get( result ), result );
                                return true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        } );
    }

    public void setData( CcChartDataSet dataSet ) {
        this.dataSet = dataSet;
        invalidate();
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        if ( dataSet == null ) {
            canvas.drawText( mText, getWidth() / 2 - mTextBounds.width() / 2, getHeight() / 2, mPaint );
        } else {
            label = dataSet.getLabelData();
            if ( label == null || label.getLabelData().size() < 2 ) {
                mPaint.setColor( Color.RED );
                mPaint.setStyle( Style.FILL );
                canvas.drawText( mText, getWidth() / 2 - mTextBounds.width() / 2, getHeight() / 2, mPaint );
            } else {
                drawChart( canvas );
            }
        }
    }

    public void drawChart( Canvas canvas ) {}
}
