package com.example.databaseroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// FRAGMENT ADD USER -> form con info da aggiungere al database

public class AddUserFragment extends Fragment {

    private EditText UserId, UserName, UserEmail;
    private Button BnSave;


    public AddUserFragment() {
        // Required empty public constructor
    }


 //   public static AddUserFragment newInstance(String param1, String param2) {
 //
 //   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        UserId = view.findViewById(R.id.txt_user_id);
        UserName = view.findViewById(R.id.txt_name);
        UserEmail = view.findViewById(R.id.txt_email);
        BnSave = view.findViewById(R.id.bn_save_user);

        BnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recupero le info dal form e le metto in variabili temporali
                int userid = Integer.parseInt(UserId.getText().toString());
                String username = UserName.getText().toString();
                String useremail = UserEmail.getText().toString();

                //ora aggiungo le info nel DATABASE -> nella mainActivity abbiamo dichiarato l'oggetto database

                //salvo le info dentro un oggetto USER che poi aggiungerò dentro il db
                User user = new User();
                user.setId(userid);
                user.setName(username);
                user.setEmail(useremail);

                //aggiungo user nel database
                MainActivity.myAppDatabase.myDao().addUser(user);
                Toast.makeText(getActivity(), "User added successfully", Toast.LENGTH_LONG).show();

                //ora pulisco l'edit text
                UserId.setText("");
                UserName.setText("");
                UserEmail.setText("");

            }
        });

        return view;
    }
}