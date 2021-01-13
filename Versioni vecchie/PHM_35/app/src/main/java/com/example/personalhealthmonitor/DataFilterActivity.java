package com.example.personalhealthmonitor;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.example.personalhealthmonitor.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;

public class DataFilterActivity extends AppCompatActivity {

    private static final String TAG = "DataFilterActivity";

    private TextView reportPriorityTV;
    private TextView personalDateBegin, personalDateEnd;
//    private Button showInfo;


    //valori che servono a reperire valori dal DB
    private String prioritaString;
    private String inizioData, fineData;

    private RecyclerView recyclerView;
    private InfoListAdapter infoListAdapter;

    //date picker dei parametri
    private int YEAR;
    private int MONTH;
    private int DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filter);

        //ID
        reportPriorityTV = findViewById(R.id.filter_importanza);
        personalDateBegin = findViewById(R.id.filter_data_begin);
        personalDateEnd = findViewById(R.id.filter_data_end);
//        showInfo = findViewById(R.id.filter_mostra_report);

        //recycler view
        recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Creo la UI

        //data inizio e fine - nel formato "dd/mm/yyyy"
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        inizioData = sdf.format(cal.getTime());
        fineData = sdf.format(cal.getTime());
        //priorità
        prioritaString = "1";

        //SETTO I DATI NElle text view
        personalDateBegin.setText(inizioData);
        personalDateEnd.setText(fineData);
        reportPriorityTV.setText(prioritaString);

        //inizializzo i valori necessari ai datepicker
        YEAR = cal.get(Calendar.YEAR);
        MONTH = cal.get(Calendar.MONTH);
        DATE = cal.get(Calendar.DAY_OF_MONTH);


        //DB
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);




        //CANCELLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //parametersList = settingsViewModel.getParametersFromValue(Integer.parseInt(priorita));
        Log.i(TAG, "RIPPERONEEEEEEEEEEEEE");
        //deve essere svolto in un thread in background
        //settingsViewModel.getParametersFromValue(Integer.parseInt(priorita));
        //parametersList =
        Log.i(TAG, "RIPPERONEEEEEEEEEEEEE");



        //===================//
        // ON CLICK LISTENER //
        //===================//

        reportPriorityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioOption();
            }
        });


        personalDateBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {

                    String data = Utils.getDataSettings(anno, mese, giorno);

                    //salvo data inizio in una var globale
                    inizioData = data;
                    personalDateBegin.setText(data);

                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        personalDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                    String data = Utils.getDataSettings(anno, mese, giorno);

                    //salvo data fine in una var globale
                    fineData = data;
                    personalDateEnd.setText(data);

                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });




    }//end on create


    //mostra un pop-up che permette di scegliere la priorità dei report da visualizzare
    private void showRadioOption() {
        String[] priority = {"1", "2", "3", "4", "5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DataFilterActivity.this);
        builder.setTitle("Scegli la priorità");
        builder.setSingleChoiceItems(priority, (Integer.parseInt(prioritaString)-1) , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //salvo la priorità selezionata in una var globale
                prioritaString = priority[i];
                reportPriorityTV.setText(prioritaString);

                Toast.makeText(DataFilterActivity.this, "Priorità selezionata: " + prioritaString, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    //
    public void changeListReport(View view){
        //todo chiama il list adapter e mostra la lista di report

        Log.i(TAG,"changeListReport" );

        //check date
        if (!Utils.checkDateSettings(inizioData, fineData)){
            Snackbar.make(view, "Le date sono sbagliate, controlla di nuovo!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        settingsViewModel.getParametersFromValue(Integer.parseInt(prioritaString)).observeForever(new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> parametersList) {

                //parametersList = lista di parametri che voglio vedere perché con value>= della priorità indicata dall'utente

                infoViewModel.getReportFromFilter(parametersList, inizioData, fineData).observeForever(new Observer<List<Info>>() {
                    @Override
                    public void onChanged(List<Info> infos) {
                        //infos = lista dei report da mostrare

                        //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                        infoListAdapter.setInfos(infos);

                    }
                });

            }
        });

    }

}













