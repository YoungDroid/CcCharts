package com.oom.simple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.oom.cccharts.model.CcBarChartData;
import com.oom.cccharts.model.CcBarChartDataEntity;
import com.oom.cccharts.model.CcBarChartDataSet;
import com.oom.cccharts.model.CcBarChartLabel;
import com.oom.cccharts.model.CcBarChartLabelEntity;
import com.oom.cccharts.model.CcChartLabel.OnLabelClickListener;
import com.oom.cccharts.model.CcChartLabelEntity;
import com.oom.cccharts.model.CcLineChartData;
import com.oom.cccharts.model.CcLineChartDataEntity;
import com.oom.cccharts.model.CcLineChartDataSet;
import com.oom.cccharts.model.CcLineChartLabel;
import com.oom.cccharts.model.CcLineChartLabelEntity;
import com.oom.cccharts.model.CcLineChartLabelEntity.CcLabelChartType;
import com.oom.cccharts.model.CcRadarChartData;
import com.oom.cccharts.model.CcRadarChartDataEntity;
import com.oom.cccharts.model.CcRadarChartDataSet;
import com.oom.cccharts.model.CcRadarChartLabel;
import com.oom.cccharts.model.CcRadarChartLabel.CcStyle;
import com.oom.cccharts.model.CcRadarChartLabelEntity;
import com.oom.cccharts.view.widget.CcBarChart;
import com.oom.cccharts.view.widget.CcLineChart;
import com.oom.cccharts.view.widget.CcRadarChart;
import com.oom.cccharts.view.widget.CcRadarChart.CcAnimation;

public class MainActivity extends AppCompatActivity {

