package com.example.personalhealthmonitor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Interfaccia che dichiara tutti i METODI che si possono usare per interagire col DB
//Implementazione dei metodi -> InfoViewModel.java

@Dao
public interface InfoDao {

    /** Wrapper in NoteViewModel.java
     */
    @Insert
    void insert(Info info);


    /** Wrapper in NoteViewModel.java
     */
    // metodo per fare fetch di tutti i report -> la tabella si chiama 'report'
    @Query("SELECT * FROM report")
    LiveData<List<Info>> getAllInfo();



    @Query("SELECT * FROM report WHERE infoData=:data")
    LiveData<List<Info>> getAllReportsInDate(String data);

}
