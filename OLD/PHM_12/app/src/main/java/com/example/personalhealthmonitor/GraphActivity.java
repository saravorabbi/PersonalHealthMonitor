package com.example.personalhealthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.personalhealthmonitor.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG: GraphActivity";

    private TextView lineChartTV, barChartTV;
    private BarChart barChartTemp, barChartPressSis, barChartPressDia, barChartGlicemia, barChartPeso;
    private LineChart lineChartTemp, lineChartPressSis, lineChartPressDia, lineChartGlicemia, lineChartPeso;

    //grafi
//    //array dei BarEntry (coppie da inserire nel DB)
//    private ArrayList<BarEntry> barEntryArrayListTemp;
    //array dei valori da mettere nell'asse X
    private final ArrayList<String> xAxisLabel = new ArrayList<>();
//    //insieme di
//    private BarDataSet barDataSetTemperatura;



    //DB
    public static InfoViewModel infoViewModelGraph;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafo_prova);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        //id pulsanti per decidere quale grafo vedere
        lineChartTV = findViewById(R.id.show_line_chart);
        barChartTV = findViewById(R.id.show_bar_chart);

        //id Bar Chart
        barChartTemp = findViewById(R.id.bar_chart_temperatura);
        barChartPeso = findViewById(R.id.bar_chart_peso);

        //id Line Chart
        //TODO

//        barEntryArrayListTemp = new ArrayList<>();

        //DB recupero dati temperatura
        infoViewModelGraph = ViewModelProviders.of(this).get(InfoViewModel.class);
        Log.i(TAG, "infoViewModelGraph 1");



        //prendo temperature dal DB
        infoViewModelGraph.getReportFromTemperature().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> tempList) {

                ArrayList<BarEntry> barEntryArrayListTemp = new ArrayList<>();

                double tempDB;
                String dataDB;

                Log.i(TAG, "Sono dentro il matrix");
                for(int i = 0; i < tempList.size(); i++){

                    tempDB = tempList.get(i).getTemperatura();
                    dataDB = tempList.get(i).getData().substring(0, 5);

                    Log.i(TAG, i + " : temperatura = " + tempDB);
                    Log.i(TAG, i + " : data = " + dataDB);
//                    double tmp = tempList.get(i);
//                    Log.i(TAG, "Temperatura " + i + " : " + tmp);


                    //prova inserimento nel grafo
                    barEntryArrayListTemp.add(new BarEntry( (float) i, (float) tempDB));
                    xAxisLabel.add(dataDB);
                }

                Log.i(TAG, "Sono qui? 1");
                // Bar Data Set

                BarDataSet barDataSetTemperatura = new BarDataSet(barEntryArrayListTemp, "Temperatura");
                barDataSetTemperatura.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetTemperatura.setValueTextColor(Color.BLACK);
                barDataSetTemperatura.setValueTextSize(16f);

                Log.i(TAG, "Sono qui? 2");

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataTemp = new BarData(barDataSetTemperatura);
                //setta i dati dentro il grafico
                barChartTemp.setData(barDataTemp);

                Log.i(TAG, "Sono qui? 3");

                barChartTemp.setFitBars(true);
                barChartTemp.getDescription().setEnabled(false);
                //tempo dell'animazione del grafo
                barChartTemp.animateY(1000);
                barChartTemp.setDragEnabled(true);

                Log.i(TAG, "Sono qui? 4");

                XAxis xAxis = barChartTemp.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(true);
                xAxis.setGranularity(1);
                xAxis.setGranularityEnabled(true);
                barChartTemp.setTouchEnabled(true);
                barChartTemp.setPinchZoom(true);
                barChartTemp.setScaleEnabled(true);
                barChartTemp.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));


            }
        });


//        //prendo peso dal DB
//        infoViewModelGraph.getReportFromPeso().observe(this, new Observer<List<Double>>() {
//            @Override
//            public void onChanged(List<Double> pesoList) {
//
//                ArrayList<BarEntry> barEntryArrayListPeso = new ArrayList<>();
//
//                Log.i(TAG, "Sono dentro il matrix");
//                for(int i = 0; i < pesoList.size(); i++){
//                    Log.i(TAG, "ciclo OK = " + i);
//                    double peso = pesoList.get(i);
//                    Log.i(TAG, "Peso " + i + " : " + peso);
//
//
//                    //prova inserimento nel grafo
//                    barEntryArrayListPeso.add(new BarEntry( (float) i, (float) peso));
//                    xAxisLabel.add(i + "£");
//                }
//
//                Log.i(TAG, "Sono qui? 1");
//                // Bar Data Set
//                BarDataSet barDataSetPeso = new BarDataSet(barEntryArrayListPeso, "Peso");
//                barDataSetPeso.setColors(ColorTemplate.MATERIAL_COLORS);
//                barDataSetPeso.setValueTextColor(Color.BLACK);
//                barDataSetPeso.setValueTextSize(16f);
//
//                Log.i(TAG, "Sono qui? 2");
//
//                // Bar Data - dati che voglio inserire nel grafo
//                BarData barDataPeso = new BarData(barDataSetPeso);
//                //setta i dati dentro il grafico
//                barChartPeso.setData(barDataPeso);
//
//                Log.i(TAG, "Sono qui? 3");
//
//                barChartPeso.setFitBars(true);
//                barChartPeso.getDescription().setEnabled(false);
//                //tempo dell'animazione del grafo
//                barChartPeso.animateY(1000);
//                barChartPeso.setDragEnabled(true);
//
//                Log.i(TAG, "Sono qui? 4");
//
//                XAxis xAxis = barChartPeso.getXAxis();
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                xAxis.setDrawGridLines(true);
//                xAxis.setGranularity(1);
//                xAxis.setGranularityEnabled(true);
//                barChartPeso.setTouchEnabled(true);
//                barChartPeso.setPinchZoom(true);
//                barChartPeso.setScaleEnabled(true);
//                barChartPeso.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//
//            }
//        });






        lineChartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        barChartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });




    }



}