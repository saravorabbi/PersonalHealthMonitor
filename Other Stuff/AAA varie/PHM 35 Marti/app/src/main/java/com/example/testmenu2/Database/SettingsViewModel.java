package com.example.testmenu2.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private SettingsDao settingsDao;
    private DB settingsDB;
    private LiveData<List<Settings>> mAllSettings;

    public SettingsViewModel(Application application){
        super(application);

        settingsDB = DB.getDatabase(application);
        settingsDao = settingsDB.settingsDao();
        mAllSettings = settingsDao.getAllSettings();
    }

    public void setSettings(Settings Settings){
        new InsertAsyncTask(settingsDao).execute(Settings);
    }

    public void updateSettings(Settings Settings){
        new UpdateAsyncTask(settingsDao).execute(Settings);
    }

    public void deleteSettings(Settings Settings){
        new DeleteAsyncTask(settingsDao).execute(Settings);
    }

    public Settings getSetting(String valore){ return settingsDao.getSetting(valore);}

    public LiveData<List<Settings>> getAllSettings(){
        return mAllSettings;
    }

    //OPERAZIONI ASYNC
    private class OperationAsyncTask extends AsyncTask<Settings, Void, Void> {

        SettingsDao AsyncTaskDao;

        public OperationAsyncTask(SettingsDao asyncTaskDao) {
            AsyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Settings... Settingss) {
            return null;
        }
    }

    //OPERAZIONE DI INSERIMENTO
    private class InsertAsyncTask extends OperationAsyncTask{

        public InsertAsyncTask(SettingsDao SettingsDao) {
            super(SettingsDao);
        }

        @Override
        protected Void doInBackground(Settings... Settingss) {
            AsyncTaskDao.addSettings(Settingss[0]);
            return null;
        }
    }

    //OPERAZIONE DI MODIFICA
    private class UpdateAsyncTask extends AsyncTask<Settings, Void, Void> {

        SettingsDao mSettingsDao;

        public UpdateAsyncTask(SettingsDao SettingsDao) {
            this.mSettingsDao = SettingsDao;
        }

        @Override
        protected Void doInBackground(Settings... Settingss) {
            settingsDao.updateSettings(Settingss[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Settings, Void, Void> {
        SettingsDao mSettingsDao;

        public DeleteAsyncTask(SettingsDao SettingsDao) {
            this.mSettingsDao = SettingsDao;
        }


        @Override
        protected Void doInBackground(Settings... Settingss) {
            settingsDao.deleteSettings(Settingss[0]);
            return null;
        }
    }
}
