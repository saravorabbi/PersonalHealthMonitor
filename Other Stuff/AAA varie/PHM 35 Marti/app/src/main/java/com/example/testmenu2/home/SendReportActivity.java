package com.example.testmenu2.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testmenu2.Database.Report;
import com.example.testmenu2.Database.ReportViewModel;
import com.example.testmenu2.R;
import com.example.testmenu2.Utilities.Converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.testmenu2.home.HomeFragment.reportViewModel;

public class SendReportActivity extends AppCompatActivity {

    //VARIABILI
    private Bundle bundle;
    private int reportId;
    private LiveData<Report> LDReport;
    private EditText ETbattiti, ETpressione, ETtemperatura, ETglicemia, ETnote;
    private Button BTNSendReport;
    private double battiti, pressione, temperatura, glicemia;
    private String nota, ora;
    private Report Report;
    private boolean newReport;
    private Date giorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        newReport= false;
        ETbattiti = findViewById(R.id.ETbattiti);
        ETpressione = findViewById(R.id.ETpressione);
        ETtemperatura = findViewById(R.id.ETtemperatura);
        ETglicemia = findViewById(R.id.ETglicemia);
        ETnote = findViewById(R.id.ETnote);
        BTNSendReport = findViewById(R.id.Btn_sendReport);

        bundle = getIntent().getExtras();
        if(bundle!= null){
            this.setTitle("Modifica report");
            reportId = bundle.getInt("report_id");
            BTNSendReport.setText("Aggiorna i dati");

            reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
            LDReport = reportViewModel.getReport(reportId);

            LDReport.observe(this, new Observer<Report>() {
                @Override
                public void onChanged(@Nullable Report report) {
                    giorno = report.getGiorno();
                    ora = report.getOra();
                    ETbattiti.setText(nullValue(report.getBattito()));
                    ETpressione.setText(nullValue(report.getPressione()));
                    ETtemperatura.setText(nullValue(report.getTemperatura()));
                    ETglicemia.setText(nullValue(report.getGlicemia()));
                    ETnote.setText(nullString(report.getNota()));
                }
            });
        }
        else{
            newReport = true;
            this.setTitle("Aggiungi un nuovo report");
            BTNSendReport.setText("Invia dati");
        }



        BTNSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check2input()){

                    if(newReport){
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
                        giorno = Converters.StringToDate(SDF.format(calendar.getTime())); //java.text.DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALIAN).format(new Date());
                        ora = java.text.DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ITALIAN).format(new Date());

                        Report report = new Report(0, giorno, ora, battiti,temperatura,pressione,glicemia, nota);

                        reportViewModel.setReport(report);
                        Toast.makeText(getApplication(), "Report salvato", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Report = new Report(reportId, giorno, ora, battiti,temperatura,pressione,glicemia, nota);
                        reportViewModel.updateReport(Report);
                        Toast.makeText(getApplication(), "Report modificato", Toast.LENGTH_SHORT).show();

                    }
                    finish();
                }
            }
        });
    }



    //CONTROLLA CHE SIANO STATI INSERITI ALMENO DUE VALORI - SE TRUE ALLORA ALMENO DUE VALORI SONO IMPOSTATI CORRETTAMENTE, ALTRIMENTI FALSE
    private boolean check2input (){
        int count = 0;
        String Sbattiti = ETbattiti.getText().toString();
        String Spressione = ETpressione.getText().toString();
        String Stemperatura = ETtemperatura.getText().toString();
        String Sglicemia = ETglicemia.getText().toString();
        String Snote = ETnote.getText().toString();

        if(!(Sbattiti.matches("") || Sbattiti.equals("null"))){
            try{
                battiti = Double.parseDouble(Sbattiti);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per i battiti non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!(Spressione.matches("") || Spressione.equals("null"))){
            try {
                pressione = Double.parseDouble(Spressione);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la pressione non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!(Stemperatura.matches("") || Stemperatura.equals("null"))){
            try{
                temperatura = Double.parseDouble(Stemperatura);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la temperatura non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!(Sglicemia.matches("") || Sglicemia.equals("null"))){
            try {
                glicemia = Double.parseDouble(Sglicemia);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la glicemia non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!(Snote.matches("") || Snote.equals("Nessuna info aggiuntiva"))){
            nota = Snote;
            count++;
        }

        if(count >= 2) return true;
        else{
            Toast.makeText(getApplication(), "Inserisci almeno due valori", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //SE NON HO INSERITO DEI VALORI == 0 ALLORA STAMPO NULL A SCHERMO
    private String nullValue(double val){
        if(val == 0){
            return "null";
        }
        return String.valueOf(val);
    }

    //SE LE INFORMAZIONI AGGIUNTIVE SONO VUOTE SCRIVO NULL
    private String nullString(String nota){
        if(nota == null) return "Nessuna info aggiuntiva";
        else return nota;
    }
}