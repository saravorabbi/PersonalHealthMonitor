package com.example.personalhealthmonitor.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

// View Model che implementa i metodi dichiarati nell'interface InfoDao
// Queste funzioni sono usate per interagire col DB

public class InfoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private String TAGG = "DEBUG - NoteViewModel";
    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;


    // Costruttore
    public InfoViewModel(@NonNull Application application) {
        super(application);

        //fetching the instance of the database, ho bisogno di passare l'application context -> per questo uso "extends AndroidViewModel"
        //perchè ci lascia passare il context
        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "Info ViewModel Distrutto");
    }

    // ================ //
    // WRAPPER FUNCTION //
    // ================ //

    // Inserimento del report nel DB
    public void insert(Info info){
        new InsertAsyncTask(infoDao).execute(info);
    }

    // Update del report nel DB
    public void update(Info info){
        new UpdateAsyncTask(infoDao).execute(info);
    }

    // Eliminazione del report dal DB
    public void delete(Info info){
        new DeleteAsyncTask(infoDao).execute(info);
    }


    // Ritorna i report aggiunti nella data passata come input
    public LiveData<List<Info>> getAllInfoInDate(String data){
        return infoDao.getAllInfoInDate(data);
    }

    // Ritorna un report che ha l'id corrispondente alla stringa passata in input (EditInfoActivity)
    public LiveData<Info> getInfoFromID(String noteId){
        return infoDao.getReport(noteId);
    }


    // Ritorna il numero di report inseriti in una data specifica
    public int getReportsCount(String data){
        return infoDao.getReportsCount(data);
    }


    public List<String> getAllDatesOnce(){
        return infoDao.getAllDatesOnce();
    }

    //GRAPH ACTIVITY -> sarebbe il caso di averne una con input il parametro "DB_TEMPERATURA" etc.

    public LiveData<List<Info>> getReportFromTemperature(){
        return infoDao.getReportFromTemperature();
    }

    public LiveData<List<Info>> getReportFromPressioneSistolica(){
        return infoDao.getReportFromPressioneSistolica();
    }

    public LiveData<List<Info>> getReportFromPressioneDiastolica(){
        return infoDao.getReportFromPressioneDiastolica();
    }

    public LiveData<List<Info>> getReportFromGlicemia(){
        return infoDao.getReportFromGlicemia();
    }

    public LiveData<List<Info>> getReportFromPeso(){
        return infoDao.getReportFromPeso();
    }


    //NOTIFICHE MONITORAGGIO

    //Temperatura
    public double getAverageTemperatura(String dataBegin, String dataEnd){
        return infoDao.getAverageTemperatura(dataBegin, dataEnd);
    }

    //Pressione Sistolica
    public double getAveragePressioneSistolica(String dataBegin, String dataEnd){
        return infoDao.getAveragePressioneSistolica(dataBegin, dataEnd);
    }

    //Pressione Diastolica
    public double getAveragePressioneDiastolica(String dataBegin, String dataEnd){
        return infoDao.getAveragePressioneDiastolica(dataBegin, dataEnd);
    }

    //Glicemia
    public double getAverageGlicemia(String dataBegin, String dataEnd){
        return infoDao.getAverageGlicemia(dataBegin, dataEnd);
    }

    //Peso
    public double getAveragePeso(String dataBegin, String dataEnd){
        return infoDao.getAveragePeso(dataBegin, dataEnd);
    }


    //FILTRI

    public LiveData<List<Info>> getReportFromFilter(List<String> settingsList, String begin, String end){

        // Creo query
        String new_query = "SELECT * FROM report WHERE (";

        // Inserico i parametri
        if(settingsList.size() > 0) {
            for (int i = 0; i < settingsList.size(); i++) {
                new_query = new_query + settingsList.get(i) + "!=0";

                //se
                if (i != (settingsList.size() - 1)) {
                    new_query = new_query + " OR ";
                }
            }
        } else return infoDao.getAllInfoInDate("0");

        new_query = new_query + ") AND (infoData>='" + begin + "' AND infoData<='" + end + "')";

        Log.i(TAG, "QUERY = " + new_query);

        // Passo la new_query che ho appena creato
        return infoDao.getReportFiltered(new SimpleSQLiteQuery(new_query));
    }


    // ================ //
    // CLASSI ASINCRONE //
    // ================ //

    // Superclasse che generalizza le classi async
    private class OperationAsyncTask extends AsyncTask<Info, Void, Void> {

        InfoDao asyncTaskDao;

        // Costruttore
        OperationAsyncTask(InfoDao dao){
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Info... infos) {
            return null;
        }
    }


    // Async task per l'operazione Insert
    private class InsertAsyncTask extends OperationAsyncTask {

        // Costruttore
        public InsertAsyncTask(InfoDao infoDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Info... infos){
            asyncTaskDao.insert(infos[0]);
            return null;
        }
    }

    // Async task per l'operazione Update
    private class UpdateAsyncTask extends OperationAsyncTask {

        //costruttore
        public UpdateAsyncTask(InfoDao infoDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Info... infos) {
            asyncTaskDao.update(infos[0]);
            return null;
        }
    }

    // Async task per l'operazione Delete
    private class DeleteAsyncTask extends OperationAsyncTask {

        // Costruttore
        public DeleteAsyncTask(InfoDao noteDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Info... infos) {
            asyncTaskDao.delete(infos[0]);
            return null;
        }
    }

}
