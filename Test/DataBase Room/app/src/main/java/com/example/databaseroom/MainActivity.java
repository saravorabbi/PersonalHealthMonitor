package com.example.databaseroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    //crep variabile di classe database (questo è il database)
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aggiungo il  fragment al Fragmet_container
        fragmentManager = getSupportFragmentManager();

        //inizializzo il database (il context, nome della classe del database, nome del database-> me lo invento ora )
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "userdb").build();
        //con questo oggetto posso fare le transaction del database

        if(findViewById(R.id.fragment_container) != null){

            if(savedInstanceState != null){
                return;
            }
            //aggiunge la HomeFragment (UN FRAGMENT) alla main activity
            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
        }
    }
}