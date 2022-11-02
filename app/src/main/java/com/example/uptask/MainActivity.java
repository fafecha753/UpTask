package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
<<<<<<< Updated upstream
    Button btnRegistrarse;
    Button btnIniciarSesion;
=======
    Button btnRegistrarse, btnPerfil;
    Button btnIniciarSesion, btnEditarPerfil;
    //declaracion de la variable que almacena el usuario  de firebase
    private FirebaseAuth mAuth;

    //cuando se visualiza la pantalla verifica si hay un usuario logueado
    @Override
    public void onStart() {
        super.onStart();
        //toma el usuario que haya logueado, devolviendo null si no hay uno
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //si existe un usuario logueado entonces lo redirige a la pantalla home
            home();
        }
    }// fin del onstart
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
<<<<<<< Updated upstream
=======
        btnEditarPerfil= findViewById(R.id.btnEditarPerfil);
        btnPerfil= findViewById(R.id.btnPerfil);
        mAuth= FirebaseAuth.getInstance();
>>>>>>> Stashed changes

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarIniciarSesion(view);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Abrir pagina Registro", Toast.LENGTH_SHORT).show();
            }
        });
<<<<<<< Updated upstream
    }
=======

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent perfil= new Intent(MainActivity.this, activity_perfil.class);
                startActivity(perfil);
            }
        });

    }//fin del oncreate


    //Metodo de llamada a la vista de inicio de sesión
>>>>>>> Stashed changes
    public void cambiarIniciarSesion(View view){
        Intent cambiarInicioSesion = new Intent(this, activity_registro.class);
        startActivity(cambiarInicioSesion);
    }
}