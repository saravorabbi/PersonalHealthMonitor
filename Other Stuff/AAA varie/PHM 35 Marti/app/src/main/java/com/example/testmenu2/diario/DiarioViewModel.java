package com.example.testmenu2.diario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applandeo.materialcalendarview.EventDay;
import com.example.testmenu2.Database.Report;
import com.example.testmenu2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiarioViewModel extends ViewModel {

    private static MutableLiveData<String> SGiorno;
    public static Calendar calendar;
    public static SimpleDateFormat SDF;

    public DiarioViewModel() {
        calendar = Calendar.getInstance();
        SDF = new SimpleDateFormat("dd/MM/yy");
        SGiorno = new MutableLiveData<>();
        SGiorno.setValue(SDF.format(calendar.getTime()));

    }

}