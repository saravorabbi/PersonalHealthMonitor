package com.example.personalhealthmonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;
import static com.example.personalhealthmonitor.utils.Utils.*;

public class DataFilterActivity extends AppCompatActivity {

    public static final String TAG = "DataFilterActivity";

    private TextView reportPriorityTV;
    private TextView personalDateBegin, personalDateEnd;

    //valori che servono a reperire valori dal DB
    private String prioritaString;
    private String inizioDataUI, fineDataUI;
    private String inizioDataDB, fineDataDB;

    //parametri selezionati
    private LinearLayout linearLayoutParams;
    private TextView textViewParams;

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

        //parametri selezionati
        linearLayoutParams = findViewById(R.id.data_filter_parametri_layout);
        textViewParams = findViewById(R.id.data_filter_parametri);

        //recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //data inizio e fine - nel formato "dd/mm/yyyy" per la UI
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfUI = new SimpleDateFormat("dd/MM/yyyy");
        inizioDataUI = sdfUI.format(cal.getTime());
        fineDataUI = sdfUI.format(cal.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDB = new SimpleDateFormat("MM/dd/yyyy");
        inizioDataDB = sdfDB.format(cal.getTime());
        fineDataDB = sdfDB.format(cal.getTime());


        //priorità
        prioritaString = "1";

        //setto i dati nelle text view
        personalDateBegin.setText(inizioDataUI);
        personalDateEnd.setText(fineDataUI);
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

                //salvo data inizio in una var globale
                inizioDataDB = getDataSettings(anno, mese, giorno);
                inizioDataUI = getDataDisplayUI(anno, mese, giorno);

                personalDateBegin.setText(inizioDataUI);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        personalDateEnd.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {

                //salvo data fine in una var globale
                fineDataDB = getDataSettings(anno, mese, giorno);
                fineDataUI = getDataDisplayUI(anno, mese, giorno);

                personalDateEnd.setText(fineDataUI);

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

            //mostro i parametri con priorità >= a prioritaString (l'importanza)
            settingsViewModel.getParametersFromValue(Integer.parseInt(prioritaString)).observeForever(parametersList -> {

                String param = "";

                if(parametersList.size() == 0){
                    linearLayoutParams.setVisibility(View.GONE);
                }else{

                    for (int j = 0; j < parametersList.size(); j++){
                        Log.i(TAG, "PARAMETER = " + parametersList.get(j));

                        param = param + getParameterName(parametersList.get(j));
                        if( j != (parametersList.size() - 1) ){
                            param = param + " - ";
                        }
                    }

                    textViewParams.setText(param);
                    linearLayoutParams.setVisibility(View.VISIBLE);
                }
            });

            dialogInterface.dismiss();
        });

        builder.show();
    }

    //funzione che cambia la lista dei report visualizzati in base ai parametri inseriti dall'utente
    public void changeListReport(View view){

        //check date
        if (!checkDateSettings(inizioDataDB, fineDataDB)){
            Snackbar.make(view, "Le date sono sbagliate, controlla di nuovo!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }


        //input: la priorità scelta dall'utente -> ritorna: la lista di parametri con value >= priorità
        settingsViewModel.getParametersFromValue(Integer.parseInt(prioritaString)).observeForever(parametersList -> {

            //input: parametersList = lista di parametri che voglio vedere perché con value >= della priorità indicata dall'utente
            infoViewModel.getReportFromFilter(parametersList, inizioDataDB, fineDataDB).observeForever(infos -> {

                //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                infoListAdapter.setInfos(infos);

            });
        });

    }

}