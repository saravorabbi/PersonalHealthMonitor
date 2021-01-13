package com.example.personalhealthmonitor;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class InfoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();

    public InfoViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Distrutto");
    }
}
