package com.example.personalhealthmonitor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;

import static com.example.personalhealthmonitor.utils.Utils.*;

public class EditInfoActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG: EditInfoAdapter";

    private EditText temperaturaEdit, pressioneSISEdit, pressioneDIAEdit, glicemiaEdit, pesoEdit, notaEdit;
    private Double[] parametri;
    InfoViewModel infoModel;

    private Bundle bundle;
    private String infoId;
    private String infoData;
    private LiveData<Info> report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Log.i(DEBUG, "sono nella EDIT NOTE ACTIVITY");

        //collego id
        temperaturaEdit = findViewById(R.id.editTemperatura);
        pressioneSISEdit = findViewById(R.id.editPressioneSistolica);
        pressioneDIAEdit = findViewById(R.id.editPressioneDiastolica);
        glicemiaEdit = findViewById(R.id.editGlicemia);
        pesoEdit = findViewById(R.id.editPeso);
        notaEdit = findViewById(R.id.editNota);

        //view model
        infoModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        //fetch dei dati che ho passato dalla InfoListAdapter
        bundle = getIntent().getExtras();
        if (bundle != null){
            //assegno l'id del report che voglio editare/eliminare
            infoId = bundle.getString("info_id");
            infoData = bundle.getString("info_data");
        }

        //vettore usato per inserire i dati nel DB
        parametri = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};

        report = infoModel.getInfoFromID(infoId);

        //ora inserisco tutti i campo del reportpreso dal DB nel mio layout
        report.observe(this, new Observer<Info>() {
            @Override
            public void onChanged(Info info) {

                temperaturaEdit.setText( String.valueOf(info.getTemperatura()) );
                pressioneSISEdit.setText( String.valueOf(info.getPressioneSistolica()) );
                pressioneDIAEdit.setText( String.valueOf(info.getPressioneDiastolica()) );
                glicemiaEdit.setText( String.valueOf(info.getGlicemia()) );
                pesoEdit.setText( String.valueOf(info.getPeso()) );
                notaEdit.setText( info.getNota() );
            }
        });


    }

    //Funzioni on click

    //update del report
    public void updateNote(View view){

        boolean bool = isCorrect(
                temperaturaEdit.getText().toString(),
                pressioneSISEdit.getText().toString(),
                pressioneDIAEdit.getText().toString(),
                glicemiaEdit.getText().toString(),
                pesoEdit.getText().toString(),
                getApplicationContext(),
                parametri
        );

        if( bool ) {

            //prendo i dati dagli edit text
            double updateTemp = parametri[0];
            double updatePressSis = parametri[1];
            double updatePressDia = parametri[2];
            double updateGlic =  parametri[3];
            double updatePeso =  parametri[4];
            String updateNota = notaEdit.getText().toString();

            //new report per mandare il risultato nel database
            Info info = new Info(infoId, updateTemp, updatePressSis, updatePressDia, updateGlic, updatePeso, updateNota, infoData);

            //update della nota in modo asincrono //passiamo la nota all'update method della ViewModel
            infoModel.update(info);

            finish();
        }
    }

    //torna indietro e annulla le modifiche
    public void cancelUpdate(View view){
        //infoModel.delete(info);
        finish();
    }

}
