package com.example.testchart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = findViewById(R.id.piechart);

        PieDataSet pieDataSet = new PieDataSet(dataValues1(), "PieDataSet");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();

    }

    private ArrayList<PieEntry> dataValues1(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();

        dataVals.add(new PieEntry(15, "Sun"));
        dataVals.add(new PieEntry(34, "Mon"));
        dataVals.add(new PieEntry(23, "Tue"));
        dataVals.add(new PieEntry(86, "Wed"));
        dataVals.add(new PieEntry(26, "Thu"));
        dataVals.add(new PieEntry(17, "Fri"));
        dataVals.add(new PieEntry(63, "Sat"));
        return dataVals;
    }
}