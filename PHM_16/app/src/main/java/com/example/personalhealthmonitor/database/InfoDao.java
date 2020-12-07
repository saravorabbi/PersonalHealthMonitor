package com.example.personalhealthmonitor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personalhealthmonitor.database.Info;

import java.util.List;

//Interfaccia che dichiara tutti i METODI che si possono usare per interagire col DB
//Implementazione dei metodi -> InfoViewModel.java
//                           -> SettingsViewModel.java
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
    // ->c'era int come valore di ritorno nella versione PHM_12, controlla se è sempre stata int - ora l'ho messo void
    @Delete
    void delete(Info info);


    //======================//
    // Funzioni per i Grafi //
    //======================//

    @Query("SELECT * FROM report WHERE infoTemperatura != 0 ORDER BY infoData ASC")
    LiveData<List<Info>> getReportFromTemperature();

    @Query("SELECT * FROM report WHERE infoPressioneSistolica != 0 ORDER BY infoData ASC")
    LiveData<List<Info>> getReportFromPressioneSistolica();

    @Query("SELECT * FROM report WHERE infoPressioneDiastolica != 0 ORDER BY infoData ASC")
    LiveData<List<Info>> getReportFromPressioneDiastolica();

    @Query("SELECT * FROM report WHERE infoGlicemia != 0 ORDER BY infoData ASC")
    LiveData<List<Info>> getReportFromGlicemia();

    @Query("SELECT * FROM report WHERE infoPeso != 0 ORDER BY infoData ASC")
    LiveData<List<Info>> getReportFromPeso();



    //=======================//
    // Funzioni per Settings //
    //=======================//

    @Insert
    void insertSettings(Settings settings);


}
