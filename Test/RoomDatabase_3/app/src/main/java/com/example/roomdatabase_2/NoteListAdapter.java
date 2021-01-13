package com.example.roomdatabase_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.xml.xpath.XPathFunctionResolver;

//ADAPTER per la RECYCLER VIEW -> è la Recycler View
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAGG = "DEBUG - NoteListAdapter";

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Note> mNotes; //we need to inflates this Note in the RecyclerView

    // costruttore
    public NoteListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    //qua si fa l'inflate delle Note
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(mNotes != null){
            Note note = mNotes.get(position);
            holder.setData(note.getNote(), position);

            holder.setListenerss();

        } else{
            // Covers the case of data not being ready yet
            holder.noteItemView.setText(R.string.no_note);
        }

    }

    @Override
    public int getItemCount() {
        if(mNotes != null)
            return  mNotes.size();
        else return 0;
    }

    //notifica il cambiamento della nota (???)
    //fetch data from DB, e setto le note in questa var. A questo punto notifico il cambiamento
    //e nella mainActivity popolo la lista di note
    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    //CLASSE NoteViewHolder
    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView noteItemView;
        private  int mPosition;
        private ImageView imgDelete, imgEdit;   //bottoni nella list_item.xml per modifica e bin

        //costruttore
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            //collego i bottoni nella list_item.xml per modificare e eliminare
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String note, int position) {
            noteItemView.setText(note);
            mPosition = position;
        }

        // Funzione che setta i listener per i due pulsanti (copy e bin) in list_item.xml
        public void setListenerss() {

            imgEdit.setOnClickListener(new View.OnClickListener() {
                //1 - fetch ID della nota e mandarla alla EDIT ACTIVITY che farà fetch della nota e popolerà la EditNoteActivity
                @Override
                public void onClick(View view) {
                    Log.i(TAGG, "Ho cliccato la edit");

                    //intent che ci permette di passare alla EditNoteActivity (mContext-> context della mainActivity)
                    Intent intent = new Intent(mContext, EditNoteActivity.class);
                    //prendo l'ID della nota che devo passare e lo passo
                    intent.putExtra("note_id", mNotes.get(mPosition).getId());
                    //quando l'activity di Edit finisce devo tornare qui
                    ((Activity)mContext).startActivityForResult(intent, MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAGG, "Ho cliccato la delete");
                }
            });
        }
    }
}
