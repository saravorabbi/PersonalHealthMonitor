package com.example.personalhealthmonitor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.utils.UndoAction;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context thisContext;

    //lista di Info che si inserirà nella RecyclerView
    private List<Info> infoList;

    public InfoListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.thisContext = context;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater. inflate(R.layout.lista_report, parent, false);
        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        //se la lista ha degli elementi, metto la lista dentro l'holder
        if(infoList != null){
            Info info = infoList.get(position);

            //funzione della classe Info View Holder (sotto)
            holder.setData(info.getTemperatura(), info.getPressioneSistolica(),info.getPressioneDiastolica(), info.getGlicemia(), info.getPeso(), info.getNota(), position);

            //setto i listener per i pulsanti di edit info e delete
            holder.setListeners();

        } else{
            //caso in cui i non ci sono report dati nel DB
            holder.notaLA.setText(R.string.no_report);
        }
    }

    @Override
    public int getItemCount() {
        if(infoList != null){
            int num = infoList.size();
            return  num;
        } else{
            return 0; //se la lista è vuota ritorno zero
        }
    }


    public void setInfos(List<Info> infos){
        //Log.i(DEBUG, "set Infos");
        infoList = infos;
        notifyDataSetChanged();
    }



    //CLASSE INFO VIEW HOLDER
    public class InfoViewHolder extends RecyclerView.ViewHolder{

        private TextView temperaturaLA, pressioneSisLA, pressioneDiaLA, glicemiaLA, pesoLA, notaLA;
        private int posizioneInLista;
        private ImageView imgEditBtn, imgDeleteBtn;

        //Costruttore
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);

            //id dei valori da prendere nel DB (nella lista_report)
            temperaturaLA = itemView.findViewById(R.id.list_new_temp);
            pressioneSisLA = itemView.findViewById(R.id.list_new_pressione_sistolica);
            pressioneDiaLA = itemView.findViewById(R.id.list_new_pressione_diastolica);
            glicemiaLA = itemView.findViewById(R.id.list_new_glicemia);
            pesoLA = itemView.findViewById(R.id.list_new_peso);
            notaLA = itemView.findViewById(R.id.list_new_nota);

            //immagini bottoni che editano ed eliminano le note da DB (nella lista_report)
            imgEditBtn = itemView.findViewById(R.id.list_new_edit);
            imgDeleteBtn = itemView.findViewById(R.id.list_new_trash);

        }

        //funzione che mette i dati del DB nei TextView della cardView (per mostrarli)
        public void setData(double temp, double pressSIS, double pressDIA, double glic, double peso, String note, int position){
            posizioneInLista = position;

            //se i campi sono nulli, viene inserito 0.0 in modo automatico
            temperaturaLA.setText(String.valueOf(temp));
            pressioneSisLA.setText(String.valueOf(pressSIS));
            pressioneDiaLA.setText(String.valueOf(pressDIA));
            glicemiaLA.setText(String.valueOf(glic));
            pesoLA.setText(String.valueOf(peso));
            notaLA.setText(note);

        }

        public void setListeners() {

            //listener immagine modifica report
            imgEditBtn.setOnClickListener(view -> {

                //intent che ci permette di passare alla EditNoteActivity (mContext-> context della mainActivity)
                Intent intent = new Intent(thisContext, EditInfoActivity.class);

                //prendo l'ID e la data della nota che devo passare (e non devono essere modificate)
                intent.putExtra("info_id", infoList.get(posizioneInLista).getId());
                intent.putExtra("info_data", infoList.get(posizioneInLista).getData());

                thisContext.startActivity(intent);

            });

            //listener immagine cancellazione report
            imgDeleteBtn.setOnClickListener(view -> {

                Info infoRepo = infoList.get(posizioneInLista);

                //elimino il report dal DB
                infoViewModel.delete(infoRepo);

                //annulla azione
                UndoAction undoAction = new UndoAction();
                undoAction.deletedInfo(infoRepo);
                Snackbar snackbar = Snackbar.make(view, "Report cancellato", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.setAction("Annulla", undoAction);
                snackbar.show();
            });

        }
    }
}
