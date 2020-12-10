package com.example.personalhealthmonitor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Interfaccia che dichiara tutti i METODI che si possono usare per interagire col DB
//Implementazione dei metodi -> InfoViewModel.java
/** Wrapper in InfoViewModel.java */

@Dao
public interface InfoDao {

    @Insert
    void insert(Info info);


    // metodo per fare fetch di tutti i report -> la tabella si chiama 'report'
    @Query("SELECT * FROM report")
    LiveData<List<Info>> getAllInfo();


    @Query("SELECT * FROM report WHERE infoData=:data")
    LiveData<List<Info>> getAllInfoInDate(String data);


    @Query("SELECT * FROM report WHERE infoTemperatura=:temp")
    LiveData<List<Info>> getAllInfoSameTemp(double temp);


    //Funzione che ritorna la nota che ha come id il parametro 'noteId'
    @Query("SELECT * FROM report WHERE id=:infoId")
    LiveData<Info> getReport(String infoId);


    // Funzione per l'aggiornamento del report
    @Update
    void update(Info info);

    // Funzione per l'eliminazione del report
    @Delete
    int delete(Info info);

}
