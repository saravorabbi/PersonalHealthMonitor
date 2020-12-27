package com.example.personalhealthmonitor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Classe DataBase

@Database(
        entities = {
                Info.class,
                Settings.class
        },
        version = 1
)
public abstract class InfoRoomDatabase extends RoomDatabase {

    public abstract InfoDao infoDao();

    // Istanza del database - deve essere un singleton
    private static volatile InfoRoomDatabase infoRoomDatabase;

    // Costruisco il DB e controllo che sia un singoletto
    public static InfoRoomDatabase getMyAppDatabase(final Context context){
        if(infoRoomDatabase == null){

            synchronized(InfoRoomDatabase.class){

                // Se l'istanza di myAppDatabase è nulla, creo il DB
                if(infoRoomDatabase == null){

                    // Inizializzo il database (context, nome della classe del database, nome del database)
                    infoRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), InfoRoomDatabase.class, "info_database").build();

                }
            }
        }

        return infoRoomDatabase;
    }

}
