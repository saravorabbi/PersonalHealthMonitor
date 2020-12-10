package com.example.recyclerviewcardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ora likiamo la RecyclerView con il CustomAdapter
        //Il che significa che prendiamo l'Adapter (che sono le righe orizzontali in list_item.XML)
        // e andrano a moltiplicarsi nel RecyclerView (che è il contenitore della lista)

        //collego l'istanza della RecyclerView col file XML
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //istanza del myAdapter: context, objectList
        MyAdapter adapter = new MyAdapter(this, IconModel.getObjectList());
        recyclerView.setAdapter(adapter);

        //LINK THE LAYOUT MANAGER FOR RECYCLERVIEW
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}