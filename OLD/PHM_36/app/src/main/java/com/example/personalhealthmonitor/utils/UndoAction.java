package com.example.personalhealthmonitor.utils;

import android.view.View;

import com.example.personalhealthmonitor.database.Info;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;

public class UndoAction implements View.OnClickListener {

    Info deleteInfo;

    @Override
    public void onClick(View view) {
        infoViewModel.insert(deleteInfo);
    }

    //salvo il report rimosso
    public void deletedInfo(Info info){
        this.deleteInfo = info;
    }

}
