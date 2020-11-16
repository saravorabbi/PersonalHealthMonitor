package com.example.personalhealthmonitor;

import androidx.room.Dao;
import androidx.room.Insert;

//Interfaccia che dichiara tutti i METODI che si possono usare per interagire col DB
//Implementazione dei metodi -> InfoViewModel.java

@Dao
public interface InfoDao {

    /** Wrapper in NoteViewModel.java
     */
    @Insert
    void insert(Info info);

}
