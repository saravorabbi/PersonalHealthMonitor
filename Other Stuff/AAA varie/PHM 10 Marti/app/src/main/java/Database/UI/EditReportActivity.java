package Database.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phm.MainActivity;
import com.example.phm.R;

import java.text.DateFormat;
import java.util.Date;

import Database.Report;
import Database.ReportViewModel;

public class EditReportActivity extends AppCompatActivity {

    //VARIABILI
    Bundle bundle;
    int reportId;
    LiveData<Report> report;
    private EditText ETbattiti, ETpressione, ETtemperatura, ETglicemia, ETnote;
    private Button BTNEditReport;
    private double battiti, pressione, temperatura, glicemia;
    private String nota, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);

        ETbattiti = findViewById(R.id.ETbattiti);
        ETpressione = findViewById(R.id.ETpressione);
        ETtemperatura = findViewById(R.id.ETtemperatura);
        ETglicemia = findViewById(R.id.ETglicemia);
        ETnote = findViewById(R.id.ETnote);
        BTNEditReport = findViewById(R.id.Btn_editReport);

        bundle = getIntent().getExtras();
        if(bundle!= null){
            reportId = bundle.getInt("report_id");
            Log.i("ID: ", Integer.toString(reportId));
        }
        else{
            Log.i("Errore", "Non arriva il bundle");
        }

        MainActivity.reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        report = MainActivity.reportViewModel.getReport(reportId);



        report.observe(this, new Observer<Report>() {
            @Override
            public void onChanged(@Nullable Report report) {
                ETbattiti.setText(nullValue(report.getBattito()));
                ETpressione.setText(nullValue(report.getPressione()));
                ETtemperatura.setText(nullValue(report.getTemperatura()));
                ETglicemia.setText(nullValue(report.getGlicemia()));
                ETnote.setText(nullString(report.getNota()));
            }
        });

        BTNEditReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check2input()){
                    Report report = new Report(reportId, /*java.text.DateFormat.getDateTimeInstance().format(new Date())*/ java.text.DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()),battiti,temperatura,pressione,glicemia, nota);

                    MainActivity.reportViewModel.updateReport(report);
                    Toast.makeText(getApplication(), "Report modificato", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    /*public void updateReport(View view){
        //String updateReport = ETDescrizione.getText().toString();
        //Report report = new Report(reportId, updateReport);
        //MainActivity.reportViewModel.updateReport(report);
        Toast.makeText(getApplication(), "Report modificato", Toast.LENGTH_SHORT).show();

        finish();
    }

     */

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

class SnackbarUndo implements View.OnClickListener {

    Report reportRimosso;

    @Override
    public void onClick(View v) {

        MainActivity.reportViewModel.setReport(reportRimosso);

    }

    public void reportRimosso(Report report) {
        this.reportRimosso = report;
    }
}