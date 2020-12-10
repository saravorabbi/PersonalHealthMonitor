package com.example.databaseroom;

import androidx.room.Dao;
import androidx.room.Insert;

// Data Access Object
// In questa CLASSE ASTRATTA metto tutti i METODI che mi permettono di aggiungere e eliminare etc
// info dal database

@Dao
public interface MyDao {
// specify possible method for database operations within this interface
    //insertion
    //selection
    //deletion
    //abdecion????


    @Insert
    public void addUser(User user);


}
