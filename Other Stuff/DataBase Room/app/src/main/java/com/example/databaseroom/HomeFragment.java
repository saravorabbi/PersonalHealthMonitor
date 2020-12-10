package com.example.databaseroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//FRAGMENT -> ADD USER, DELETE USER ETC (operazioni del database)

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button BnAddUser;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        BnAddUser = view.findViewById(R.id.bn_add_user);

        BnAddUser.setOnClickListener(this);
        return view;
    }

    // Metodo implementato causa "implements View.OnClickListener"
    @Override
    public void onClick(View view) {

        switch(view.getId()){

            //in questo caso devo dispayare il fragment ADD USER
            case R.id.bn_add_user:
                //prendo il fragmentContainer e lo rimpiazzo, il fragment container lo metto nello stack (anche se non ho idea di cosa voglia dire)
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddUserFragment()).addToBackStack(null).commit();
                break;


        }
    }



}