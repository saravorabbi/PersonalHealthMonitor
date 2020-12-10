package com.example.recyclerviewcardview;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//MyAdapter.MyViewHolder perché MyViewHolder è una classe definita dentro MyAdapter
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<IconModel> objectList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<IconModel> objectList) {
        layoutInflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    // XML layout will be rendered by creating view object in memory by the Android OS

    @NonNull
    @Override   //inflato col file XML 'list_parent'
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chiamato quando si crea una view
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override   //chiamato per OGNI elemento list_item
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IconModel current = objectList.get(position);
        holder.setData(current, position);
    }

    // Definisco classe MyViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView imageThumb, imgDelete, imgCopy;
        private int position;
        private IconModel curretObject;

        //costruttore
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            imageThumb = (ImageView) itemView.findViewById(R.id.image_thumb);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            imgCopy = (ImageView) itemView.findViewById(R.id.img_copy);
        }

        public void setData(IconModel curretObject, int position) {
            this.title.setText(curretObject.getTitle());
            this.imageThumb.setImageResource(curretObject.getImageID());
            this.position = position;
            this.curretObject = curretObject;
        }
    }
}
