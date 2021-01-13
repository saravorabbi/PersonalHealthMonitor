package com.example.personalhealthmonitor;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB -> nel mio DB ho una tabella con 6 colonne per il momento

@Entity(tableName = "report")
public class Info {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private String id;

    @ColumnInfo(name = "infoTemperatura")
    private double temperatura;

    @ColumnInfo(name = "infoPressione")
    private int pressione;

    @ColumnInfo(name = "infoGlicemia")
    private int glicemia;

    @ColumnInfo(name = "infoPeso")
    private double peso;

    @ColumnInfo(name = "infoNota")
    private String nota;

    @ColumnInfo(name = "infoData")
    private String data;

    // Costruttore
    public Info(@NonNull String id, double temperatura, int pressione, int glicemia, double peso, String nota, String data) {
        this.id = id;
        this.temperatura = temperatura;
        this.pressione = pressione;
        this.glicemia = glicemia;
        this.peso = peso;
        this.nota = nota;
        this.data = data;
    }

    // Getter e setter
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getPressione() {
        return pressione;
    }

    public void setPressione(int pressione) {
        this.pressione = pressione;
    }

    public int getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(int glicemia) {
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


