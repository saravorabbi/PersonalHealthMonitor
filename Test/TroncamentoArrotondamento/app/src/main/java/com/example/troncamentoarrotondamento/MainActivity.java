package com.example.troncamentoarrotondamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main activity";
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_1);


        double uno = 27.7896553;
        textView.setText(String.format("%.1f", uno)); //arrotonda per eccesso
        Log.i(TAG, "Prima Prova: " + uno );


        DecimalFormat precision = new DecimalFormat("0.00");

        String string = precision.format(uno);
        Log.i(TAG, "Seconfa Prova: " + uno );
        Log.i(TAG, "Terza Prova: " + string );

    }
}