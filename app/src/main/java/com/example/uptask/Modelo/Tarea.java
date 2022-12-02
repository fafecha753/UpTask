package com.example.uptask.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarea implements Parcelable {

    private String usuario, titulo, descripcion, categoria, fecha_limite, hora, id;
    int alarmID;
    public Tarea() {
    }

    public Tarea(String usuario, String titulo, String descripcion, String categoria, String fecha_limite, String id, int alarmID) {
        this.id= id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha_limite = fecha_limite;
        this.alarmID= alarmID;

    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }
    public String getDia() {
        String[] s=fecha_limite.split("-");
        return s[2];
    }
    public String getMes() {
        String[] s=fecha_limite.split("-");
        String mes="";
        if("01".equalsIgnoreCase(s[1])) {
         mes="Ene";
        }else if("02".equalsIgnoreCase(s[1])){
            mes="Feb";
        }else if("03".equalsIgnoreCase(s[1])){
            mes="Mar";
        }else if("04".equalsIgnoreCase(s[1])){
            mes="Abr";
        }else if("05".equalsIgnoreCase(s[1])){
            mes="May";
        }else if("06".equalsIgnoreCase(s[1])){
            mes="Jun";
        }else if("07".equalsIgnoreCase(s[1])){
            mes="Jul";
        }else if("08".equalsIgnoreCase(s[1])){
            mes="Ago";
        }else if("09".equalsIgnoreCase(s[1])){
            mes="Sep";
        }else if("10".equalsIgnoreCase(s[1])){
            mes="Oct";
        }else if("11".equalsIgnoreCase(s[1])){
            mes="Nov";
        }else if("12".equalsIgnoreCase(s[1])){
            mes="Dic";
        }
        return mes;
    }

    public static final Parcelable.Creator<Tarea> CREATOR= new Parcelable.Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel parcel) {
            return new Tarea();
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(usuario);
        parcel.writeString(titulo);
        parcel.writeString(descripcion);
        parcel.writeString(categoria);
        parcel.writeString(fecha_limite);
        parcel.writeString(id);
    }
    @Override
    public int describeContents() {
        return 0;
    }


}
