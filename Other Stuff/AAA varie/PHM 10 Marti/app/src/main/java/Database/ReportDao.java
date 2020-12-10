package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    public void addReport(Report report);

    @Query("SELECT * FROM reports")
    LiveData<List<Report>> getAllReports();

    @Query("SELECT * FROM reports WHERE id=:reportId")
    LiveData<Report> getReport(int reportId);

    @Update
    void updateReport (Report report);

    @Delete
    void deleteReport (Report report);
}
