package com.example.recyclerviewcardview;

import java.util.ArrayList;
import java.util.List;

public class IconModel {

    private int ImageID;
    private String title;


    //GETTER E SETTER
    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        this.ImageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<IconModel> getObjectList(){
        List<IconModel> dataList = new ArrayList<>();
        int[] images = getImages();

        //Loop che aggiunge gli elementi dentro il vettore images dentro la lista
        for(int i = 0; i < images.length; i++){
            IconModel nature = new IconModel();
            nature.setImageID(images[i]);
            nature.setTitle("Picture " + i);
            dataList.add(nature);
        }

        return dataList;
    }

    //metto dentro un vettore images tutte le immagini chevoglio inserire nel recycler view
    private static int[] getImages() {
        int[] images = {R.drawable.apple, R.drawable.coconut, R.drawable.peach, R.drawable.whisky};

        return images;
    }


}
