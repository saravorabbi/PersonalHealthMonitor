package com.example.settingsproveee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity: ";
    private SeekBar seekBar;
    private TextView textView;
    private Button button;
    private LinearLayout linearLayout;

    private EditText lower, upper;

    private TextView begin, end, risposta;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DATE PICKER CONTROLLI
        begin = findViewById(R.id.begin);
        end = findViewById(R.id.end);
        okButton = findViewById(R.id.conferma);
        risposta = findViewById(R.id.risposta);

        //datepicker
        int YEAR = Calendar.getInstance().get(Calendar.YEAR);
        int MONTH = Calendar.getInstance().get(Calendar.MONTH);
        int DATE = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        //

        //begin date
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String day = String.valueOf(giorno);

                        if( giorno >=1 && giorno <=9 ){
                            day = "0" + day;    //se ho una cifra, aggiungo lo zero
                        }

                        String data = (mese+1) + "/" + day + "/" + anno;
                        Log.i(TAG, "data : " + data);

                        begin.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });


        //end date
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String day = String.valueOf(giorno);

                        if( giorno >=1 && giorno <=9 ){
                            day = "0" + day;    //se ho una cifra, aggiungo lo zero
                        }

                        String data = (mese+1) + "/" + day + "/" + anno;
                        Log.i(TAG, "data : " + data);

                        end.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        //controllo se la prima data viene prima della seconda
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beg = begin.getText().toString();
                String endd = end.getText().toString();

                Date dataInizio = new Date(12/02/2020);
                Date dataFine = new Date(12/02/2020);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    dataInizio = simpleDateFormat.parse(beg);
                    Log.i(TAG, "data= " + dataInizio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try{
                    dataFine = simpleDateFormat.parse(endd);
                } catch (ParseException e){
                    e.printStackTrace();
                }

//                boolean flag = dataFine.after(dataInizio);
//                Log.i(TAG, String.valueOf(flag));
//                if(flag){
//                    risposta.setText("Si");
//                }else{
//                    risposta.setText("NO");
//                }

                int val = dataFine.compareTo(dataInizio);
                Log.i(TAG, "Valore = " + val);
                if (dataFine.compareTo(dataInizio) >= 0)
                {   //data inizio > data fine => return 1
                    Log.d(TAG,"data inizio >= data fine => return 1 (o 0) - valore =" + val);
                }
                else
                {   //data fine > data inizio => return -1 (CASO ERRORE)
                    Log.d(TAG,"data fine > data inizio (CASO ERRORE) => return 0 - valore =" + val);
                }



            }
        });



        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.linearLazout1);

        lower = findViewById(R.id.temp_lower_bound);
        upper = findViewById(R.id.temp_upper_bound);

        // seekBar.setProgress(4);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i(TAG, "STO CMABAINDO I DTI" );
                textView.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "ho finito di toccare lo schermoooo " );

                int num = Integer.parseInt((String) textView.getText());

                if (num > 2){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else{
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });



    }

    public void check(View view){

        boolean flag = true;

        int tempVAL = Integer.parseInt( (String)textView.getText() );

        if( tempVAL > 2) {

            double low = Double.parseDouble( String.valueOf(lower.getText()) );
            double up = Double.parseDouble( String.valueOf(upper.getText()) );

            if (low < up) {  //inserisco nel DB
                //CHECK ANCHE DELLE DATE
                Log.i(TAG, "BOUND Tutto OK!!");
                Snackbar.make(view, " Bound Tutto OK!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //INSERT NEL DB

            } else {
                Log.i(TAG, "bound NON VA BENE");
                Snackbar.make(view, "bound NON VA BENE", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                // SETTA UN FLAG A FALSE
                flag = false;

            }
        }



        //se arrivo fino a qui, ho passato tutti i controlli e setto la notifica
        if (flag) {
            //SETTO LE NOTIFICHEEE
        }



    }
}