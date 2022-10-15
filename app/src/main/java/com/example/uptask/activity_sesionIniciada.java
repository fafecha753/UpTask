package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class activity_sesionIniciada extends AppCompatActivity {

    Button btnCerrarSesion, btnAgregarTarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_iniciada);
        btnCerrarSesion= findViewById(R.id.btnCerrarSesion);
        btnAgregarTarea= findViewById(R.id.btnAgTarea);


        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r= new Intent(activity_sesionIniciada.this, activity_AgregarTarea.class);
                startActivity(r);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                main();
            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void main(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
}