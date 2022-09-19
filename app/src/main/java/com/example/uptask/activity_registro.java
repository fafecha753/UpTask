package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptask.Modelo.Registro;
import com.example.uptask.Modelo.Usuario;

import org.w3c.dom.Text;

public class activity_registro extends AppCompatActivity {
    //Variables
    private Button btnInicioSesion;
    private Button btnRegresar;
    private EditText txtUsuario;
    private EditText txtPassword;
    private TextView tvOlvidoContra;
    private TextView tvUsuario;
    private TextView tvPassword;

    private Usuario usuario;
    private Registro registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnInicioSesion = (Button) findViewById(R.id.btnInicioSesion);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvOlvidoContra = (TextView) findViewById(R.id.tvOlvidoContra);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().toString().isEmpty()
                || txtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor complete los campos solicitados", Toast.LENGTH_SHORT).show();
                }else{
                    if(registro.getUsuario(txtUsuario.getText().toString().trim()) != null){
                        inicioSesion(view);
                    }else{
                        Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvOlvidoContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pagina de recuperacion de contraseña", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }

    public void inicioSesion(View view){
        Intent inicioSesion = new Intent(this, activity_sesionIniciada.class);
        startActivity(inicioSesion);
    }
}