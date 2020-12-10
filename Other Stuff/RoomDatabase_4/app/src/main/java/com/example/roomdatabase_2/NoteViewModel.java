package com.example.roomdatabase_2;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Implemento i metodi "insert" "delete" dichiarati nell'interface che devo usare per
// interagire col DB

public class NoteViewModel extends AndroidViewModel {

    private String TAGG = "DEBUG - NoteViewModel";
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes; //live data for all notes

    /**
     * ho bisgno di una istanza del DB e del DAO che useremo
     */

    //COSTRUTTORE del note view model in which the application is paused
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
    // WRAPPER METHOD per l'inserimento della nota nel DB
    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    //WRAPPER METHOD ritorna il liveData'mAllNotes' dal noteViewModel alla UI(nella main activity)
    //la uso nel main per prendere tutte le note in the UI e popolare la recycler lista con le note
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    //WRAPPER METHOD per fare l'update della nota
    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    //WRAPPER METHOD per l'eliminazione della nota
    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }


    //Superclasse che generalizza le classi async
    private class OperationAsyncTask extends  AsyncTask<Note, Void, Void>{

        NoteDao mAsyncTaskDao;

        //costruttore
        OperationAsyncTask(NoteDao dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }


    // Creo async task, per eseguire il task in background
    // (deprecato, vedi se lo puoi sosttuire con qualcos'altro)!!!!!!!!!!!!!

    //Async task per l'operazione Insert
    private class InsertAsyncTask extends OperationAsyncTask {

        //costruttore
        public InsertAsyncTask(NoteDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes){
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }

    }

    //Async task per l'operazione Update
    private class UpdateAsyncTask extends OperationAsyncTask {

        //costruttore
        public UpdateAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    //Async task per l'operazione Delete
    private class DeleteAsyncTask extends OperationAsyncTask {

        //costruttore
        public DeleteAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}
