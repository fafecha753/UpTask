package com.example.uptask.Modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uptask.R;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class Adapter_Logros extends BaseAdapter {
    Context context;
    ArrayList<Logro>listaLogros;

    public Adapter_Logros(Context context, ArrayList<Logro>listaLogros){
        this.context=context;
        this.listaLogros=listaLogros;
    }//Fin constructor

    @Override
    public int getCount() {
        return listaLogros.size();
    }

    @Override
    public Object getItem(int i) {
        return listaLogros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return view;
    }
}//Fin clase
