package com.example.roomdatabase_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.xml.xpath.XPathFunctionResolver;

//ADAPTER per la RECYCLER VIEW -> è la Recycler View
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Note> mNotes; //we need to inflates this Note in the RecyclerView

    //costruttore
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

        //costruttore
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
        }

        public void setData(String note, int position) {
            noteItemView.setText(note);
            mPosition = position;
        }
    }
}
