package com.example.testmenu2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    public void addReport(Report report);

    @Update
    void updateReport (Report report);

    @Delete
    void deleteReport (Report report);

    @Query("DELETE FROM reports")
    void deleteAll();

    @Query("SELECT * FROM reports")
    LiveData<List<Report>> getAllReports();

    @Query("SELECT * FROM reports WHERE id=:reportId")
    LiveData<Report> getReport(int reportId);

    @Query("SELECT * FROM reports WHERE report_giorno=:date")
    LiveData<List<Report>> getAllReportInDate(Date date);

    @Query("SELECT * FROM reports WHERE report_giorno>= :date1 AND report_giorno<=:date2 ORDER BY report_giorno DESC, report_ora DESC")
    LiveData<List<Report>> getAllReportsInPeriod(Date date1, Date date2);

    @Query("SELECT * FROM reports ORDER BY report_giorno DESC, report_ora DESC")
    LiveData<List<Report>> getAllReportsOrder();

    @Query("SELECT AVG(report_battito) FROM reports WHERE report_giorno=:date AND report_battito != 0")
    LiveData<Double> getAVGBattitoInDate(Date date);

    @Query("SELECT AVG(report_pressione) FROM reports WHERE report_giorno=:date AND report_pressione != 0")
    LiveData<Double> getAVGPressioneInDate(Date date);

    @Query("SELECT AVG(report_temperatura) FROM reports WHERE report_giorno=:date AND report_temperatura != 0")
    LiveData<Double> getAVGTemperaturaInDate(Date date);

    @Query("SELECT AVG(report_glicemia) FROM reports WHERE report_giorno=:date AND report_glicemia != 0")
    LiveData<Double> getAVGGlicemiaInDate(Date date);

    @Query("SELECT AVG(report_battito) AS media, report_giorno AS giorno FROM reports WHERE report_battito != 0 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGBattitoAll();

    @Query("SELECT AVG(report_pressione) AS media, report_giorno AS giorno FROM reports WHERE report_pressione != 0 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGPressioneAll();

    @Query("SELECT AVG(report_temperatura) AS media, report_giorno AS giorno FROM reports WHERE report_temperatura != 0 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGTemperaturaAll();

    @Query("SELECT AVG(report_glicemia) AS media, report_giorno AS giorno FROM reports WHERE report_glicemia != 0 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGGlicemiaAll();

    @Query("SELECT AVG(report_battito) AS media, report_giorno AS giorno FROM reports WHERE report_battito != 0 AND report_giorno>= :date1 AND report_giorno<=:date2 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGBattitoInPeriod(Date date1, Date date2);

    @Query("SELECT AVG(report_pressione) AS media, report_giorno AS giorno FROM reports WHERE report_pressione != 0 AND report_giorno>= :date1 AND report_giorno<=:date2 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGPressioneInPeriod(Date date1, Date date2);

    @Query("SELECT AVG(report_temperatura) AS media, report_giorno AS giorno FROM reports WHERE report_temperatura != 0 AND report_giorno>= :date1 AND report_giorno<=:date2 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGTemperaturaInPeriod(Date date1, Date date2);

    @Query("SELECT AVG(report_glicemia) AS media, report_giorno AS giorno FROM reports WHERE report_glicemia != 0 AND report_giorno>= :date1 AND report_giorno<=:date2 GROUP BY report_giorno ORDER BY report_giorno")
    LiveData<List<AVG>> getAVGGlicemiaInPeriod(Date date1, Date date2);

}
