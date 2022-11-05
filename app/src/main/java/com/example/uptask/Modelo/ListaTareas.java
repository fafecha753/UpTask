package com.example.uptask.Modelo;

import java.util.ArrayList;

public class ListaTareas {
    ArrayList<Tarea> listaTareas;

    public ListaTareas() {

        listaTareas= new ArrayList<Tarea>();
    }


    //Devolver Lista
    public ArrayList<Tarea> devolverLista() {
        return listaTareas;
    }
}
