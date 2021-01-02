package com.example.personalhealthmonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.utils.Utils.*;

public class EditInfoActivity extends AppCompatActivity {

    private EditText temperaturaEdit, pressioneSISEdit, pressioneDIAEdit, glicemiaEdit, pesoEdit, notaEdit;
    private Double[] parametri;

    private String infoId;
    private String infoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //id
        temperaturaEdit = findViewById(R.id.editTemperatura);
        pressioneSISEdit = findViewById(R.id.editPressioneSistolica);
        pressioneDIAEdit = findViewById(R.id.editPressioneDiastolica);
        glicemiaEdit = findViewById(R.id.editGlicemia);
        pesoEdit = findViewById(R.id.editPeso);
        notaEdit = findViewById(R.id.editNota);

        //view model
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        //fetch dei dati che ho passato dalla InfoListAdapter
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //assegno l'id del report che voglio editare/eliminare
            infoId = bundle.getString("info_id");
            infoData = bundle.getString("info_data");
        }

        //vettore usato per inserire i dati nel DB
        parametri = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};

        LiveData<Info> report = infoViewModel.getInfoFromID(infoId);

        //inserisco tutti i campi del report preso dal DB nel mio layout
        report.observe(this, info -> {

            temperaturaEdit.setText( String.valueOf(info.getTemperatura()) );
            pressioneSISEdit.setText( String.valueOf(info.getPressioneSistolica()) );
            pressioneDIAEdit.setText( String.valueOf(info.getPressioneDiastolica()) );
            glicemiaEdit.setText( String.valueOf(info.getGlicemia()) );
            pesoEdit.setText( String.valueOf(info.getPeso()) );
            notaEdit.setText( info.getNota() );
        });

    }

    //=================//
    //Funzioni on click//
    //=================//

    //update del report
    public void updateNote(View view){

        //controllo che i valori inseriti siano corretti
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

            //dati da inserire nel DB
            double updateTemp = parametri[0];
            double updatePressSis = parametri[1];
            double updatePressDia = parametri[2];
            double updateGlic =  parametri[3];
            double updatePeso =  parametri[4];
            String updateNota = notaEdit.getText().toString();

            Info info = new Info(infoId, updateTemp, updatePressSis, updatePressDia, updateGlic, updatePeso, updateNota, infoData);

            //update della nota
            infoViewModel.update(info);

            finish();
        }
    }

    //torna indietro e annulla tutte le modifiche
    public void cancelUpdate(View view){
        finish();
    }

}
