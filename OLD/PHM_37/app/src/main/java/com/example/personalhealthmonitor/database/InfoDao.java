package com.example.personalhealthmonitor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;


import java.util.List;

@Dao
public interface InfoDao {

    //==============//
    // ENTITA' INFO //
    //==============//

    // Funzione per l'inserimento del report
    @Insert
    void insert(Info info);

    // Funzione per l'aggiornamento del report
    @Update
    void update(Info info);

    // Funzione per l'eliminazione del report
    @Delete
    void delete(Info info);

    // Ritorna tutti i report in una determinata data
    @Query("SELECT * FROM report WHERE infoData=:data")
    LiveData<List<Info>> getAllInfoInDate(String data);

    // Ritorna il numero di report in una determinata data
    @Query("SELECT COUNT(*) FROM report WHERE infoData==:data")
    int getReportsCount(String data);

    // Ritorna la nota che ha come id il parametro 'noteId'
    @Query("SELECT * FROM report WHERE id=:infoId")
    LiveData<Info> getReport(String infoId);

    // Ritorna tutte le date del DB senza ripetizioni
    @Query("SELECT DISTINCT infoData FROM report ORDER BY infoData DESC")
    List<String> getAllDatesOnce();


    //========================//
    // NOTIFICHE MONITORAGGIO //
    //========================//

    // Query che ritonano la media di un parametro, controllando che le righe siano comprese fra le date in input

    //Temperatura
    @Query("SELECT avg(infoTemperatura) FROM report WHERE infoTemperatura!=0 AND infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAverageTemperatura(String dataBegin, String dataEnd);

    //Pressione Sistolica
    @Query("SELECT avg(infoPressioneSistolica) FROM report WHERE infoPressioneSistolica!=0 AND infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePressioneSistolica(String dataBegin, String dataEnd);

    //Pressione Diastolica
    @Query("SELECT avg(infoPressioneDiastolica) FROM report WHERE infoPressioneDiastolica!=0 AND infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePressioneDiastolica(String dataBegin, String dataEnd);

    //Glicemia
    @Query("SELECT avg(infoGlicemia) FROM report WHERE infoGlicemia!=0 AND infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAverageGlicemia(String dataBegin, String dataEnd);

    //Peso
    @Query("SELECT avg(infoPeso) FROM report WHERE infoPeso!=0 AND infoData>=:dataBegin AND infoData<=:dataEnd")
    double getAveragePeso(String dataBegin, String dataEnd);


    //=======//
    // GRAFI //
    //=======//

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


    //========//
    // FILTRI //
    //========//

    @RawQuery(observedEntities = Info.class)
    LiveData<List<Info>> getReportFiltered(SupportSQLiteQuery query);



    //~~~~~~~~~~~~~~~~~~//
    // ENTITA' SETTINGS //
    //~~~~~~~~~~~~~~~~~~//

    @Insert
    void insertSettings(Settings settings);

    @Update
    void updateSettings(Settings settings);

    // Input: parametro - output: la riga che ha come parametro quello passato in input
    @Query("SELECT * FROM settings WHERE parameter=:parametro")
    LiveData<Settings> getSettingsFromParametro(String parametro);

    // Input: parametro - output: priorità del parametro
    @Query("SELECT value FROM settings WHERE parameter=:parametro")
    int getValueFromParameter(String parametro);

    //~~~~~~~~~~~//
    // NOTIFICHE //
    //~~~~~~~~~~~//

    @Query("SELECT begin_date FROM settings WHERE parameter=:parametro")
    String getBeginDate(String parametro);

    @Query("SELECT end_date FROM settings WHERE parameter=:parametro")
    String getEndDate(String parametro);

    @Query("SELECT upper_bound FROM settings WHERE parameter=:parametro")
    double getUpperBound(String parametro);

    @Query("SELECT lower_bound FROM settings WHERE parameter=:parametro")
    double getLowerBound(String parametro);

    //~~~~~~~~//
    // FILTRI //
    //~~~~~~~~//

    // Ritorna la lista di parametri che ha una priorità >= a quella passata in input
    @Query("SELECT parameter FROM settings WHERE value>=:priority ")
    LiveData<List<String>> getParametersFromValue(int priority);

}
