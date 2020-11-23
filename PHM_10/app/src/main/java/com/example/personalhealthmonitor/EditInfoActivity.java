package com.example.personalhealthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditInfoActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG: EditInfoAdapter";

    private EditText temperaturaEdit, pressioneEdit, glicemiaEdit, pesoEdit, notaEdit;
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
        pressioneEdit = findViewById(R.id.editPressione);
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

        report = infoModel.getInfoFromID(infoId);

        //ora inserisco tutti i campo del reportpreso dal DB nel mio layout
        report.observe(this, new Observer<Info>() {
            @Override
            public void onChanged(Info info) {

                temperaturaEdit.setText( String.valueOf(info.getTemperatura()) );
                pressioneEdit.setText( String.valueOf(info.getPressione()) );
                glicemiaEdit.setText( String.valueOf(info.getGlicemia()) );
                pesoEdit.setText( String.valueOf(info.getPeso()) );
                notaEdit.setText( info.getNota() );
            }
        });


    }

    //Funzioni on click

    //update del report
    public void updateNote(View view){
        //prendo i dati dagli edit text

        double updateTemp = Double.parseDouble( temperaturaEdit.getText().toString() );
        double updatePress =  Double.parseDouble( pressioneEdit.getText().toString() );
        double updateGlic =  Double.parseDouble( glicemiaEdit.getText().toString() );
        double updatePeso =  Double.parseDouble( pesoEdit.getText().toString() );
        String updateNota = notaEdit.getText().toString();


        //new Intent per mandare il risultato nel database
        Info info = new Info(infoId, updateTemp, updatePress, updateGlic, updatePeso, updateNota, infoData);

        //update della nota in modo asincrono //passiamo la nota all'update method della ViewModel
        infoModel.update(info);
        //Toast.makeText(getApplicationContext(),"Inserito",Toast.LENGTH_SHORT ).show();

        Log.i(DEBUG, "FUNZIONE UPPDATEEE");
        finish();
    }

    //torna indietro e annulla le modifiche
    public void cancelUpdate(View view){

        //infoModel.delete(info);

        finish();
    }

}
