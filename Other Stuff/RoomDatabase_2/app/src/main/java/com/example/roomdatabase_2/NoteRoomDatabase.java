package com.example.roomdatabase_2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//DB

@Database(entities = Note.class, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract  NoteDao noteDao();

    //create instance of database - deve essere un singleton
    private static volatile NoteRoomDatabase noteRoomDatabase;

    //funzione che mi controlla che il DB sia un singoletto
    static NoteRoomDatabase getMyAppDatabase(final Context context){
        if(noteRoomDatabase == null){
            synchronized(NoteRoomDatabase.class){
                //Se l'istanza di myAppDatabase è nulla => la creo
                if(noteRoomDatabase == null){
                    //inizializzo il database (il context, nome della classe del database, nome del database-> me lo invento ora )
                    noteRoomDatabase= Room.databaseBuilder(context.getApplicationContext(), NoteRoomDatabase.class, "note_database").build();
                }
            }
        }

        return noteRoomDatabase;
    }

}
