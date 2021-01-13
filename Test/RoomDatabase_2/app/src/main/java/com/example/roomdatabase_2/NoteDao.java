package com.example.roomdatabase_2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Interfaccia che dichiara tutti i metodi che si possono usare per interagire col DB

@Dao
public interface NoteDao {

    @Insert
    void insert(Note Note);

    //define method to fetch all notes -> la tabella l'abbiamo rinominata 'notes'
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

}
