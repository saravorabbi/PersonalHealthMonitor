package com.example.roomdatabase_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAGG = "DEBUG";

    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //Recycle view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteListAdapter = new NoteListAdapter(this);
        Log.i(TAGG, "Uno");
        recyclerView.setAdapter(noteListAdapter);
        Log.i(TAGG, "Due");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //floating action button (???)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        // popolo il recycler con le note nel DB
        //osserviamo il liveData che il noteViewModel ci ritorna e con sta roba che ci ritorna
        // (che è una lista di dati -> note) mettiamo la lista nella recyclerView(che è il noteListAdapter)
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Vedo se il requestCode che sta arrivando dalla NewNoteActivity è ok opp no
        if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            //Code to insert the note - OPERAZIONE INSERT
            // prendo questa stringa da usare come ID nel DB (non so che sia l'UUID)
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);
            //end code to insert note

            //display il messaggio toast che inserimento è andato bene
            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved, //viene dal file string.xml
                    Toast.LENGTH_LONG).show();
        } else{
            //display il messaggio toast che ci dice che la nota non è stata salvata
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved, //viene dal file string.xml
                    Toast.LENGTH_LONG).show();
        }

    }
}