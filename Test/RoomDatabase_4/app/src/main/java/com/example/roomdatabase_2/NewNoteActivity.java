package com.example.roomdatabase_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//Activity per creare una nuova nota

public class NewNoteActivity extends AppCompatActivity {

    public static final String NOTE_ADDED = "new_note";
    private EditText etNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        etNewNote = findViewById(R.id.etNewNote);

        Button button = findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();

                if( TextUtils.isEmpty(etNewNote.getText()) ){
                    setResult(RESULT_CANCELED, resultIntent);   // 0
                } else{
                    String note = etNewNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    setResult(RESULT_OK, resultIntent); // -1
                }

                //finisco l'activity
                finish();
            }
        });
    }
}