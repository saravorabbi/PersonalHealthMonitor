package com.example.personalhealthmonitor.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "impostazioni")
public class Impostazioni {

    @PrimaryKey//(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "temperaturaSettings")
    private int temperaturaSet;

    @ColumnInfo(name = "pressioneSistolicaSettings")
    private int pressSisSet;

    @ColumnInfo(name = "pressioneDiastolicaSettings")
    private int pressDiaSet;

    @ColumnInfo(name = "glicemiaSettings")
    private int glicemiaSet;

    @ColumnInfo(name = "pesoSettings")
    private int pesoSet;

    // Costruttore
    public Impostazioni(int id, int temperaturaSet, int pressSisSet, int pressDiaSet, int glicemiaSet, int pesoSet) {
        this.id = id;
        this.temperaturaSet = temperaturaSet;
        this.pressSisSet = pressSisSet;
        this.pressDiaSet = pressDiaSet;
        this.glicemiaSet = glicemiaSet;
        this.pesoSet = pesoSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperaturaSet() {
        return temperaturaSet;
    }

    public void setTemperaturaSet(int temperaturaSet) {
        this.temperaturaSet = temperaturaSet;
    }

    public int getPressSisSet() {
        return pressSisSet;
    }

    public void setPressSisSet(int pressSisSet) {
        this.pressSisSet = pressSisSet;
    }

    public int getPressDiaSet() {
        return pressDiaSet;
    }

    public void setPressDiaSet(int pressDiaSet) {
        this.pressDiaSet = pressDiaSet;
    }

    public int getGlicemiaSet() {
        return glicemiaSet;
    }

    public void setGlicemiaSet(int glicemiaSet) {
        this.glicemiaSet = glicemiaSet;
    }

    public int getPesoSet() {
        return pesoSet;
    }

    public void setPesoSet(int pesoSet) {
        this.pesoSet = pesoSet;
    }
}
