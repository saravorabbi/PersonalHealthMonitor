package com.example.personalhealthmonitor.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB -> nel mio DB ho una tabella con 6 colonne per il momento

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "value")
    private int value;

    @ColumnInfo(name = "lower_bound")
    private int lowerBound;

    @ColumnInfo(name = "upper_bound")
    private int upperBound;

    @ColumnInfo(name = "begin_date")
    private int beginDate;

    @ColumnInfo(name = "end_date")
    private int endDate;


    public Settings(int value, int lowerBound, int upperBound, int beginDate, int endDate) {
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }
}
