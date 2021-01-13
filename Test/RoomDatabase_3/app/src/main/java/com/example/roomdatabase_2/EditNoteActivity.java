package com.example.roomdatabase_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditNoteActivity extends AppCompatActivity {

    private static final String TAGG = "DEBUG - EditNoteActivity";

    private EditText etNote;
    EditNoteViewModel noteModel;

    private Bundle bundle;  //bundle attraverso il quale facciamo fetch dell'ID della nota (nell'intent)
    private String noteId;  //ID della nota
    private LiveData<Note> note;

    public static final String NOTE_ID = "note_id";
    static final String UPDATED_NOTE = "note_text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNote = findViewById(R.id.etNote);

        //fetch dei dati che abbiamo passato con l'Intent tramite il Bundle
        bundle = getIntent().getExtras();

        if(bundle != null){
            noteId = bundle.getString("note_id");
        }

        noteModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);

        //funz wrapper che mi fetcha la nota
        note = noteModel.getNote(noteId);

        //ora abbiamo la nota, la possiamo modificare
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                //se ci sono dei cambiamenti nella nota
                etNote.setText(note.getNote());
            }
        });
    }

    //FUNZIONI onClick DELLA NOTA ->

    public void updateNote(View view){
        String updatedNote = etNote.getText().toString();
        //new Intent per mandare il risultato nel database
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //se clicco sul pulsante cancel cancella gli aggiornamenti fatti non ancora salvati
    public void cancelUpdate(View view){
        finish();
    }

}
