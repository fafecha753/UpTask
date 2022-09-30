package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class activity_registro extends AppCompatActivity {
    EditText txtUsuario;
    EditText txtCorreoElectronico;
    EditText txtPassword;
    ImageButton imgBA;
    ImageButton imgBB;
    ImageButton imgBC;
    ImageButton imgBD;
    Button btnRegresarRegistro;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtCorreoElectronico = (EditText) findViewById(R.id.txtCorreoElectronico);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        imgBA = (ImageButton) findViewById(R.id.imgBA);
        imgBB = (ImageButton) findViewById(R.id.imgBB);
        imgBC = (ImageButton) findViewById(R.id.imgBC);
        imgBD = (ImageButton) findViewById(R.id.imgBD);
        btnRegresarRegistro = (Button) findViewById(R.id.btnRegresarRegistro);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
    }

    public void regresar(View view){
        finish();
    }
}