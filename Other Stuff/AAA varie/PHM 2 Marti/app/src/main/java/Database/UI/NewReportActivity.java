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

public class NewReportActivity extends AppCompatActivity {

    //VARIABILI
    private EditText ETId, ETDescrizione;
    private Button BTNNuovoReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        ETId = findViewById(R.id.ETid);
        ETDescrizione = findViewById(R.id.ETdescrizione);
        BTNNuovoReport = findViewById(R.id.Btn_newReport);

        //QUANDO CLICCHI SU "SALVA REPORT"
        BTNNuovoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int reportid = Integer.parseInt(ETId.getText().toString());
                String reportdescrizione = ETDescrizione.getText().toString();

                Report report = new Report();
                report.setId(reportid);
                report.setDescrizione(reportdescrizione);


                //MainActivity.db.reportDao().addReport(report);
                MainActivity.reportViewModel.setReport(report);
                Toast.makeText(getApplication(), "Report salvato", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}