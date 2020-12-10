package com.example.personalhealthmonitor.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SettingsViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();

    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;

    public SettingsViewModel(@NonNull Application application) {
        super(application);

        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
    }

    // Funzione chiamata quando finisco di usare il view model, distruggo l'oggetto
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "Settings ViewModel Distrutto");
    }







    // wrapper per l'inserimento della nota nel DB
    public void insertSettings(Settings setting){
        Log.i(TAG, "sto inserendo il setting");
        //devo eseguire questa operazione nel thread non UI -> uso async task
        new SettingsViewModel.InsertAsyncTask(infoDao).execute(setting);
    }

    public void updateSettings(Settings settings){
        new SettingsViewModel.UpdateAsyncTask(infoDao).execute(settings);
    }


    //ritorna la riga che ha il parametro passato (temperatura, peso etc.)
    public LiveData<Settings> getSettingsFromParametro(String parametro){
        return infoDao.getSettingsFromParametro(parametro);
    }

    // ================ //
    // CLASSI ASINCRONE //
    // ================ //

    // Superclasse che generalizza le classi async
    private class OperationAsyncTask extends AsyncTask<Settings, Void, Void> {

        InfoDao asyncTaskDao;

        //costruttore
        OperationAsyncTask(InfoDao dao){
            Log.i(TAG, "DO IN BACKGROUNDDD 1111111");
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Settings... settings) {
            Log.i(TAG, "DO IN BACKGROUNDDD 22222");
            return null;
        }
    }


    // Creo async task, per eseguire il task in background
    // (deprecato, vedi se lo puoi sosttuire con qualcos'altro)!!!!!!!!!!!!!

    //Async task per l'operazione Insert
    private class InsertAsyncTask extends SettingsViewModel.OperationAsyncTask {

        //costruttore
        public InsertAsyncTask(InfoDao infoDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Settings... settings){
            asyncTaskDao.insertSettings(settings[0]);
            return null;
        }
    }


    //Async task per l'operazione Update
    private class UpdateAsyncTask extends SettingsViewModel.OperationAsyncTask {

        //costruttore
        public UpdateAsyncTask(InfoDao infoDao) {
            super(infoDao);
        }

        @Override
        protected Void doInBackground(Settings... settings) {
            asyncTaskDao.updateSettings(settings[0]);
            return null;
        }
    }


}
