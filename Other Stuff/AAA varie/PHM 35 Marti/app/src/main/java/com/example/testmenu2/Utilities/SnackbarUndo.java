package com.example.testmenu2.Utilities;

import android.view.View;

import com.example.testmenu2.Database.Report;

import static com.example.testmenu2.home.HomeFragment.reportViewModel;


public class SnackbarUndo implements View.OnClickListener {

    Report reportRimosso;

    @Override
    public void onClick(View v) {

        reportViewModel.setReport(reportRimosso);

    }

    public void reportRimosso(Report report) {
        this.reportRimosso = report;
    }
}
