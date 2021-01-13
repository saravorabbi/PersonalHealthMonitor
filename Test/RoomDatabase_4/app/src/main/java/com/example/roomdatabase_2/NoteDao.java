package com.example.roomdatabase_2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Interfaccia che dichiara tutti i metodi che si possono usare per interagire col DB

@Dao
public interface NoteDao {
    /** Wrapper in NoteViewModel.java
    */
    @Insert
    void insert(Note Note);

    /** Wrapper in NoteViewModel.java
     */
    //define method to fetch all notes -> la tabella l'abbiamo rinominata 'notes'
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    /** Wrapper in EditNoteViewModel.java
     */
    //Funzione che ritorna la nota che ha come id il parametro 'noteId'
    @Query("SELECT * FROM notes WHERE id=:noteId")
    LiveData<Note> getNote(String noteId);

    /** Wrapper in NoteViewModel.java
     * */
    // Funzione per l'aggiornamento della nota
    @Update
    void update(Note note);

    /** Wrapper in NoteViewModel.java
     * */
    // Funzione per l'eliminazione della nota
    @Delete
    int delete(Note note);
}
