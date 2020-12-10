package Database.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.Report;

import com.example.phm.MainActivity;
import com.example.phm.R;

import java.text.DateFormat;
import java.util.Date;

public class NewReportActivity extends AppCompatActivity {

    //VARIABILI
    private EditText ETbattiti, ETpressione, ETtemperatura, ETglicemia, ETnote;
    private Button BTNNuovoReport;
    private double battiti, pressione, temperatura, glicemia;
    private String nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        ETbattiti = findViewById(R.id.ETbattiti);
        ETpressione = findViewById(R.id.ETpressione);
        ETtemperatura = findViewById(R.id.ETtemperatura);
        ETglicemia = findViewById(R.id.ETglicemia);
        ETnote = findViewById(R.id.ETnote);
        BTNNuovoReport = findViewById(R.id.Btn_newReport);

        //QUANDO CLICCHI SU "SALVA REPORT"
        BTNNuovoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check2input()){
                    Report report = new Report(0, /*java.text.DateFormat.getDateTimeInstance().format(new Date())*/ java.text.DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()),battiti,temperatura,pressione,glicemia, nota);

                    MainActivity.reportViewModel.setReport(report);
                    Toast.makeText(getApplication(), "Report salvato", Toast.LENGTH_SHORT).show();
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

        if(!Sbattiti.matches("")){
            try{
                battiti = Double.parseDouble(Sbattiti);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per i battiti non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!Spressione.matches("")){
            try {
                pressione = Double.parseDouble(Spressione);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la pressione non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!Stemperatura.matches("")){
            try{
                temperatura = Double.parseDouble(Stemperatura);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la temperatura non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!Sglicemia.matches("")){
            try {
                glicemia = Double.parseDouble(Sglicemia);
            }
            catch(NumberFormatException e){
                Toast.makeText(getApplication(), "Il valore per la glicemia non è numerico positivo", Toast.LENGTH_SHORT).show();
                return false;
            }
            count++;
        }
        if(!ETnote.getText().toString().matches("")){
            nota = Snote;
            count++;
        }

        if(count >= 2) return true;
        else{
            Toast.makeText(getApplication(), "Inserisci almeno due valori", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}