package com.example.personalhealthmonitor;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Implemento i metodi "insert" "delete" dichiarati nell'interface che devo usare per
// interagire col DB

public class InfoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private String TAGG = "DEBUG - NoteViewModel";
    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;
   // private LiveData<List<Info>> AllNotes; //live data for all notes


    public InfoViewModel(@NonNull Application application) {
        super(application);

        //fetching the instance of the database, ho bisogno di passare l'application context -> per questo uso "extends AndroidViewModel"
        //perchè ci lascia passare il context
        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
    }

    //dobbiamo eseguire questa operazione nel thread non UI -> uso async task
    // WRAPPER METHOD per l'inserimento della nota nel DB
    public void insert(Info info){
        new InsertAsyncTask(infoDao).execute(info);
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
