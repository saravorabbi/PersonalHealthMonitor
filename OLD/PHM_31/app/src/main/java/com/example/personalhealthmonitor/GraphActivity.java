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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG: GraphActivity";

    private TextView lineChartTV, barChartTV;
    private BarChart barChartTemp, barChartPressSis, barChartPressDia, barChartGlicemia, barChartPeso;
    private LineChart lineChartTemp, lineChartPressSis, lineChartPressDia, lineChartGlicemia, lineChartPeso;

    //DB
    public static InfoViewModel infoViewModelGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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



        //TEMPERATURA
        infoViewModelGraph.getReportFromTemperature().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> tempList) {

                ArrayList<BarEntry> barEntryArrayListTemp = new ArrayList<>();
                ArrayList<Entry> lineEntryArrayListTemp = new ArrayList<>();

                ArrayList<String> asseXBarArrayList = new ArrayList<>(); // forse ne basta uno di array???
                ArrayList<String> asseXLineArrayList = new ArrayList<>();

                XAxis asseXBar, asseXLine;

                double tempDB;
                String dataDB;
                String idDB;


                for(int i = 0; i < tempList.size(); i++){

                    // Prendo i valori nel DB
                    tempDB = tempList.get(i).getTemperatura();
                    idDB =  tempList.get(i).getId();
                    dataDB = tempList.get(i).getData().substring(0, 5);

                    //Log.i(TAG, i + " : temperatura = " + tempDB);
                    Log.i(TAG, i + " : data = " + dataDB + " id = " + idDB + " temperatura = " + tempDB);

                    // Inserisco valori negli array
                    barEntryArrayListTemp.add( new BarEntry((float)i,(float)tempDB) );
                    asseXBarArrayList.add(dataDB);

                    lineEntryArrayListTemp.add(new Entry( (float) i, (float) tempDB));
                    asseXLineArrayList.add(dataDB);
                }

                // =========================== //
                // Setto i dati nel LINE CHART //
                // =========================== //

                // Line Data Set
                LineDataSet lineDataSetTemperatura = new LineDataSet(lineEntryArrayListTemp, TEMPERATURA);
                lineDataSetTemperatura.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSetTemperatura.setValueTextColor(Color.BLACK);
                lineDataSetTemperatura.setValueTextSize(12f);
                lineDataSetTemperatura.setLineWidth(3); //spessore della linea nel grafico

                // Line Data - dati che voglio inserire nel grafo
                LineData lineDataTemp = new LineData(lineDataSetTemperatura);
                lineChartTemp.setData(lineDataTemp); //setta i dati dentro il grafico

                lineChartTemp.getDescription().setEnabled(false);
                lineChartTemp.animateY(1000); //tempo dell'animazione del grafo
                lineChartTemp.setTouchEnabled(true);
                lineChartTemp.setPinchZoom(true);
                lineChartTemp.setScaleEnabled(true);
                lineChartTemp.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXLine = lineChartTemp.getXAxis();
                asseXLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXLine.setDrawGridLines(true);
                asseXLine.setGranularity(1);
                asseXLine.setGranularityEnabled(true);

                //setto la data nell'asse x
                lineChartTemp.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));

                // ========================== //
                // Setto i dati nel BAR CHART
                // ========================== //

                // Bar Data Set
                BarDataSet barDataSetTemperatura = new BarDataSet(barEntryArrayListTemp, TEMPERATURA);
                barDataSetTemperatura.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetTemperatura.setValueTextColor(Color.BLACK);
                barDataSetTemperatura.setValueTextSize(12f);

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataTemp = new BarData(barDataSetTemperatura);
                barChartTemp.setData(barDataTemp);  //setta i dati dentro il grafico

                //caratteristiche della view grafo
                barChartTemp.getDescription().setEnabled(false);    //descrizione disattivata
                barChartTemp.animateY(1000);    //tempo dell'animazione del grafo
                barChartTemp.setTouchEnabled(true);
                barChartTemp.setPinchZoom(true);
                barChartTemp.setScaleEnabled(true);
                barChartTemp.setFitBars(true);
                barChartTemp.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXBar = barChartTemp.getXAxis();
                asseXBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXBar.setDrawGridLines(true);
                asseXBar.setGranularity(1);
                asseXBar.setGranularityEnabled(true);

                //setto la data nell'asse x
                barChartTemp.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXBarArrayList));
            }
        });


        //PRESSIONE SISTOLICA
        infoViewModelGraph.getReportFromPressioneSistolica().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> pressList) {

                ArrayList<BarEntry> barEntryArrayListPessSIS = new ArrayList<>();
                ArrayList<Entry> lineEntryArrayListPessSIS = new ArrayList<>();

                ArrayList<String> asseXBarArrayList = new ArrayList<>(); // forse ne basta uno di array???
                ArrayList<String> asseXLineArrayList = new ArrayList<>();

                XAxis asseXBar, asseXLine;

                double pressSisDB;
                String dataDB;
                String idDB;

                //recupero i dati da inserire nel grafo dalla lista restituita dalla query
                for(int i = 0; i < pressList.size(); i++){

                    // Prendo i valori nel DB
                    pressSisDB = pressList.get(i).getPressioneSistolica();
                    idDB =  pressList.get(i).getId();
                    dataDB = pressList.get(i).getData().substring(0, 5);


                    Log.i(TAG, i + " : data = " + dataDB + " id = " + idDB + " pressSis = " + pressSisDB);

                    // Inserisco valori negli array
                    barEntryArrayListPessSIS.add( new BarEntry((float)i,(float)pressSisDB) );
                    asseXBarArrayList.add(dataDB);

                    lineEntryArrayListPessSIS.add(new Entry( (float) i, (float) pressSisDB));
                    asseXLineArrayList.add(dataDB);
                }

                // =========================== //
                // Setto i dati nel LINE CHART //
                // =========================== //

                // Line Data Set
                LineDataSet lineDataSetPressSis = new LineDataSet(lineEntryArrayListPessSIS, PRESSIONE_SISTOLICA);
                lineDataSetPressSis.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSetPressSis.setValueTextColor(Color.BLACK);
                lineDataSetPressSis.setValueTextSize(12f);
                lineDataSetPressSis.setLineWidth(3); //spessore della linea nel grafico

                // Line Data - dati che voglio inserire nel grafo
                LineData lineDataPressSis = new LineData(lineDataSetPressSis);
                lineChartPressSis.setData(lineDataPressSis); //setta i dati dentro il grafico

                //caratteristiche della view grafo
                lineChartPressSis.getDescription().setEnabled(false);
                lineChartPressSis.animateY(1000); //tempo dell'animazione del grafo
                lineChartPressSis.setTouchEnabled(true);
                lineChartPressSis.setPinchZoom(true);
                lineChartPressSis.setScaleEnabled(true);
                lineChartPressSis.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXLine = lineChartPressSis.getXAxis();
                asseXLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXLine.setDrawGridLines(true);
                asseXLine.setGranularity(1);
                asseXLine.setGranularityEnabled(true);

                //setto la data nell'asse x
                lineChartPressSis.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));

                // ========================== //
                // Setto i dati nel BAR CHART //
                // ========================== //

                // Bar Data Set
                BarDataSet barDataSetPressSis = new BarDataSet(barEntryArrayListPessSIS, PRESSIONE_SISTOLICA);
                barDataSetPressSis.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetPressSis.setValueTextColor(Color.BLACK);
                barDataSetPressSis.setValueTextSize(12f);

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataPressSis = new BarData(barDataSetPressSis);
                barChartPressSis.setData(barDataPressSis);  //setta i dati dentro il grafico

                //caratteristiche della view grafo
                barChartPressSis.getDescription().setEnabled(false);    //descrizione disattivata
                barChartPressSis.animateY(1000);    //tempo dell'animazione del grafo
                barChartPressSis.setTouchEnabled(true);
                barChartPressSis.setPinchZoom(true);
                barChartPressSis.setScaleEnabled(true);
                barChartPressSis.setFitBars(true);
                barChartPressSis.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXBar = barChartPressSis.getXAxis();
                asseXBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXBar.setDrawGridLines(true);
                asseXBar.setGranularity(1);
                asseXBar.setGranularityEnabled(true);

                //setto la data nell'asse x
                barChartPressSis.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));
            }
        });


        //PRESSIONE DIASTOLICA
        infoViewModelGraph.getReportFromPressioneDiastolica().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> pressList) {

                ArrayList<BarEntry> barEntryArrayListPessDIA = new ArrayList<>();
                ArrayList<Entry> lineEntryArrayListPessDIA = new ArrayList<>();

                ArrayList<String> asseXBarArrayList = new ArrayList<>(); // forse ne basta uno di array???
                ArrayList<String> asseXLineArrayList = new ArrayList<>();

                XAxis asseXBar, asseXLine;

                double pressDiaDB;
                String dataDB;
                String idDB;

                //recupero i dati da inserire nel grafo dalla lista restituita dalla query
                for(int i = 0; i < pressList.size(); i++){

                    // Prendo i valori nel DB
                    pressDiaDB = pressList.get(i).getPressioneDiastolica();
                    idDB =  pressList.get(i).getId();
                    dataDB = pressList.get(i).getData().substring(0, 5);


                    Log.i(TAG, i + ": pressDia = " + pressDiaDB + " data = " + dataDB + " id = " + idDB);

                    // Inserisco valori negli array
                    barEntryArrayListPessDIA.add( new BarEntry((float)i,(float)pressDiaDB) );
                    asseXBarArrayList.add(dataDB);

                    lineEntryArrayListPessDIA.add(new Entry( (float) i, (float) pressDiaDB));
                    asseXLineArrayList.add(dataDB);
                }

                // =========================== //
                // Setto i dati nel LINE CHART //
                // =========================== //

                // Line Data Set
                LineDataSet lineDataSetPressDia = new LineDataSet(lineEntryArrayListPessDIA, PRESSIONE_DIASTOLICA);
                lineDataSetPressDia.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSetPressDia.setValueTextColor(Color.BLACK);
                lineDataSetPressDia.setValueTextSize(12f);
                lineDataSetPressDia.setLineWidth(3); //spessore della linea nel grafico

                // Line Data - dati che voglio inserire nel grafo
                LineData lineDataPressDia = new LineData(lineDataSetPressDia);
                lineChartPressDia.setData(lineDataPressDia); //setta i dati dentro il grafico

                //caratteristiche della view grafo
                lineChartPressDia.getDescription().setEnabled(false);
                lineChartPressDia.animateY(1000); //tempo dell'animazione del grafo
                lineChartPressDia.setTouchEnabled(true);
                lineChartPressDia.setPinchZoom(true);
                lineChartPressDia.setScaleEnabled(true);
                lineChartPressDia.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXLine = lineChartPressDia.getXAxis();
                asseXLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXLine.setDrawGridLines(true);
                asseXLine.setGranularity(1);
                asseXLine.setGranularityEnabled(true);

                //setto la data nell'asse x
                lineChartPressDia.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));

                // ========================== //
                // Setto i dati nel BAR CHART //
                // ========================== //

                // Bar Data Set
                BarDataSet barDataSetPressDia = new BarDataSet(barEntryArrayListPessDIA, PRESSIONE_DIASTOLICA);
                barDataSetPressDia.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetPressDia.setValueTextColor(Color.BLACK);
                barDataSetPressDia.setValueTextSize(12f);

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataPressDia = new BarData(barDataSetPressDia);
                barChartPressDia.setData(barDataPressDia);  //setta i dati dentro il grafico

                //caratteristiche della view grafo
                barChartPressDia.getDescription().setEnabled(false);    //descrizione disattivata
                barChartPressDia.animateY(1000);    //tempo dell'animazione del grafo
                barChartPressDia.setTouchEnabled(true);
                barChartPressDia.setPinchZoom(true);
                barChartPressDia.setScaleEnabled(true);
                barChartPressDia.setFitBars(true);
                barChartPressDia.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXBar = barChartPressDia.getXAxis();
                asseXBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXBar.setDrawGridLines(true);
                asseXBar.setGranularity(1);
                asseXBar.setGranularityEnabled(true);

                //setto la data nell'asse x
                barChartPressDia.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));
            }
        });


        //GLICEMIA
        infoViewModelGraph.getReportFromGlicemia().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> glicList) {

                ArrayList<BarEntry> barEntryArrayListGlic = new ArrayList<>();
                ArrayList<Entry> lineEntryArrayListGlic = new ArrayList<>();

                ArrayList<String> asseXBarArrayList = new ArrayList<>(); // forse ne basta uno di array???
                ArrayList<String> asseXLineArrayList = new ArrayList<>();

                XAxis asseXBar, asseXLine;

                double glicDB;
                String dataDB;

                //recupero i dati da inserire nel grafo dalla lista restituita dalla query
                for(int i = 0; i < glicList.size(); i++){

                    // Prendo i valori nel DB
                    glicDB = glicList.get(i).getGlicemia();
                    dataDB = glicList.get(i).getData().substring(0, 5);

                    Log.i(TAG, i + ": glicemia = " + glicDB + " data = " + dataDB);

                    // Inserisco valori negli array
                    barEntryArrayListGlic.add( new BarEntry((float)i, (float)glicDB));
                    asseXBarArrayList.add(dataDB);

                    lineEntryArrayListGlic.add(new Entry( (float) i, (float)glicDB));
                    asseXLineArrayList.add(dataDB);
                }

                // =========================== //
                // Setto i dati nel LINE CHART //
                // =========================== //

                // Line Data Set
                LineDataSet lineDataSetGlic = new LineDataSet(lineEntryArrayListGlic, GLICEMIA);
                lineDataSetGlic.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSetGlic.setValueTextColor(Color.BLACK);
                lineDataSetGlic.setValueTextSize(12f);
                lineDataSetGlic.setLineWidth(3); //spessore della linea nel grafico

                // Line Data - dati che voglio inserire nel grafo
                LineData lineDataGlic = new LineData(lineDataSetGlic);
                lineChartGlicemia.setData(lineDataGlic); //setta i dati dentro il grafico

                //caratteristiche della view grafo
                lineChartGlicemia.getDescription().setEnabled(false);
                lineChartGlicemia.animateY(1000); //tempo dell'animazione del grafo
                lineChartGlicemia.setTouchEnabled(true);
                lineChartGlicemia.setPinchZoom(true);
                lineChartGlicemia.setScaleEnabled(true);
                lineChartGlicemia.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXLine = lineChartGlicemia.getXAxis();
                asseXLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXLine.setDrawGridLines(true);
                asseXLine.setGranularity(1);
                asseXLine.setGranularityEnabled(true);

                //setto la data nell'asse x
                lineChartGlicemia.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));

                // ========================== //
                // Setto i dati nel BAR CHART //
                // ========================== //

                // Bar Data Set
                BarDataSet barDataSetGlic = new BarDataSet(barEntryArrayListGlic, GLICEMIA);
                barDataSetGlic.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetGlic.setValueTextColor(Color.BLACK);
                barDataSetGlic.setValueTextSize(12f);

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataGlic = new BarData(barDataSetGlic);
                barChartGlicemia.setData(barDataGlic);  //setta i dati dentro il grafico

                //caratteristiche della view grafo
                barChartGlicemia.getDescription().setEnabled(false);    //descrizione disattivata
                barChartGlicemia.animateY(1000);    //tempo dell'animazione del grafo
                barChartGlicemia.setTouchEnabled(true);
                barChartGlicemia.setPinchZoom(true);
                barChartGlicemia.setScaleEnabled(true);
                barChartGlicemia.setFitBars(true);
                barChartGlicemia.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXBar = barChartGlicemia.getXAxis();
                asseXBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXBar.setDrawGridLines(true);
                asseXBar.setGranularity(1);
                asseXBar.setGranularityEnabled(true);

                //setto la data nell'asse x
                barChartGlicemia.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));
            }
        });


        //PESO
        infoViewModelGraph.getReportFromPeso().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> pesoList) {

                ArrayList<BarEntry> barEntryArrayListPeso = new ArrayList<>();
                ArrayList<Entry> lineEntryArrayListPeso = new ArrayList<>();

                ArrayList<String> asseXBarArrayList = new ArrayList<>(); // forse ne basta uno di array???
                ArrayList<String> asseXLineArrayList = new ArrayList<>();

                XAxis asseXBar, asseXLine;

                double pesoDB;
                String dataDB;

                //recupero i dati da inserire nel grafo dalla lista restituita dalla query
                for(int i = 0; i < pesoList.size(); i++){

                    // Prendo i valori nel DB
                    pesoDB = pesoList.get(i).getPeso();
                    dataDB = pesoList.get(i).getData().substring(0, 5);

                    Log.i(TAG, i + ": peso = " + pesoDB + " data = " + dataDB);

                    // Inserisco valori negli array
                    barEntryArrayListPeso.add( new BarEntry((float)i, (float)pesoDB));
                    asseXBarArrayList.add(dataDB);

                    lineEntryArrayListPeso.add(new Entry( (float) i, (float)pesoDB));
                    asseXLineArrayList.add(dataDB);
                }

                // =========================== //
                // Setto i dati nel LINE CHART //
                // =========================== //

                // Line Data Set
                LineDataSet lineDataSetPeso = new LineDataSet(lineEntryArrayListPeso, PESO);
                lineDataSetPeso.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSetPeso.setValueTextColor(Color.BLACK);
                lineDataSetPeso.setValueTextSize(12f);
                lineDataSetPeso.setLineWidth(3); //spessore della linea nel grafico

                // Line Data - dati che voglio inserire nel grafo
                LineData lineDataPeso = new LineData(lineDataSetPeso);
                lineChartPeso.setData(lineDataPeso); //setta i dati dentro il grafico

                //caratteristiche della view grafo
                lineChartPeso.getDescription().setEnabled(false);
                lineChartPeso.animateY(1000); //tempo dell'animazione del grafo
                lineChartPeso.setTouchEnabled(true);
                lineChartPeso.setPinchZoom(true);
                lineChartPeso.setScaleEnabled(true);
                lineChartPeso.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXLine = lineChartPeso.getXAxis();
                asseXLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXLine.setDrawGridLines(true);
                asseXLine.setGranularity(1);
                asseXLine.setGranularityEnabled(true);

                //setto la data nell'asse x
                lineChartPeso.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));


                // ========================== //
                // Setto i dati nel BAR CHART //
                // ========================== //

                // Bar Data Set
                BarDataSet barDataSetPeso = new BarDataSet(barEntryArrayListPeso, PESO);
                barDataSetPeso.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSetPeso.setValueTextColor(Color.BLACK);
                barDataSetPeso.setValueTextSize(12f);

                // Bar Data - dati che voglio inserire nel grafo
                BarData barDataPeso = new BarData(barDataSetPeso);
                barChartPeso.setData(barDataPeso);  //setta i dati dentro il grafico

                //caratteristiche della view grafo
                barChartPeso.getDescription().setEnabled(false);    //descrizione disattivata
                barChartPeso.animateY(1000);    //tempo dell'animazione del grafo
                barChartPeso.setTouchEnabled(true);
                barChartPeso.setPinchZoom(true);
                barChartPeso.setScaleEnabled(true);
                barChartPeso.setFitBars(true);
                barChartPeso.setDragEnabled(true);

                //caratteristiche dell'asse x
                asseXBar = barChartPeso.getXAxis();
                asseXBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                asseXBar.setDrawGridLines(true);
                asseXBar.setGranularity(1);
                asseXBar.setGranularityEnabled(true);

                //setto la data nell'asse x
                barChartPeso.getXAxis().setValueFormatter(new IndexAxisValueFormatter(asseXLineArrayList));
            }
        });



        //onClick "Line Graph" -> nasconde il bar chart e nasconde il line chart
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
            }
        });

        //onClick "Bar Graph" -> nasconde il line chart e mostra il bar chart
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
            }
        });




    }




}