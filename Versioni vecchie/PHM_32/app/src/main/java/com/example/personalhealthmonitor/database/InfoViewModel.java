package com.example.personalhealthmonitor.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

// Implemento i metodi "insert" "delete" dichiarati nell'interface che devo usare per interagire col DB
// Funzioni usate per interagire col DB (ci sono tutte le wrapper qui)

public class InfoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private String TAGG = "DEBUG - NoteViewModel";
    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;
    //private LiveData<List<Info>> infoListLiveData; //live data for all info


    // Costruttore
    public InfoViewModel(@NonNull Application application) {
        super(application);

        //fetching the instance of the database, ho bisogno di passare l'application context -> per questo uso "extends AndroidViewModel"
        //perchè ci lascia passare il context
        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
        //infoListLiveData = infoDao.getAllInfo();//lista di tutte le note, fetch da InfoDao.java, la query

        /*da dove le prendo le date del calendario per recuperare le date che mi servono nel DB????
        * */
        //infoListLiveData = infoDao. getAllReportsInDate(dataSelezionata);

    }

    // Funzione chiamata quando finisco di usare il view model, distruggo l'oggetto
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "Info ViewModel Distrutto");
    }

    // ================ //
    // WRAPPER FUNCTION //
    // ================ //

    // wrapper per l'inserimento della nota nel DB
    public void insert(Info info){
        //devo eseguire questa operazione nel thread non UI -> uso async task
        new InsertAsyncTask(infoDao).execute(info);
    }

    // wrapper per update del report
    public void update(Info info){
        new UpdateAsyncTask(infoDao).execute(info);
    }

    // wrapper per l'eliminazione del report
    public void delete(Info info){
        new DeleteAsyncTask(infoDao).execute(info);
    }


    // 2 WRAPPER METHOD ritorna il liveData'mAllNotes' dal noteViewModel alla UI(nella main activity)
    //la uso nel main per prendere tutte le note in the UI e popolare la recycler lista con le note
    public LiveData<List<Info>> getAllInfo(){
        //return infoListLiveData;
        return infoDao.getAllInfo();
    }

    // 3 WRAPPER METHOD ritorna le righe del DB che hanno la stessa data che passo in input
    public LiveData<List<Info>> getAllInfoInDate(String data){
        //LiveData<List<Info>> lista = infoDao.getAllInfoInDate(data);
        //return lista;
        return infoDao.getAllInfoInDate(data);
    }

    // 4 wrapper
    public LiveData<List<Info>> getAllInfoTemp(double temp){
        return infoDao.getAllInfoSameTemp(temp);
    }


    // 5 WRAPPER per avere il report -> usiamo questo metodo nella EditInfoActivity
    // per fare fetch della nota e poi modificarla passando l'id (che è un intero)
    public LiveData<Info> getInfoFromID(String noteId){
        return infoDao.getReport(noteId);
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

        //creo query
        String new_query = "SELECT * FROM report WHERE (";
        //inserico
        if(settingsList.size() > 0) {
            for (int i = 0; i < settingsList.size(); i++) {
                new_query = new_query + settingsList.get(i) + "!=0";

                //se
                if (i != (settingsList.size() - 1)) {
                    new_query = new_query + " OR ";
                }
            }
        } else return infoDao.getAllInfoInDate("0");
        //TODO RITORNA UNA LISTA DI REPORT MA VUOTA
        // dummy for now //CASO IN CUI NON HO PARAMETRI >= VALUE

        //new_query = new_query + ")";
        new_query = new_query + ") AND (infoData>='" + begin + "' AND infoData<='" + end + "')";

        Log.i(TAG, "QUERY = " + new_query);

        //uso la new_query
        return infoDao.getReportFiltered(new SimpleSQLiteQuery(new_query));
    }


    // ================ //
    // CLASSI ASINCRONE //
    // ================ //

    // Superclasse che generalizza le classi async
    private class OperationAsyncTask extends AsyncTask<Info, Void, Void> {

        InfoDao asyncTaskDao;

        //costruttore
        OperationAsyncTask(InfoDao dao){
            //Log.i(TAG, "DO IN BACKGROUNDDD 1111111");
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Info... infos) {
            //Log.i(TAG, "DO IN BACKGROUNDDD 22222");
            return null;
        }
    }


    // Creo async task, per eseguire il task in background
    // (deprecato, vedi se lo puoi sosttuire con qualcos'altro)!!!!!!!!!!!!!

    //Async task per l'operazione Insert
    private class InsertAsyncTask extends OperationAsyncTask {

        //costruttore
        public InsertAsyncTask(InfoDao infoDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Info... infos){
            asyncTaskDao.insert(infos[0]);
            return null;
        }
    }

    //Async task per l'operazione Update
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

    //Async task per l'operazione Delete
    private class DeleteAsyncTask extends OperationAsyncTask {

        //costruttore
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
