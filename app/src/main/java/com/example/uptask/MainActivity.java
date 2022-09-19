package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnRegistrarse;
    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);

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
    }
    public void cambiarIniciarSesion(View view){
        Intent cambiarInicioSesion = new Intent(this, activity_registro.class);
        startActivity(cambiarInicioSesion);
    }
}