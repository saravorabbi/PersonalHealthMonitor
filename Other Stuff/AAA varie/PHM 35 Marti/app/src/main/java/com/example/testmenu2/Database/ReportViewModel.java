package com.example.testmenu2.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private ReportDao reportDao;
    private DB reportDB;
    private LiveData<List<Report>> mAllReports;

    public ReportViewModel(Application application){
        super(application);

        reportDB = DB.getDatabase(application);
        reportDao = reportDB.reportDao();
        mAllReports = reportDao.getAllReports();
    }

    public void setReport(Report report){
        new InsertAsyncTask(reportDao).execute(report);
    }

    public void updateReport(Report report){
        new UpdateAsyncTask(reportDao).execute(report);
    }

    public void deleteReport(Report report){
        new DeleteAsyncTask(reportDao).execute(report);
    }

    public LiveData<List<Report>> getAllReports(){
        return mAllReports;
    }

    public LiveData<List<Report>> getAllReportsInDate(Date date){ return reportDao.getAllReportInDate(date); }

    public LiveData<List<Report>> getAllReportsInPeriod(Date date1, Date date2){
        return reportDao.getAllReportsInPeriod( date1, date2);
    }

    //public LiveData<List<Report>> getAllReportsOrder(){ return reportDao.getAllReportsOrder(); }

    public LiveData<Report> getReport(int reportId){
        return reportDao.getReport(reportId);
    }

    public LiveData<Double> getAVGInDate(String value, Date data){

        switch (value){
            case "battito": return reportDao.getAVGBattitoInDate(data);
            case "pressione": return reportDao.getAVGPressioneInDate(data);
            case "temperatura": return reportDao.getAVGTemperaturaInDate(data);
            case "glicemia": return reportDao.getAVGGlicemiaInDate(data);
        }
        return null;
    }

    public LiveData<List<AVG>> getAVGAll(String value){
        switch (value){
            case "Battito": return reportDao.getAVGBattitoAll();
            case "Pressione": return reportDao.getAVGPressioneAll();
            case "Temperatura": return reportDao.getAVGTemperaturaAll();
            case "Glicemia": return reportDao.getAVGGlicemiaAll();
        }
        return null;
    }

    public LiveData<List<AVG>> getAVGInPeriod(String value, Date date1, Date date2){
        switch (value){
            case "Battito": return reportDao.getAVGBattitoInPeriod(date1, date2);
            case "Pressione": return reportDao.getAVGPressioneInPeriod(date1, date2);
            case "Temperatura": return reportDao.getAVGTemperaturaInPeriod(date1, date2);
            case "Glicemia": return reportDao.getAVGGlicemiaInPeriod(date1, date2);
        }
        return null;
    }


    //OPERAZIONI ASYNC
    private class OperationAsyncTask extends AsyncTask<Report, Void, Void> {

        ReportDao AsyncTaskDao;

        public OperationAsyncTask(ReportDao asyncTaskDao) {
            AsyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Report... reports) {
            return null;
        }
    }

    //OPERAZIONE DI INSERIMENTO
    private class InsertAsyncTask extends OperationAsyncTask{

        public InsertAsyncTask(ReportDao reportDao) {
            super(reportDao);
        }

        @Override
        protected Void doInBackground(Report... reports) {
            AsyncTaskDao.addReport(reports[0]);
            return null;
        }
    }

    //OPERAZIONE DI MODIFICA
    private class UpdateAsyncTask extends AsyncTask<Report, Void, Void> {

        ReportDao mreportDao;

        public UpdateAsyncTask(ReportDao reportDao) {
            this.mreportDao = reportDao;
        }

        @Override
        protected Void doInBackground(Report... reports) {
            mreportDao.updateReport(reports[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Report, Void, Void> {
        ReportDao mreportDao;

        public DeleteAsyncTask(ReportDao reportDao) {
            this.mreportDao = reportDao;
        }


        @Override
        protected Void doInBackground(Report... reports) {
            mreportDao.deleteReport(reports[0]);
            return null;
        }
    }
}
