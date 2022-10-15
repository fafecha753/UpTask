package com.example.uptask.Modelo;

import java.util.ArrayList;

public class ListaLogros {

    ArrayList<Logro> listaLogros;

    public ListaLogros() {

        listaLogros = new ArrayList<Logro>();
    }


    //Devolver Lista
    public ArrayList<Logro> devolverLista() {
        return listaLogros;
    }
}
