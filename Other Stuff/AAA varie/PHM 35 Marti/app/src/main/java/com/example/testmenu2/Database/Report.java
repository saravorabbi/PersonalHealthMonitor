package com.example.testmenu2.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "reports")
public class Report {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "report_giorno")
    private Date giorno;

    @ColumnInfo(name = "report_ora")
    private String ora;

    @ColumnInfo(name = "report_battito")
    private double battito;

    @ColumnInfo(name = "report_temperatura")
    private double temperatura;

    @ColumnInfo(name = "report_pressione")
    private double pressione;

    @ColumnInfo(name = "report_glicemia")
    private double glicemia;

    @ColumnInfo(name = "report_nota")
    private String nota;

    //COSTRUTTORE

    public Report(int id, Date giorno, String ora, double battito, double temperatura, double pressione, double glicemia, String nota) {
        this.id = id;
        this.giorno = giorno;
        this.ora = ora;
        this.battito = battito;
        this.temperatura = temperatura;
        this.pressione = pressione;
        this.glicemia = glicemia;
        this.nota = nota;
    }


    //SETTER AND GETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getGiorno() {
        return giorno;
    }

    public void setGiorno(Date data) {
        this.giorno = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public double getBattito() {
        return battito;
    }

    public void setBattito(double battito) {
        this.battito = battito;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPressione() {
        return pressione;
    }

    public void setPressione(double pressione) {
        this.pressione = pressione;
    }

    public double getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(double glicemia) {
        this.glicemia = glicemia;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}

