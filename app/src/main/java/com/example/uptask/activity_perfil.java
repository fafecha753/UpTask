package com.example.uptask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class activity_perfil extends AppCompatActivity {
    ImageButton imgBEditarPerfil;
    Button btnRegresarHome, btnCerrarSes;


    /*private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    String avatarSelec= "";
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        imgBEditarPerfil= (ImageButton) findViewById(R.id.imgBEditarPerfil);

        btnRegresarHome= (Button) findViewById(R.id.btnRegresar);
        btnCerrarSes= (Button) findViewById(R.id.btnCerrarSesion);

        imgBEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editarPerfil = new Intent(activity_perfil.this, activity_editarPerfil.class);
                startActivity(editarPerfil);
                finish();
            }
        });

        btnRegresarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    regresar(v);
                }
        });

        btnCerrarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                main();
            }
        });

    }



    public void regresar(View view){
        finish();
    }

    public void main(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

}

