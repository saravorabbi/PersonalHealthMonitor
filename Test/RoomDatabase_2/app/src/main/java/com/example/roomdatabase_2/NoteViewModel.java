package com.example.roomdatabase_2;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Implemento i metodi "insert" "delete" dichiarati nell'interface che devo usare per
// interagire col DB

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes; //live data for all notes

    /**
     * ho bisgno di una istanza del DB e del DAO che useremo
     *
      *
     */

    //COSTRUTTORE del note view model in wich the application is paused
    public NoteViewModel(Application application){
        super(application);

        //fetching the instance of the database, ho bisogno di passare l'application context -> per questo uso "extends AndroidViewModel"
        //perchè ci lascia passare il context
        noteDB = NoteRoomDatabase.getMyAppDatabase(application);
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();  //con live data, l'operazione sarà eseguita in background in automatico
                                            //quindi no AsyncTask needed
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Distrutto");
    }

    //dobbiamo eseguire questa operazione nel thread non UI -> uso async task
    // WRAPPER METHOD FOR INSERT FUNCTION
    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    //WRAPPER METHOD ritorna il liveData'mAllNotes' dal noteViewModel alla UI(nella main activity)
    //la uso nel main per prendere tutte le note in the UI e popolare la recycler lista con le note
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    //creo async task, per eseguire il task in background
    // (deprecato, vedi se lo puoi sosttuire con qualcos'altro)
    private class InsertAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao mNoteDao;

        public InsertAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes){
            mNoteDao.insert(notes[0]);
            return null;
        }

    }

}
