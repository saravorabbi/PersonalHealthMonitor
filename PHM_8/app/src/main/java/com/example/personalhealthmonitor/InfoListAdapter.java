package com.example.personalhealthmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



//ADAPTER per la RECYCLER VIEW -> è la Recycler View
//codice to inflate the recycler view con i report
public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    private static final String TAGG = "DEBUG - InfoListAdapter";

    private final LayoutInflater layoutInflater;
    private Context thisContext;
    private List<Info> infoList; //we need to inflates this Note in the RecyclerView
    //private OnDeleteClickListener onDeleteClickListener;

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
            holder.setData(info.getTemperatura(), info.getPressione(), info.getGlicemia(), info.getPeso(), info.getNota(), position);   //getInfo viene dalla classe Info.java, è un getter

            //holder.setListenerss();

        } else{
            // Caso in cui i non ci sono reporto dati nel DB
            holder.notaLA.setText(R.string.no_report);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }



    //notifica il cambiamento della nota (???)
    //fetch data from DB, e setto le note in questa var. A questo punto notifico il cambiamento
    //e nella mainActivity popolo la lista di note
    public void setInfos(List<Info> infos){
        infoList = infos;
        notifyDataSetChanged();
    }



    //CLASSE INFO VIEW HOLDER
    public class InfoViewHolder extends RecyclerView.ViewHolder{

        private TextView temperaturaLA, pressioneLA, glicemiaLA, pesoLA, notaLA;
        private int posizioneInLista;

        //Costruttore
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            temperaturaLA = itemView.findViewById(R.id.list_new_temp);
            pressioneLA = itemView.findViewById(R.id.list_new_pressione);
            glicemiaLA = itemView.findViewById(R.id.list_new_glicemia);
            pesoLA = itemView.findViewById(R.id.list_new_peso);
            notaLA = itemView.findViewById(R.id.list_new_nota);

        }

        //funzione che mette i dati del DB nei TextView della cardView (per mostrarli)
        public void setData(double temp, double press, double glic, double peso, String note, int position){
            posizioneInLista = position;

            //può essere che crashi tutto se i campi sono nulli => bisognerebbe fare un controllo if!=null

            temperaturaLA.setText(Double.toString(temp));
            pressioneLA.setText(Double.toString(press));
            glicemiaLA.setText(Double.toString(glic));
            pesoLA.setText(Double.toString(peso));
            notaLA.setText(note);

        }

    }
}
