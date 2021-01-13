package com.example.roomdatabase_2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase db;

    //costruttore
    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = NoteRoomDatabase.getMyAppDatabase(application);
        noteDao = db.noteDao();
    }

    //WRAPPER per avere la nota -> usiamo questo metodo nella EditNoteActivity per fare fetch della nota e poi modificarla
    public LiveData<Note> getNote(String noteId){
        return noteDao.getNote(noteId);
        /*6:10 min */
    }

}