    private CcRadarChart rcMain;
    private CcBarChart bcMain;
    private CcLineChart lcMain;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

//        initRadarChart();
        initBarChart();
        initLineChart();
    }

    private void initLineChart() {
        CcLineChartLabel labelData = new CcLineChartLabel();
        labelData.setMaxValueY( 200 );
        CcLineChartLabelEntity labelEntity = new CcLineChartLabelEntity( "平均" );
        labelEntity.setType( CcLabelChartType.Average );

        labelData.addEntity( labelEntity );

        CcLineChartData chartData = new CcLineChartData();
        CcLineChartDataEntity dataEntity1 = new CcLineChartDataEntity( 50, 0, 0 );
        CcLineChartDataEntity dataEntity2 = new CcLineChartDataEntity( 70, 25, 0 );
        CcLineChartDataEntity dataEntity3 = new CcLineChartDataEntity( 60, 33, 0 );
        CcLineChartDataEntity dataEntity4 = new CcLineChartDataEntity( 100, 48, 0 );
        CcLineChartDataEntity dataEntity5 = new CcLineChartDataEntity( 40, 66, 0 );
        CcLineChartDataEntity dataEntity6 = new CcLineChartDataEntity( 0, 72, 0 );
        CcLineChartDataEntity dataEntity7 = new CcLineChartDataEntity( 80, 90, 0 );
        chartData.addEntity( dataEntity1 );
        chartData.addEntity( dataEntity2 );
        chartData.addEntity( dataEntity3 );
        chartData.addEntity( dataEntity4 );
        chartData.addEntity( dataEntity5 );
        chartData.addEntity( dataEntity6 );
        chartData.addEntity( dataEntity7 );
        labelData.setMinValueX( 0 );
        labelData.setMaxValueX( 90 );
        labelData.setMinValueY( 0 );
        labelData.setMaxValueY( 79 );


        CcLineChartDataSet dataSet = new CcLineChartDataSet();
        dataSet.setLabelData( labelData );
        dataSet.addChartData( chartData );
        lcMain = ( CcLineChart ) findViewById( R.id.lc_main );
        lcMain.setData( dataSet );
        labelData.setOnLabelClickListener( new OnLabelClickListener() {
            @Override
            public void onLabelClickListener( CcChartLabelEntity label, int index ) {
                Toast.makeText( MainActivity.this, "" + label.getWords() + "\t" + index, Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void initBarChart() {
        CcBarChartLabel labelData = new CcBarChartLabel();
        CcBarChartLabelEntity entity1 = new CcBarChartLabelEntity( "比赛场次" );
        CcBarChartLabelEntity entity2 = new CcBarChartLabelEntity( "场均用时" );
        CcBarChartLabelEntity entity3 = new CcBarChartLabelEntity( "KDA" );
        CcBarChartLabelEntity entity4 = new CcBarChartLabelEntity( "场均推塔" );
        CcBarChartLabelEntity entity5 = new CcBarChartLabelEntity( "场均大龙" );
        CcBarChartLabelEntity entity6 = new CcBarChartLabelEntity( "场均小龙" );
        labelData.addEntity( entity1 );
        labelData.addEntity( entity2 );
        labelData.addEntity( entity3 );
        labelData.addEntity( entity4 );
        labelData.addEntity( entity5 );
        labelData.addEntity( entity6 );

        CcBarChartData data = new CcBarChartData();
        CcBarChartDataEntity e1 = new CcBarChartDataEntity( 50, 0 );
        CcBarChartDataEntity e2 = new CcBarChartDataEntity( 130, 1 );
        CcBarChartDataEntity e3 = new CcBarChartDataEntity( 80, 2 );
        CcBarChartDataEntity e4 = new CcBarChartDataEntity( 90, 3 );
        CcBarChartDataEntity e5 = new CcBarChartDataEntity( 60, 4 );
        CcBarChartDataEntity e6 = new CcBarChartDataEntity( 65, 5 );
        data.addEntity( e1 );
        data.addEntity( e2 );
        data.addEntity( e3 );
        data.addEntity( e4 );
        data.addEntity( e5 );
        data.addEntity( e6 );
        CcBarChartData data2 = new CcBarChartData();
        CcBarChartDataEntity e21 = new CcBarChartDataEntity( 70, 0 );
        CcBarChartDataEntity e22 = new CcBarChartDataEntity( 70, 1 );
        CcBarChartDataEntity e23 = new CcBarChartDataEntity( 70, 2 );
        CcBarChartDataEntity e24 = new CcBarChartDataEntity( 70, 3 );
        CcBarChartDataEntity e25 = new CcBarChartDataEntity( 70, 4 );
        CcBarChartDataEntity e26 = new CcBarChartDataEntity( 70, 5 );
        data2.addEntity( e21 );
        data2.addEntity( e22 );
        data2.addEntity( e23 );
        data2.addEntity( e24 );
        data2.addEntity( e25 );
        data2.addEntity( e26 );

        CcBarChartDataSet dataSet = new CcBarChartDataSet();
        dataSet.setLabelData( labelData );
        data.setStrokeColor( Color.parseColor( "#F5A623" ) );
        dataSet.addChartData( data );
        data2.setStrokeColor( Color.parseColor( "#7ED321" ) );
        dataSet.addChartData( data2 );
        bcMain = ( CcBarChart ) findViewById( R.id.bc_main );
        bcMain.setData( dataSet );
        labelData.setOnLabelClickListener( new OnLabelClickListener() {
            @Override
            public void onLabelClickListener( CcChartLabelEntity label, int index ) {
                Toast.makeText( MainActivity.this, "" + label.getWords() + "\t" + index, Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void initRadarChart() {
        CcRadarChartLabel labelData = new CcRadarChartLabel();
        labelData.setStyle( CcStyle.Rect );
        labelData.setLabelCount( 3 );
        labelData.setMaxValue( 200 );
        labelData.setMinValue( 60 );
        CcRadarChartLabelEntity chartLabel1 = new CcRadarChartLabelEntity( "补刀" );
        CcRadarChartLabelEntity chartLabel2 = new CcRadarChartLabelEntity( "生存" );
        CcRadarChartLabelEntity chartLabel3 = new CcRadarChartLabelEntity( "突袭" );
        CcRadarChartLabelEntity chartLabel4 = new CcRadarChartLabelEntity( "支援" );
        CcRadarChartLabelEntity chartLabel5 = new CcRadarChartLabelEntity( "控图" );
        CcRadarChartLabelEntity chartLabel6 = new CcRadarChartLabelEntity( "控野" );
        labelData.addEntity( chartLabel1 );
        labelData.addEntity( chartLabel2 );
        labelData.addEntity( chartLabel3 );
        labelData.addEntity( chartLabel4 );
        labelData.addEntity( chartLabel5 );
        labelData.addEntity( chartLabel6 );

        CcRadarChartData datas = new CcRadarChartData();
        CcRadarChartDataEntity data1 = new CcRadarChartDataEntity( 150, 0 );
        CcRadarChartDataEntity data2 = new CcRadarChartDataEntity( 120, 1 );
        CcRadarChartDataEntity data3 = new CcRadarChartDataEntity( 70, 2 );
        CcRadarChartDataEntity data4 = new CcRadarChartDataEntity( 110, 3 );
        CcRadarChartDataEntity data5 = new CcRadarChartDataEntity( 90, 4 );
        CcRadarChartDataEntity data6 = new CcRadarChartDataEntity( 90, 5 );
        datas.addEntity( data1 );
        datas.addEntity( data2 );
        datas.addEntity( data3 );
        datas.addEntity( data4 );
        datas.addEntity( data5 );
        datas.addEntity( data6 );
        datas.setDrawFill( true );
        datas.setFillColor( Color.RED );

        CcRadarChartData datas2 = new CcRadarChartData();
        CcRadarChartDataEntity data21 = new CcRadarChartDataEntity( 100, 0 );
        CcRadarChartDataEntity data22 = new CcRadarChartDataEntity( 80, 1 );
        CcRadarChartDataEntity data23 = new CcRadarChartDataEntity( 90, 2 );
        CcRadarChartDataEntity data24 = new CcRadarChartDataEntity( 70, 3 );
        CcRadarChartDataEntity data25 = new CcRadarChartDataEntity( 110, 4 );
        CcRadarChartDataEntity data26 = new CcRadarChartDataEntity( 90, 5 );
        datas2.addEntity( data21 );
        datas2.addEntity( data22 );
        datas2.addEntity( data23 );
        datas2.addEntity( data24 );
        datas2.addEntity( data25 );
        datas2.addEntity( data26 );
        datas2.setDrawFill( true );

        CcRadarChartDataSet dataSet = new CcRadarChartDataSet();
        dataSet.setLabelData( labelData );
        dataSet.addChartData( datas );
        dataSet.addChartData( datas2 );

        rcMain = ( CcRadarChart ) findViewById( R.id.rc_main );

        rcMain.setData( dataSet );
        rcMain.setAnimation( CcAnimation.ToLarge, 500 );
        labelData.setOnLabelClickListener( new OnLabelClickListener() {
            @Override
            public void onLabelClickListener( CcChartLabelEntity label, int index ) {
                Toast.makeText( MainActivity.this, "" + label.getWords() + "\t" + index, Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}

