package com.example.personalhealthmonitor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalhealthmonitor.database.Info;

import java.util.List;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;

//ADAPTER per la RECYCLER VIEW -> è la Recycler View
//codice to inflate the recycler view con i report
public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    private static final String DEBUG = "DEBUG: InfoListAdapter";

    private static final String TAGG = "DEBUG - InfoListAdapter";

    private final LayoutInflater layoutInflater;
    private Context thisContext;
    private List<Info> infoList; //we need to inflates this Note in the RecyclerView

    public InfoListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        thisContext = context;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater. inflate(R.layout.lista_report, parent, false);
        InfoViewHolder viewHolder = new InfoViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        //se la lista ha degli elementi, metto la lista dentro l'holder
        if(infoList != null){
            Info info = infoList.get(position);
            //funzione della classe Info View Holder (sotto)
            holder.setData(info.getTemperatura(), info.getPressioneSistolica(),info.getPressioneDiastolica(), info.getGlicemia(), info.getPeso(), info.getNota(), position);   //getInfo viene dalla classe Info.java, è un getter
            //setto i listener per i pulsanti di edit info e delete
            holder.setListeners();

        } else{
            // Caso in cui i non ci sono reporto dati nel DB
            holder.notaLA.setText(R.string.no_report);
        }
    }

    @Override
    public int getItemCount() {
        if(infoList != null){
            int num = infoList.size();
            Log.i(DEBUG, "lista NON vuota - ho " + num + " reportss");
            return  num;
        } else{
            Log.i(DEBUG, "lista vuota");
            return 0; //se la lista è vuota ritorno zero
        }
    }



    //notifica il cambiamento della nota (???)
    //fetch data from DB, e setto le nuove note in questa var. A questo punto notifico il cambiamento
    //e nella Activity popolo la lista di note
    public void setInfos(List<Info> infos){
        Log.i(DEBUG, "set Infos");
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

            //può essere che crashi tutto se i campi sono nulli => bisognerebbe fare un controllo if!=null
            //i campi non sono nulli perché se non inserisci nulla mette 0.0 in modo automatico

            temperaturaLA.setText(Double.toString(temp));
            pressioneSisLA.setText(Double.toString(pressSIS));
            pressioneDiaLA.setText(Double.toString(pressDIA));
            glicemiaLA.setText(Double.toString(glic));
            pesoLA.setText(Double.toString(peso));
            notaLA.setText(note);

        }

        public void setListeners() {
            imgEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAGG,"sto cliccando il pulsante per EDITARE il report");

                    //intent che ci permette di passare alla EditNoteActivity (mContext-> context della mainActivity)
                    Intent intent = new Intent(thisContext, EditInfoActivity.class);

                    //prendo l'ID e la data della nota che devo passare (e non devono essere modificate)
                    intent.putExtra("info_id", infoList.get(posizioneInLista).getId());
                    intent.putExtra("info_data", infoList.get(posizioneInLista).getData());

                    thisContext.startActivity(intent);
                    //((Activity)thisContext).startActivity(intent);

                }
            });

            imgDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAGG,"sto cliccando il pulsante per ELIMINARE il report");

                    Info infoRepo = infoList.get(posizioneInLista);
                    //elimino il report dal DB
                    infoViewModel.delete(infoRepo);

                    Log.i(TAGG,"report eliminato!!");

                }
            });

        }
    }
}
