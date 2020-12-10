package com.example.testmenu2.home;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu2.Database.Report;
import com.example.testmenu2.Database.ReportViewModel;
import com.example.testmenu2.R;
import com.example.testmenu2.Utilities.Converters;
import com.example.testmenu2.Utilities.OnSwipeTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static ReportListAdapter reportListAdapter;
    public static ReportViewModel reportViewModel;
    public static LiveData<List<Report>> mReports;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //TODAY REPORT
        TextView todayreportVal = root.findViewById(R.id.TXVTodayReport_val);
        TextView TXVBattiti = root.findViewById(R.id.TXVbattito);
        TextView TXVPressione = root.findViewById(R.id.TXVpressione);
        TextView TXVTemperatura = root.findViewById(R.id.TXVtemperatura);
        TextView TXVGlicemia = root.findViewById(R.id.TXVglicemia);

        //CONTAINER MAIN
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        reportListAdapter = new ReportListAdapter(getContext());
        recyclerView.setAdapter(reportListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);


        //QUANDO CAMBIA IL GIORNO IN CONSIDERAZIONE
        homeViewModel.getSGiorno().observe(getViewLifecycleOwner(), new Observer<Date>() {
            @Override
            public void onChanged(Date s) {

                //CAMBIA LA DATA SCRITTA IN ALTO
                todayreportVal.setText(Converters.DateToString(s));

                //CAMBIANO I VALORI MEDI
                LiveData<Double> battiti = reportViewModel.getAVGInDate("battito", s);
                battiti.observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        TXVBattiti.setText(nullValue(tronca(aDouble)));
                    }
                });

                LiveData<Double> pressione = reportViewModel.getAVGInDate("pressione", s);
                pressione.observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        TXVPressione.setText(nullValue(tronca(aDouble)));
                    }
                });

                LiveData<Double> temperatura = reportViewModel.getAVGInDate("temperatura", s);
                temperatura.observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        TXVTemperatura.setText(nullValue(tronca(aDouble)));
                    }
                });

                LiveData<Double> glicemia = reportViewModel.getAVGInDate("glicemia", s);
                glicemia.observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        TXVGlicemia.setText(nullValue(tronca(aDouble)));
                    }
                });

                //CAMBIA LA LISTA DEGLI ELEMENTI
                mReports = reportViewModel.getAllReportsInDate(s);
                mReports.observe(getViewLifecycleOwner(), new Observer<List<Report>>() {
                    @Override
                    public void onChanged(List<Report> reports) {
                        reportListAdapter.setReports(reports);
                    }
                });
            }
        });

        //GESTISCE LO SWIPE TOP BOT LEFT E RIGHT
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
               homeViewModel.Ieri();
            }

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                homeViewModel.Domani();
            }
        });

        //FAB
        FloatingActionButton fab = root.findViewById(R.id.FabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendReportActivity.class);
               // startActivity(intent);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(fab,"activity_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                startActivity(intent,options.toBundle());
            }
        });


        return root;
    }

    //SE NON HO INSERITO DEI VALORI == 0 ALLORA STAMPO NULL A SCHERMO
    private String nullValue(Double val){
        if(val == 0){
            return "null";
        }
        return String.valueOf(val);
    }

    private double tronca(Double num){
        if(num == null ) return 0;
        else{
            num = num * 100;
            num = (double) Math.round(num);
            num = num / 100;
            return num;
        }

    }
}