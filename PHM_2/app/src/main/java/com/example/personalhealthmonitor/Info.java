package com.example.personalhealthmonitor;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB -> nel mio DB ho una tabella con 6 colonne per il momento

@Entity(tableName = "report")
public class Info {

    //rifo che qua fa tutto schifo

    @PrimaryKey
    @NonNull
    private String id;

    //GETTER SETTER
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}


