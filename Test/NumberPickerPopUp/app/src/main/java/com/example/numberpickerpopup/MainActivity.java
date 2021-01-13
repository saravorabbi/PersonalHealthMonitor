package com.example.numberpickerpopup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    private static TextView tv, differentTesto;
    private Dialog d ;
    private Dialog finestraDialog;

    private EditText edit_text;
    private static final String TAG = "DEBUG";
    Button editButton;
    private EditText testo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_text = findViewById(R.id.edit_text1);

        testo = findViewById(R.id.edit_textTESTO);
        tv = findViewById(R.id.testo_main);

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv.setText(testo.getText().toString());


                double valore = Double.parseDouble(edit_text.getText().toString());

                if(valore > 3){
                    Log.i(TAG, "primo if");
                }else {
                    Log.i(TAG, "else");
                }
            }
        });

/*
        edit_text.addTextChangedListener(new TextChangedListener<EditText>(edit_text) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                Log.i(TAG, "edit text change");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(){

                }
                Log.i(TAG, "finito di modificare");
                //super.afterTextChanged(s);
            }
        });
*/









        tv = (TextView) findViewById(R.id.testo_main);
        Button b = (Button) findViewById(R.id.open);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                show();
            }
        });

        differentTesto = (TextView) findViewById(R.id.differentTesto);
        Button differentOpen = findViewById(R.id.differentOpen);

        differentOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDifferentOpen();
            }
        });
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }

    public void show()
    {

        final Dialog dialogg = new Dialog(MainActivity.this);
        dialogg.setTitle("NumberPicker");
        dialogg.setContentView(R.layout.dialog);
        Button set = (Button) dialogg.findViewById(R.id.set);      //set = b1
        Button cancel = (Button) dialogg.findViewById(R.id.cancel);      //cancel = b2

        final NumberPicker np = (NumberPicker) dialogg.findViewById(R.id.numberPicker1);

        np.setMaxValue(42);
        np.setMinValue(30);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue()));
                dialogg.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogg.dismiss();
            }
        });

        dialogg.show();


    }

    public void showDifferentOpen(){

        finestraDialog = new Dialog(MainActivity.this);
        finestraDialog.setTitle("NumberPicker");
        finestraDialog.setContentView(R.layout.dialog);
        Button set = (Button) finestraDialog.findViewById(R.id.set);      //set = b1
        Button cancel = (Button) finestraDialog.findViewById(R.id.cancel);      //cancel = b2

        final NumberPicker np = (NumberPicker) finestraDialog.findViewById(R.id.numberPicker1);

        np.setMaxValue(300);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                differentTesto.setText(String.valueOf(np.getValue()));
                finestraDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finestraDialog.dismiss();
            }
        });

        finestraDialog.show();
    }


//===================================================================================
    private abstract class TextChangedListener<T> implements TextWatcher {

        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            //this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }






}







