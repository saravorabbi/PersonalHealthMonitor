package com.example.personalhealthmonitor;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;

// Implemento i metodi "insert" "delete" dichiarati nell'interface che devo usare per
// interagire col DB

public class InfoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private String TAGG = "DEBUG - NoteViewModel";
    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;
    private LiveData<List<Info>> infoListLiveData; //live data for all info


    public InfoViewModel(@NonNull Application application, String dataSelezionata) {
        super(application);

        //fetching the instance of the database, ho bisogno di passare l'application context -> per questo uso "extends AndroidViewModel"
        //perchè ci lascia passare il context
        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
        infoListLiveData = infoDao.getAllInfo();//lista di tutte le note, fetch da InfoDao.java, la query

        /*da dove le prendo le date del calendario per recuperare le date che mi servono nel DB????
        * */
        //infoListLiveData = infoDao. getAllReportsInDate(dataSelezionata);

    }

    //dobbiamo eseguire questa operazione nel thread non UI -> uso async task
    // WRAPPER METHOD per l'inserimento della nota nel DB
    public void insert(Info info){
        new InsertAsyncTask(infoDao).execute(info);
    }

    //WRAPPER METHOD ritorna il liveData'mAllNotes' dal noteViewModel alla UI(nella main activity)
    //la uso nel main per prendere tutte le note in the UI e popolare la recycler lista con le note
    LiveData<List<Info>> getAllInfo(){
        return infoListLiveData;
    }


    LiveData<List<Info>> getAllInfoInDate(String data){

        LiveData<List<Info>> lista = infoDao.getAllInfoInDate(data);

        return lista;
    }


    //========================== CLASSI ASINCRONE

    //Superclasse che generalizza le classi async
    private class OperationAsyncTask extends AsyncTask<Info, Void, Void> {

        InfoDao asyncTaskDao;

        //costruttore
        OperationAsyncTask(InfoDao dao){
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Info... infos) {
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




    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Distrutto");
    }
}
