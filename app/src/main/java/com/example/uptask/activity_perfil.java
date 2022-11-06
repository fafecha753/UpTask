package com.example.uptask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_perfil extends AppCompatActivity {
    ImageButton imgBEditarPerfil;
    Button btnRegresarHome, btnCerrarSes;
    ImageView imgPerfil;
    TextView tvNombreUsuario, tvNumNivel;
    String avatarSelec= "";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        imgBEditarPerfil= (ImageButton) findViewById(R.id.imgBEditarPerfil);
        btnRegresarHome= (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSes= (Button) findViewById(R.id.btnRegresar);
        imgPerfil= (ImageView) findViewById(R.id.imgPerfil);
        tvNumNivel = findViewById(R.id.tvNumNivel);
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        iniciarInformación();

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
        Intent i = new Intent(this, activity_sesionIniciada.class);
        startActivity(i);
        finish();
    }

    public void main(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    public void iniciarInformación(){
        String userui = mAuth.getUid();
        DocumentReference docRef = db.collection("Users").document(userui);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre= documentSnapshot.get("usuario").toString();
                String nivel = obtenerNivel(documentSnapshot.get("exp").toString());
                String img = documentSnapshot.get("avatar").toString();
                tvNombreUsuario.setText(nombre);
                tvNumNivel.setText(nivel);
                cambiarAvatar(img);
            }
        });
    }

    public void cambiarAvatar(String img){
        if(img.equalsIgnoreCase("imgBA")){
            imgPerfil.setImageResource(R.drawable.imgba);
        }
        if(img.equalsIgnoreCase("imgBB")){
            imgPerfil.setImageResource(R.drawable.imgbb);
        }
        if(img.equalsIgnoreCase("imgBC")){
            imgPerfil.setImageResource(R.drawable.imgbc);
        }
        if(img.equalsIgnoreCase("imgD")){
            imgPerfil.setImageResource(R.drawable.imgbd);
        }
    }
    public String obtenerNivel(String exp){
        int r= (Integer.parseInt(exp)/10);
        String n= String.valueOf(r);
        return n;
    }

}

