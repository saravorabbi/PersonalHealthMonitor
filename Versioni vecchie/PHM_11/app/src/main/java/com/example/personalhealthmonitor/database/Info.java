package com.example.personalhealthmonitor.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB -> nel mio DB ho una tabella con 6 colonne per il momento

@Entity(tableName = "report")
public class Info {

    @PrimaryKey     //(autoGenerate = true)
    @NonNull
    private String id;

    @ColumnInfo(name = "infoTemperatura")
    private double temperatura;

    @ColumnInfo(name = "infoPressioneSistolica")
    private double pressioneSistolica;

    @ColumnInfo(name = "infoPressioneDiastolica")
    private double pressioneDiastolica;

    @ColumnInfo(name = "infoGlicemia")
    private double glicemia;

    @ColumnInfo(name = "infoPeso")
    private double peso;

    @ColumnInfo(name = "infoNota")
    private String nota;

    @ColumnInfo(name = "infoData")
    private String data;

    // Costruttore
    public Info(@NonNull String id, double temperatura, double pressioneSistolica, double pressioneDiastolica, double glicemia, double peso, String nota, String data) {
        this.id = id;
        this.temperatura = temperatura;
        this.pressioneSistolica = pressioneSistolica;
        this.pressioneDiastolica = pressioneDiastolica;
        this.glicemia = glicemia;
        this.peso = peso;
        this.nota = nota;
        this.data = data;
    }

    // Getter e setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPressioneSistolica() {
        return pressioneSistolica;
    }

    public void setPressioneSistolica(double pressioneSistolica) {
        this.pressioneSistolica = pressioneSistolica;
    }

    public double getPressioneDiastolica() {
        return pressioneDiastolica;
    }

    public void setPressioneDiastolica(double pressioneDiastolica) {
        this.pressioneDiastolica = pressioneDiastolica;
    }

    public double getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(double glicemia) {
        this.glicemia = glicemia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


