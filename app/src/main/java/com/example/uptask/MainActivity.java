package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnRegistrarse;
    Button btnIniciarSesion;
    //declaracion de la variable que almacena el usuario  de firebase
    private FirebaseAuth mAuth;

    //cuando se visualiza la pantalla verifica si hay un usuario logueado
    @Override
    public void onStart() {
        super.onStart();
        //toma el usuario que haya logueado, debolviendo null si no hay uno
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //si existe un usuario logueado entonces lo redirige a la pantalla home
            home();
        }
    }// fin del onstart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        mAuth= FirebaseAuth.getInstance();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                cambiarIniciarSesion(view);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Abrir pagina Registro", Toast.LENGTH_SHORT).show();
            }
        });

    }//fin del oncreate


    //Metodo de llamada a la vista de inicio de sesión
    public void cambiarIniciarSesion(View view){
        Intent cambiarInicioSesion = new Intent(this, activity_inicioSesion.class);
        startActivity(cambiarInicioSesion);
    }
    //metodo de llamada a la vista de registro
    public void cambiarRegistrarse(View view){
        Intent cambiarRegistro = new Intent(this, activity_inicioSesion.class);
        startActivity(cambiarRegistro);
    }
    //metodo de llamada a la pantalla home
    public void home(){
        Intent inicioSesion = new Intent(this, activity_sesionIniciada.class);
        startActivity(inicioSesion);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}