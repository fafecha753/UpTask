package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class activity_olvidoContrasena extends AppCompatActivity {
    private EditText txtUsuario;
    private Button btnRecCont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasena);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        btnRecCont = findViewById(R.id.btnRecuperarContrasena);

        btnRecCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
                    txtUsuario.setError("Formato de correo invalido");
                }else{
                    validarDatos();
                }
            }
        });

    }



    public void validarDatos(){
        String correo= txtUsuario.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(getApplicationContext(), "Formato de correo invalido",
                    Toast.LENGTH_SHORT).show();
            txtUsuario.setError("Formato de correo invalido");
            return;
        }
        recuperarContraseña();
    }

    public void recuperarContraseña(){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(txtUsuario.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Correo enviado",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "El correo no está registrado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void regresar(View view){
        finish();
    }
}