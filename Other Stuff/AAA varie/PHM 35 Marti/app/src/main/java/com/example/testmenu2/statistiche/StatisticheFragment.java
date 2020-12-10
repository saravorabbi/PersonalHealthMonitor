package com.example.testmenu2.statistiche;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.testmenu2.Database.AVG;
import com.example.testmenu2.Database.Report;
import com.example.testmenu2.Database.ReportViewModel;
import com.example.testmenu2.R;
import com.example.testmenu2.Utilities.Converters;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.Date;
import java.util.List;

public class StatisticheFragment extends Fragment {

    private StatisticheViewModel statisticheViewModel;
    private Button BTNsettimana, BTNmese, BTNanno, BTNtutti;
    private TextView TXVPeriodo, TXVNumReport;
    private static LiveData<List<Report>> mReports;
    private static LiveData<List<AVG>> battitoAVG, pressioneAVG, temperaturaAVG, glicemiaAVG;
    private ReportViewModel reportViewModel;
    private PieChart pieChart;
    private LineChart battitoChart, temperaturaChart, pressioneChart, glicemiaChart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticheViewModel =
                new ViewModelProvider(this).get(StatisticheViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistiche, container, false);

        BTNsettimana = root.findViewById(R.id.BTNsettimana);
        BTNmese = root.findViewById(R.id.BTNmese);
        BTNanno = root.findViewById(R.id.BTNanno);
        BTNtutti = root.findViewById(R.id.BTNtutti);
        TXVPeriodo = root.findViewById(R.id.TXVPeriodo_val);
        TXVNumReport = root.findViewById(R.id.TXVNumeroReport_val);

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        UpdateDate("Settimana");


        BTNsettimana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate("Settimana");
            }
        });

        BTNmese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate("Mese");
            }
        });

        BTNanno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate("Anno");
            }
        });

        BTNtutti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate("Tutto");
            }
        });

        pieChart = root.findViewById(R.id.piechart);
        battitoChart = root.findViewById(R.id.battitochart);
        temperaturaChart = root.findViewById(R.id.temperaturachart);
        pressioneChart = root.findViewById(R.id.pressioneochart);
        glicemiaChart = root.findViewById(R.id.glicemiachart);

        return root;
    }

    //DISEGNA IL GRAFICO A TORTA IN BASE AI DATI CONTENUTI NEI REPORT REGISTRATI TRA LE DUE DATE IN INPUT
   private void UpdateDate(String periodo){

        setChartData(periodo);


        //MODIFICO E OSSERVO LA LISTA DI REPORT IN BASE ALLA QUERY EFFETTUATA
        mReports.observe(getViewLifecycleOwner(),new Observer<List<Report>>() {
           @Override
           public void onChanged(List<Report> reports) {
               //AGGIORNO L'ETICHETTA CHE CONTA I REPORT DEL PERIODO
               TXVNumReport.setText(String.valueOf(reports.size()));

               //DISEGNO IL GRAFICO A TORTA IN BASE ALLA LISTA DEI REPORT RICEVUTI
               pieChart.setData(statisticheViewModel.getPieData(reports));
               pieChart.setDescription(statisticheViewModel.getDescription(""));
               pieChart.animateXY(1000, 1000);
               //pieChart.invalidate();
           }
        });

        battitoAVG.observe(getViewLifecycleOwner(), new Observer<List<AVG>>() {
            @Override
            public void onChanged(List<AVG> avgs) {
                setLineChart(battitoChart, avgs);
            }
        });

       pressioneAVG.observe(getViewLifecycleOwner(), new Observer<List<AVG>>() {
           @Override
           public void onChanged(List<AVG> avgs) {
               setLineChart(pressioneChart, avgs);
           }
       });

       temperaturaAVG.observe(getViewLifecycleOwner(), new Observer<List<AVG>>() {
           @Override
           public void onChanged(List<AVG> avgs) {
               setLineChart(temperaturaChart, avgs);
           }
       });

       glicemiaAVG.observe(getViewLifecycleOwner(), new Observer<List<AVG>>() {
           @Override
           public void onChanged(List<AVG> avgs) {
               setLineChart(glicemiaChart, avgs);
           }
       });
    }

    private void setLineChart(LineChart lineChart, List<AVG> avgs){
        lineChart.setData(statisticheViewModel.getLineData(avgs));
        lineChart.setDescription(statisticheViewModel.getDescription(""));
        lineChart.animateXY(1000, 1000);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
    }

    private void setChartData(String periodo){
        statisticheViewModel.setPeriodo(periodo); //imposto le date del periodo
        Date inizio = statisticheViewModel.getInizio(); //prendo le date del periodo
        Date fine = statisticheViewModel.getFine();

        //SE NON FORNISCO UN PERIODO ALLORA PRENDO TUTTI I REPORT SALVATI NEL DB
        if (periodo == "Tutto"){
            TXVPeriodo.setText(periodo);
            mReports = reportViewModel.getAllReports();
            battitoAVG = reportViewModel.getAVGAll("Battito");
            pressioneAVG = reportViewModel.getAVGAll("Pressione");
            temperaturaAVG = reportViewModel.getAVGAll("Temperatura");
            glicemiaAVG = reportViewModel.getAVGAll("Glicemia");

        }
        //ALTRIMENTI PRENDO SOLO I REPORT DI QUEL DETERMINATO PERIODO
        else {
            TXVPeriodo.setText("Dal " + Converters.DateToString(inizio) + " al " + Converters.DateToString(fine));
            mReports = reportViewModel.getAllReportsInPeriod(inizio, fine);
            battitoAVG = reportViewModel.getAVGInPeriod("Battito", inizio, fine);
            pressioneAVG =  reportViewModel.getAVGInPeriod("Pressione", inizio, fine);
            temperaturaAVG = reportViewModel.getAVGInPeriod("Temperatura", inizio, fine);
            glicemiaAVG = reportViewModel.getAVGInPeriod("Glicemia", inizio, fine);

        }
    }

}