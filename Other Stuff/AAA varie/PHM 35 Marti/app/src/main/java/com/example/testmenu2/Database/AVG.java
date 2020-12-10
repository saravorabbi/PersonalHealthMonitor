package com.example.testmenu2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;


public class AVG{

    @ColumnInfo(name = "media")
    private double media;

    @ColumnInfo(name = "giorno")
    private Date giorno;

    public AVG(double media, Date giorno) {
        this.media = media;
        this.giorno = giorno;
    }

    public double getMedia() {
        return media;
    }

    public Date getGiorno() {
        return giorno;
    }
}
