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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
        barChartPressSis = findViewById(R.id.bar_chart_pressione_sistolica);
        barChartPressDia = findViewById(R.id.bar_chart_pressione_diastolica);
        barChartGlicemia = findViewById(R.id.bar_chart_glicemia);
        barChartPeso = findViewById(R.id.bar_chart_peso);

        //id Line Chart
        lineChartTemp = findViewById(R.id.line_chart_temperatura);
        lineChartPressSis = findViewById(R.id.line_chart_pressione_sistolica);
        lineChartPressDia = findViewById(R.id.line_chart_pressione_diastolica);
        lineChartGlicemia = findViewById(R.id.line_chart_glicemia);
        lineChartPeso = findViewById(R.id.line_chart_peso);

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
                String idDB;

                Log.i(TAG, "Sono dentro il matrix");
                for(int i = 0; i < tempList.size(); i++){

                    //prendo i valori nel DB
                    tempDB = tempList.get(i).getTemperatura();
                    idDB =  tempList.get(i).getId();
                    dataDB = tempList.get(i).getData()/*.substring(0, 5)*/;

                    //Log.i(TAG, i + " : temperatura = " + tempDB);
                    Log.i(TAG, i + " : data = " + dataDB + " id = " + idDB);

                    //inserisco valori negli array
                        //barEntryArrayListTemp.add( new BarEntry((float)i,(float)tempDB) );
                        //xAxisLabel.add(dataDB);
                }
/*
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

*/
            }
        });




        ArrayList<Entry> lineEntryArrayListTemp = new ArrayList<>();
        lineEntryArrayListTemp.add(new Entry( (float) 1, (float) 37));
        lineEntryArrayListTemp.add(new Entry( (float) 2, (float) 36));
        lineEntryArrayListTemp.add(new Entry( (float) 3, (float) 36));
        lineEntryArrayListTemp.add(new Entry( (float) 4, (float) 35));
        lineEntryArrayListTemp.add(new Entry( (float) 5, (float) 38));
        lineEntryArrayListTemp.add(new Entry( (float) 6, (float) 39));

        LineDataSet lineDataSetTemperatura = new LineDataSet(lineEntryArrayListTemp, "Temperatura");

        lineDataSetTemperatura.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSetTemperatura.setValueTextColor(Color.BLACK);
        lineDataSetTemperatura.setValueTextSize(16f);

        // Bar Data - dati che voglio inserire nel grafo
        LineData lineDataTemp = new LineData(lineDataSetTemperatura);
        //setta i dati dentro il grafico
        lineChartTemp.setData(lineDataTemp);

//
//        lineChartTemp.getDescription().setEnabled(false);
//        //tempo dell'animazione del grafo
//        lineChartTemp.animateY(1000);
//        lineChartTemp.setDragEnabled(true);
//
//        Log.i(TAG, "Sono qui? 4");
//
//        XAxis xAxis = barChartPeso.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(true);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//        barChartPeso.setTouchEnabled(true);
//        barChartPeso.setPinchZoom(true);
//        barChartPeso.setScaleEnabled(true);
//        barChartPeso.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//





        lineChartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChartTemp.setVisibility(View.GONE);
                barChartPressSis.setVisibility(View.GONE);
                barChartPressDia.setVisibility(View.GONE);
                barChartGlicemia.setVisibility(View.GONE);
                barChartPeso.setVisibility(View.GONE);

                lineChartTemp.setVisibility(View.VISIBLE);
                lineChartPressSis.setVisibility(View.VISIBLE);
                lineChartPressDia.setVisibility(View.VISIBLE);
                lineChartGlicemia.setVisibility(View.VISIBLE);
                lineChartPeso.setVisibility(View.VISIBLE);

                Snackbar.make(view, "Ora vediamo l'istogramma", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        barChartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lineChartTemp.setVisibility(View.GONE);
                lineChartPressSis.setVisibility(View.GONE);
                lineChartPressDia.setVisibility(View.GONE);
                lineChartGlicemia.setVisibility(View.GONE);
                lineChartPeso.setVisibility(View.GONE);

                barChartTemp.setVisibility(View.VISIBLE);
                barChartPressSis.setVisibility(View.VISIBLE);
                barChartPressDia.setVisibility(View.VISIBLE);
                barChartGlicemia.setVisibility(View.VISIBLE);
                barChartPeso.setVisibility(View.VISIBLE);

                Snackbar.make(view, "Grafico Lineare!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });




    }



}