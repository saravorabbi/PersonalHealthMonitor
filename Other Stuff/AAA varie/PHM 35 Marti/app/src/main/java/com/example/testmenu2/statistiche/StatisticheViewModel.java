package com.example.testmenu2.statistiche;

import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.testmenu2.Database.AVG;
import com.example.testmenu2.Database.Report;
import com.example.testmenu2.Utilities.Converters;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticheViewModel extends ViewModel {

    private SimpleDateFormat SDF;
    private String periodo;
    private ArrayList<Entry> BattitoVals;
    private Date inizio, fine;

    public StatisticheViewModel() {
        SDF = new SimpleDateFormat("dd/MM/yyyy");
        inizio = new Date();
        fine = new Date();
        BattitoVals = new ArrayList<Entry>();
    }

    //MEMORIZZO LA DATA INIZIALE E FINALE DEL PERIODO - SE NULL ALLORA è TUTTO
    public void setDates(Date date1, Date date2){
        //Log.i("setDates", "dal "+date1.toString() + " al "+ date2.toString());

        this.inizio = date1;
        this.fine = date2;
    }

    //RESTITUISCO LA DATA DI INIZIO
    public Date getInizio(){
        return inizio;
    }

    //RESTITUISCO LA DATA DI FINE
    public Date getFine(){
        return fine;
    }

    //Imposto il PieChart
    public PieData getPieData(List<Report> reports){
        PieDataSet pieDataSet = new PieDataSet(setDataValues(reports), periodo);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.BLACK);
        PieData pieData = new PieData(pieDataSet);
        return pieData;
    }
    //Aggiungo i contenuti nel PieChart
    private ArrayList<PieEntry> setDataValues(List<Report> reports){
        ArrayList<PieEntry> PiedataVals = new ArrayList<>();
        //IMPOSTO IL PIECHART
        int battito = 0, pressione = 0, temperatura = 0, glicemia = 0;

        for (int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            if(report.getBattito() != 0) battito++;
            if(report.getPressione() != 0) pressione++;
            if(report.getTemperatura() != 0) temperatura++;
            if(report.getGlicemia() != 0) glicemia++;
        }

        PiedataVals.add(new PieEntry(battito, "Battito"));
        PiedataVals.add(new PieEntry(temperatura, "Temperatura"));
        PiedataVals.add(new PieEntry(pressione, "Pressione"));
        PiedataVals.add(new PieEntry(glicemia, "Glicemia"));
        return PiedataVals;
    }



    public LineData getLineData(List<AVG> avgs){
        LineDataSet lineDataSet = null;
        lineDataSet = new LineDataSet(getLineDataValues(avgs), periodo);

        //lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.BLACK);
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    public ArrayList<Entry> getLineDataValues(List<AVG> avgs){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        int i = 0;
        int p = 1;

        Calendar calendarInizio = Calendar.getInstance();
        Calendar calendarFine = Calendar.getInstance();
        Calendar calendarGiorno = Calendar.getInstance();

        if(inizio != null && fine != null){
            calendarInizio.setTime(inizio);
            calendarFine.setTime(fine);
            calendarFine.add(Calendar.DATE, 1);

            while (!calendarInizio.equals(calendarFine) && i<avgs.size()){
                AVG avg = avgs.get(i);
                calendarGiorno.setTime(avg.getGiorno());
                if(calendarInizio.equals(calendarGiorno)){
                    dataVals.add(new Entry(p, (float) avg.getMedia()));
                    i++;
                }
                calendarInizio.add(Calendar.DATE, 1);
                p++;
            }


        }
        else {
            //Log.i("BATTITO","TUTTO");

            calendarGiorno.setTime(avgs.get(i).getGiorno());
            calendarInizio.setTime(avgs.get(i).getGiorno());
            calendarFine.setTime(avgs.get((avgs.size())-1).getGiorno());
            calendarFine.add(Calendar.DATE, 1);

            while (!calendarInizio.equals(calendarFine) && i<avgs.size()){
                AVG avg = avgs.get(i);
                calendarGiorno.setTime(avg.getGiorno());
                if(calendarInizio.equals(calendarGiorno)){
                    dataVals.add(new Entry(p, (float) avg.getMedia()));
                    i++;
                }
                calendarInizio.add(Calendar.DATE, 1);
                p++;
            }
        }

        return dataVals;
    }

    public Description getDescription(String s){
        Description description = new Description();
        description.setText(s);

        return description;
    }

    public void setPeriodo(String periodo){
        //Log.i("setPeriodo: ", periodo);

        this.periodo = periodo;
        switch (periodo){
            case "Settimana": setDates(PrimoGiornoSettimana(), UltimoGiornoSettimana());
                break;
            case "Mese": setDates(PrimoGiornoMese(), UltimoGiornoMese());
                break;
            case "Anno": setDates(PrimoGiornoAnno(), UltimoGiornoAnno());
                break;
            case "Tutto": setDates(null, null);
                break;
        }
    }

    public Date PrimoGiornoSettimana (){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

    public Date UltimoGiornoSettimana(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE, 6);
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

    public Date PrimoGiornoMese(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

    public Date UltimoGiornoMese(){
        Calendar calendar = Calendar.getInstance();
        int ultimo = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, ultimo);
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

    public Date PrimoGiornoAnno(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

    public Date UltimoGiornoAnno(){
        Calendar calendar = Calendar.getInstance();
        int ultimo = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, ultimo);
        Date giorno = Converters.StringToDate(SDF.format(calendar.getTime()));
        return giorno;
    }

}