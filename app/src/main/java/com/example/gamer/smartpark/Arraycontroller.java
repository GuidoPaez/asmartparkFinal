package com.example.gamer.smartpark;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

//A llamar a esta clase, puedo llamar a cualquier arreglo que esté asociado a esta clase, incluso incluye setiarlo, ej:crear arreglo en otro activity,puedo ponerle el mismo nombre y puedo setearlo, luego llamarlo de
//cualquier otro lado y estará ahí.

public class Arraycontroller {
    private ArrayList<Marker> marker;
    public static Arraycontroller controller;

    private Arraycontroller(){
        //constructor de la clase, si la llamo es lo primero que se ejecutará
        marker = new ArrayList<Marker>();

    }
    public static Arraycontroller getInstance (){
        if (controller==null){
            controller = new Arraycontroller();
        }
        return controller;
    }

    public ArrayList<Marker> getMarker() {
        return marker;
    }

    public void setMarker(ArrayList<Marker> marker) {
        this.marker = marker;
    }
}
