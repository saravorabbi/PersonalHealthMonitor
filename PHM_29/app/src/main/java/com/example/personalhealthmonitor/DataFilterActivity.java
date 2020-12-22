package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataFilterActivity extends AppCompatActivity {

    private static final String TAG = "DataFilterActivity";

    private TextView priorityTV;
    private TextView personalTV, settimanaTV, meseTV, annoTV;
    private LinearLayout linearLayoutPersonal;

    private boolean personalON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filter);

        priorityTV = findViewById(R.id.filter_importanza);
        personalTV = findViewById(R.id.filter_personal);
        settimanaTV = findViewById(R.id.filter_settimana);
        meseTV = findViewById(R.id.filter_mese);
        annoTV = findViewById(R.id.filter_anno);
        linearLayoutPersonal = findViewById(R.id.filter_personal_linear_layout);

        personalON = false;
        linearLayoutPersonal.setVisibility(View.GONE);

    }


    public void personalFilters(View view){
        if(personalON){
            linearLayoutPersonal.setVisibility(View.GONE);
            personalON = false;
        }else {
            linearLayoutPersonal.setVisibility(View.VISIBLE);
            personalON = true;
        }
    }
}