package com.example.personalhealthmonitor.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabella del DB -> nel mio DB ho una tabella con 6 colonne per il momento

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "parameter")
    private String parameter;

    @ColumnInfo(name = "value")
    private int value;

    @ColumnInfo(name = "lower_bound")
    private double lowerBound;

    @ColumnInfo(name = "upper_bound")
    private double upperBound;

    @ColumnInfo(name = "begin_date")
    private String beginDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    public Settings(int id, String parameter, int value, double lowerBound, double upperBound, String beginDate, String endDate) {
        this.id = id;
        this.parameter = parameter;
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }


    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
