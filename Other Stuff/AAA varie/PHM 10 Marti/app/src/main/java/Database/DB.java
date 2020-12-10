package Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Report.class}, version = 1, exportSchema = false)
public abstract class DB extends RoomDatabase {
    public abstract ReportDao reportDao();

    public static volatile DB dbInstance;

    static DB getDatabase(final Context context){
        if(dbInstance == null){
            synchronized (DB.class){
                if(dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            DB.class, "report_database").build();
                }
            }
        }
        return dbInstance;
    }
}
