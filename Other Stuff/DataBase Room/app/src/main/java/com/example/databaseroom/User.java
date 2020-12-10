package com.example.databaseroom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// User è una TABELLA (ENTITA'), per ogni tabella c'è bisogno di una classe
// Ogni campo della classe corrisponde a una COLONNA della tabella

@Entity(tableName = "users")    //cambio il nome della tabella da "User" a "users"
public class User {

    // Definisco il numero di colonne nella mia tabella (qui 3)

    @PrimaryKey
    private int id;     //id è la primary key

    @ColumnInfo(name = "user_name")     //cambio il nome della colonna da "name" a "user_name"
    private String name;

    @ColumnInfo(name = "user_email")
    private  String email;


    // Getter e Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
