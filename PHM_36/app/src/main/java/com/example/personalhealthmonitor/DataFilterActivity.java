package com.example.personalhealthmonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;
import static com.example.personalhealthmonitor.utils.Utils.*;

public class DataFilterActivity extends AppCompatActivity {

    private TextView reportPriorityTV;
    private TextView personalDateBegin, personalDateEnd;

    //valori che servono a reperire valori dal DB
    private String prioritaString;
    private String inizioData, fineData;

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

        //recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //data inizio e fine - nel formato "dd/mm/yyyy"
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        inizioData = sdf.format(cal.getTime());
        fineData = sdf.format(cal.getTime());

        //priorità
        prioritaString = "1";

        //setto i dati nelle text view
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


        //===================//
        // ON CLICK LISTENER //
        //===================//

        reportPriorityTV.setOnClickListener(view -> showRadioOption());

        personalDateBegin.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {

                String dataGlobal = getDataSettings(anno, mese, giorno);
                String dataUI = getDataDisplayUI(anno, mese, giorno);

                //salvo data inizio in una var globale
                inizioData = dataGlobal;
                personalDateBegin.setText(dataUI);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        personalDateEnd.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {

                String dataGlobal = getDataSettings(anno, mese, giorno);
                String dataUI = getDataDisplayUI(anno, mese, giorno);

                //salvo data fine in una var globale
                fineData = dataGlobal;
                personalDateEnd.setText(dataUI);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

    }//end on create


    //mostra un pop-up che permette di scegliere la priorità dei report da visualizzare
    private void showRadioOption() {
        String[] priority = {"1", "2", "3", "4", "5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DataFilterActivity.this);
        builder.setTitle("Scegli la priorità");
        builder.setSingleChoiceItems(priority, (Integer.parseInt(prioritaString)-1) , (dialogInterface, i) -> {

            //salvo la priorità selezionata in una var globale
            prioritaString = priority[i];
            reportPriorityTV.setText(prioritaString);

            Toast.makeText(DataFilterActivity.this, "Priorità selezionata: " + prioritaString, Toast.LENGTH_SHORT).show();
            dialogInterface.dismiss();
        });

        builder.show();
    }

    //funzione che cambia la lista dei report visualizzati in base ai parametri inseriti dall'utente
    public void changeListReport(View view){

        //check date
        if (!checkDateSettings(inizioData, fineData)){
            Snackbar.make(view, "Le date sono sbagliate, controlla di nuovo!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        //input: la priorità scelta dall'utente
        settingsViewModel.getParametersFromValue(Integer.parseInt(prioritaString)).observeForever(parametersList -> {

            //input: parametersList = lista di parametri che voglio vedere perché con value >= della priorità indicata dall'utente
            infoViewModel.getReportFromFilter(parametersList, inizioData, fineData).observeForever(infos -> {

                //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                infoListAdapter.setInfos(infos);

            });
        });

    }

}