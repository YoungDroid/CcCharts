package com.oom.cccharts.utils;

/**
 * Created by CcYang on 2016/3/21.
 */
public class PointUtils {
    public static float getPathResult( float start, float end, float length, float sum ) {
        return start + ( end - start ) * length / sum;
    }

    public static float calculate2PointLength( float startX, float startY, float endX, float endY ) {
        return ( float ) Math.abs( Math.sqrt( ( startX - endX ) * ( startX - endX ) + ( startY - endY ) * ( startY - endY ) ) );
    }
}
