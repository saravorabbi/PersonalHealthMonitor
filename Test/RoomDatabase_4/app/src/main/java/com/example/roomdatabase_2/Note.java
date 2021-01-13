package com.example.roomdatabase_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB

@Entity(tableName = "notes")
public class Note {
//tabella con due colonne (id | note)

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "note") //per cambiare il nome della colonna
    private  String mNote;

    public Note(String id, String note ){
        this.id = id;
        this.mNote = note;
    }

    //GETTER
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;  //uso this. perché gli ho cambiato nome (immagino)
    }
}
