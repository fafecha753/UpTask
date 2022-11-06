package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptask.Modelo.Registro;
import com.example.uptask.Modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class activity_inicioSesion extends AppCompatActivity {
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

    //declaracion de la variable que almacena el usuario  de firebase
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Referenciar elementos layout
        btnInicioSesion = (Button) findViewById(R.id.btnInicioSesion);
        btnRegresar = (Button) findViewById(R.id.btnCerrarSesion);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvOlvidoContra = (TextView) findViewById(R.id.tvOlvidoContra);

        //toma el usuario que haya logueado, devolviendo null si no hay uno
        mAuth = FirebaseAuth.getInstance();


        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().toString().isEmpty()
                || txtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor complete los campos solicitados", Toast.LENGTH_SHORT).show();
                }else{
                    validarDatos(view);
                }
            }
        });

        tvOlvidoContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                olvContra(view);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar(view);
            }
        });

    }


    public void olvContra(View view){
        Intent olvidoContrasena = new Intent(this, activity_olvidoContrasena.class);
        startActivity(olvidoContrasena);
    }

    public void regresar(View view){
        finish();
    }

    public void inicioSesion(View view){
        Intent inicioSesion = new Intent(this, activity_sesionIniciada.class);
        startActivity(inicioSesion);
        finish();
    }

    //Metodo que valida el formato de correo
    public void validarDatos(View view){
        String correo= txtUsuario.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(getApplicationContext(), "Formato de correo invalido", Toast.LENGTH_SHORT).show();
            txtUsuario.setError("Formato de correo invalido");
            return;
        }
        iniciarSesion(view);
    }

    public void iniciarSesion(View view){
        //Metodo de firebase para relaizar el inicio de sesión.
        //Si el servidor detecta que el formato de la contraseña o del
        //correo no son los correctos, entonces no va a ejecutar nada
        //dentro del metodo y va a devolver un mensaje de consola.
        mAuth.signInWithEmailAndPassword(txtUsuario.getText().toString().trim(),
                        txtPassword.getText().toString().trim())
                .addOnCompleteListener(activity_inicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //el task que entra por parametro funciona como contenedor
                        //de la tarea que se está realizando. El metodo *isSucceful*
                        //devuelve un booleano cuando la tarea se realiza correctamente
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                            inicioSesion(view);
                        } else {
                            Toast.makeText(getApplicationContext(), "¡Usuario o contraseña invalidos!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}