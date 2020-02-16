package com.example.diana.licentaschelet.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.diana.licentaschelet.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Statistics extends Activity {

    TextView tvToday;
    TextView tvGoal;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvToday=findViewById(R.id.tvKcalTodaySettings);
        tvGoal = findViewById(R.id.tvKcalGoalSettings);
        graph = findViewById(R.id.graph);

        sharedPreferences = getSharedPreferences("SharedPreferences",0);
        tvToday.setText(String.valueOf(sharedPreferences.getFloat("kcalToday",0)));
        tvGoal.setText(String.valueOf(sharedPreferences.getFloat("kcalGoal",0)));

        drawGraph();


    }

    private void drawGraph(){

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(1, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(2, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(3, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(4, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(5, sharedPreferences.getFloat("kcalGoal",0)),
                new DataPoint(6, sharedPreferences.getFloat("kcalGoal",0)),

        });
        series.setDrawBackground(true);
        series.setAnimated(true);
        series.setDrawDataPoints(true);
        series.setTitle("Goal");

        graph.addSeries(series);

        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, sharedPreferences.getFloat("kcalToday",0)),
                new DataPoint(3, 1200),
                new DataPoint(5, 980),
                new DataPoint(6, 1500)
        });
        series2.setDataWidth(1d);
        series2.setSpacing(50);
        series2.setAnimated(true);
        series2.setDrawValuesOnTop(true);
        series2.setTitle("Kcal/day");
        series2.setColor(Color.argb(255, 60, 200, 128));

        graph.addSeries(series2);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/

    }
}
