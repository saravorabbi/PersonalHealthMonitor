package com.example.personalhealthmonitor;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Classe DataBase

@Database(entities = Info.class, version = 1)
public abstract class InfoRoomDatabase extends RoomDatabase {

    public abstract InfoDao infoDao();

    //creo una istanza del database - deve essere un singleton
    private static volatile InfoRoomDatabase infoRoomDatabase;

    //funzione che mi controlla che il DB sia un singoletto
    static InfoRoomDatabase getMyAppDatabase(final Context context){
        if(infoRoomDatabase == null){
            synchronized(InfoRoomDatabase.class){
                //Se l'istanza di myAppDatabase è nulla => creo il DB
                if(infoRoomDatabase == null){
                    //inizializzo il database (il context, nome della classe del database, nome del database-> me lo invento ora )
                    infoRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), InfoRoomDatabase.class, "info_databse").build();
                }
            }
        }

        return infoRoomDatabase;
    }

}
