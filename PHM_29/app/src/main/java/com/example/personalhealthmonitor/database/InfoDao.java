package com.example.personalhealthmonitor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


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


    //NOTIFICHE Monitoraggio
    //Query che ritona la media di un parametro controllando che le righe siano comprese fra le date in input

    //Temperatura
    @Query("SELECT avg(infoTemperatura) FROM report WHERE infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAverageTemperatura(String dataBegin, String dataEnd);

    //Pressione Sistolica
    @Query("SELECT avg(infoPressioneSistolica) FROM report WHERE infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePressioneSistolica(String dataBegin, String dataEnd);

    //Pressione Diastolica
    @Query("SELECT avg(infoPressioneDiastolica) FROM report WHERE infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePressioneDiastolica(String dataBegin, String dataEnd);

    //Glicemia
    @Query("SELECT avg(infoGlicemia) FROM report WHERE infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAverageGlicemia(String dataBegin, String dataEnd);

    //Peso
    @Query("SELECT avg(infoPeso) FROM report WHERE infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePeso(String dataBegin, String dataEnd);

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

    @Update
    void updateSettings(Settings settings);


    //get riga from informazione
    @Query("SELECT * FROM settings WHERE parameter=:parametro")
    LiveData<Settings> getSettingsFromParametro(String parametro);


    @Query("SELECT value FROM settings WHERE parameter=:parametro")
    int getValueFromParameter(String parametro);


    //NOTIFICHE
    @Query("SELECT begin_date FROM settings WHERE parameter=:parametro")
    String getBeginDate(String parametro);

    @Query("SELECT end_date FROM settings WHERE parameter=:parametro")
    String getEndDate(String parametro);

    @Query("SELECT upper_bound FROM settings WHERE parameter=:parametro")
    double getUpperBound(String parametro);

    @Query("SELECT lower_bound FROM settings WHERE parameter=:parametro")
    double getLowerBound(String parametro);
}
