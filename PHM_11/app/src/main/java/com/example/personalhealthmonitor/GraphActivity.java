package com.example.personalhealthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.personalhealthmonitor.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafo_prova);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        barChart = findViewById(R.id.bar_chart_temperatura);

        ArrayList<BarEntry> dati = new ArrayList<>();
        dati.add(new BarEntry(2013, 420));
        dati.add(new BarEntry(2014, 530));
        dati.add(new BarEntry(2015, 520));
        dati.add(new BarEntry(2016, 620));
        dati.add(new BarEntry(2017, 470));
        dati.add(new BarEntry(2018, 280));
        dati.add(new BarEntry(2019, 390));
        dati.add(new BarEntry(2020, 450));

        BarDataSet barDataSet = new BarDataSet(dati, "Temperatura");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Esempio Bar Chart");
        barChart.animateY(2000);




/*
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}