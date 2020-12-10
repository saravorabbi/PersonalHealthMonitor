package com.example.testmenu2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "settings_valore")
    private String valore;

    @ColumnInfo(name = "settings_importanza")
    private int importanza;

    @ColumnInfo(name = "settings_inizio")
    private Date inizio;

    @ColumnInfo(name = "settings_fine")
    private Date fine;

    @ColumnInfo(name = "settings_limite")
    private double limite;

    public Settings(int id, String valore, int importanza, Date inizio, Date fine, double limite) {
        this.id = id;
        this.valore = valore;
        this.importanza = importanza;
        this.inizio = inizio;
        this.fine = fine;
        this.limite = limite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public int getImportanza() {
        return importanza;
    }

    public void setImportanza(int importanza) {
        this.importanza = importanza;
    }

    public Date getInizio() {
        return inizio;
    }

    public void setInizio(Date inizio) {
        this.inizio = inizio;
    }

    public Date getFine() {
        return fine;
    }

    public void setFine(Date fine) {
        this.fine = fine;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}
