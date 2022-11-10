package com.example.uptask.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uptask.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter{
    Context context;
    ArrayList<Tarea> lista;

    public Adapter(Context context, ArrayList<Tarea> lista) {
        this.context = context;
        this.lista = lista;
    }//Fin del constructor

    //Devuelve el tamaño de la lista
    @Override
    public int getCount() {
        return lista.size();
    }

    //Devuelve un objeto en una posición
    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    //Retorna una posicion
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_tarea,null);
        }
        TextView lbTitulo = view.findViewById(R.id.txtT);
        TextView lbdesc = view.findViewById(R.id.txtDT);
        TextView lbDia = view.findViewById(R.id.txtDia);
        TextView lbtMes = view.findViewById(R.id.txtMes);

        lbTitulo.setText(lista.get(i).getTitulo());
        lbdesc.setText(lista.get(i).getDescripcion());
        lbDia.setText(lista.get(i).getDia());
        lbtMes.setText(lista.get(i).getMes());
        return view;
    }
}
