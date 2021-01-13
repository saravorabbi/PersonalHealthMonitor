package com.example.databaseroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Questo è il mio DATABASE, devo specificare tutte le entità del database -> in questo caso solo "User"

@Database(entities = {User.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    // this class must contain an abstract method that return an object of the DataAccessObject
    // this class represent the database

    public abstract MyDao myDao();

    /*
    //create instance of database - deve essere un singleton
    private static volatile MyAppDatabase myAppDatabase;


    static MyAppDatabase getMyAppDatabase(final Context context){
        if(myAppDatabase == null){
            synchronized(MyAppDatabase.class){
                //Se l'istanza di myAppDatabase è nulla => la creo
                if(myAppDatabase == null){
                    //inizializzo il database (il context, nome della classe del database, nome del database-> me lo invento ora )
                    myAppDatabase= Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, "my_database").build();
                }
            }
        }

        return myAppDatabase;
    }
*/


}
