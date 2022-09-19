package com.example.uptask.Modelo;

import java.util.ArrayList;

public class Registro {
    ArrayList<Usuario> listaUsuarios;

    public Registro(){
        listaUsuarios = new ArrayList<Usuario>();
    }

    //Agregar
    public String agregarUsuario(Usuario usuario){
        if(usuario != null){
            if(listaUsuarios.contains(usuario)){
                return "El usuario ya existe";
            }else{
                listaUsuarios.add(usuario);
                return "Usuario agregado con exito";
            }
        }
        return "Error";
    }

    //Mostrar Usuario
    public Usuario mostrarUsuario(int posicion){
        if(posicion != -1){
            return listaUsuarios.get(posicion);
        }else{
            return null;
        }
    }

    //Devolver Lista
    public ArrayList<Usuario> devolverLista(){
        return listaUsuarios;
    }

    //Encontrar Posicion con nombreUsuario
    public int getPosicion(int id){
        for (int i =0; i<listaUsuarios.size();i++){
            if(listaUsuarios.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    //Encontrar Posicion con nombreUsuario
    public String getUsuario(String nombreUsuario){
        for (int i =0; i<listaUsuarios.size();i++){
            if(listaUsuarios.get(i).getNombreUsuario().equalsIgnoreCase(nombreUsuario)){
                return listaUsuarios.get(i).getNombreUsuario();
            }
        }
        return null;
    }

    //Eliminar con Id
    public String eliminarUsuario(int id){
        if(id != -1){
            listaUsuarios.remove(id);
            return "Usuario eliminado con exito";
        }
        return "Error al eliminar el usuario";
    }

    //Modificar Pedido
    public String editarUsuario(Usuario usuario){
        int indice = getPosicion(usuario.getId());
        if(usuario != null && indice != -1){
            listaUsuarios.set(indice, usuario);
            return "Usuario editado con exito";
        }
        return "Error";
    }
}
