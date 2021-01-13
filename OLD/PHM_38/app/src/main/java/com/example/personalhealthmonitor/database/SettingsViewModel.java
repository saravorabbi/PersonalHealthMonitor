package com.example.personalhealthmonitor.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();

    private InfoDao infoDao;
    private InfoRoomDatabase infoDB;

    public SettingsViewModel(@NonNull Application application) {
        super(application);

        infoDB = InfoRoomDatabase.getMyAppDatabase(application);
        infoDao = infoDB.infoDao();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "Settings ViewModel Distrutto");
    }


    //inserimento
    public void insertSettings(Settings setting){
        new SettingsViewModel.InsertAsyncTask(infoDao).execute(setting);
    }

    //update
    public void updateSettings(Settings settings){
        new SettingsViewModel.UpdateAsyncTask(infoDao).execute(settings);
    }


    //ritorna la riga che ha il parametro dato in input
    public LiveData<Settings> getSettingsFromParametro(String parametro){
        return infoDao.getSettingsFromParametro(parametro);
    }


    //ritorna il value (importanza monitoraggio) che corrisponde al parametro dato in input
    public int getValueFromParameter(String parameter){
        return infoDao.getValueFromParameter(parameter);
    }


    //NOTIFICHE

    public String getBeginDate(String parametro){
        return infoDao.getBeginDate(parametro);
    }

    public String getEndDate(String parametro){
        return infoDao.getEndDate(parametro);
    }

    public double getUpperBound(String parametro){
        return infoDao.getUpperBound(parametro);
    }

    public double getLowerBound(String parametro){
        return infoDao.getLowerBound(parametro);
    }


    //FILTRI

    //ritorna la lista di parametri che hanno un value >= della priorità passata in input
    public LiveData<List<String>> getParametersFromValue(int priority){
        return infoDao.getParametersFromValue(priority);
    }



    // ================ //
    // CLASSI ASINCRONE //
    // ================ //

    // Superclasse che generalizza le classi async
    private class OperationAsyncTask extends AsyncTask<Settings, Void, Void> {

        InfoDao asyncTaskDao;

        //costruttore
        OperationAsyncTask(InfoDao dao){
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Settings... settings) {
            return null;
        }
    }

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
