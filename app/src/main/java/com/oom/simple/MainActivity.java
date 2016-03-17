package com.oom.simple;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.oom.cccharts.model.CcRadarChartData;
import com.oom.cccharts.model.CcRadarChartDataEntity;
import com.oom.cccharts.model.CcRadarChartDataSet;
import com.oom.cccharts.model.CcRadarChartLabel;
import com.oom.cccharts.model.CcRadarChartLabel.CcStyle;
import com.oom.cccharts.model.CcRadarChartLabelEntity;
import com.oom.cccharts.view.widget.CcRadarChart;
import com.oom.cccharts.view.widget.CcRadarChart.OnLabelClickListener;

public class MainActivity extends AppCompatActivity {

    private CcRadarChart rcMain;
    private CcRadarChartLabel labelData;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initRadarChart();
    }

    private void initRadarChart() {
        labelData = new CcRadarChartLabel();
        labelData.setStyle( CcStyle.Rect );
        labelData.setLabelCount( 3 );
        labelData.setMaxValue( 200 );
        labelData.setMinValue( 60 );
        CcRadarChartLabelEntity chartLabel1 = new CcRadarChartLabelEntity();
        chartLabel1.setWords( "补刀" );
        chartLabel1.setTextSize( 60 );
        CcRadarChartLabelEntity chartLabel2 = new CcRadarChartLabelEntity();
        chartLabel2.setWords( "生存" );
        chartLabel2.setTextColor( Color.CYAN );
        CcRadarChartLabelEntity chartLabel3 = new CcRadarChartLabelEntity();
        chartLabel3.setWords( "突袭" );
        CcRadarChartLabelEntity chartLabel4 = new CcRadarChartLabelEntity();
        chartLabel4.setWords( "支援" );
        CcRadarChartLabelEntity chartLabel5 = new CcRadarChartLabelEntity();
        chartLabel5.setWords( "控图" );
        CcRadarChartLabelEntity chartLabel6 = new CcRadarChartLabelEntity();
        chartLabel6.setWords( "控野" );
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
        dataSet.addRadarChartData( datas );
        dataSet.addRadarChartData( datas2 );

        rcMain = ( CcRadarChart ) findViewById( R.id.rc_main );
        rcMain.setData( dataSet );
        rcMain.setOnLabelClickListener( new OnLabelClickListener() {
            @Override
            public void onLabelClickListener( CcRadarChartLabelEntity label, int index ) {
                Toast.makeText( MainActivity.this, "label: " + label.getWords() + "\nindex: " + index, Toast.LENGTH_SHORT ).show();
            }
        } );
//        rcMain.invalidate();
    }
}

