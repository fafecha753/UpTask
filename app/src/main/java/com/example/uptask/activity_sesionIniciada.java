package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptask.Modelo.Adapter;

import com.example.uptask.Modelo.ListaTareas;
import com.example.uptask.Modelo.Tarea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class activity_sesionIniciada extends AppCompatActivity {

    FloatingActionButton btnAgregarTarea ;
    ImageButton btnPerfil;
    Adapter adapterP;
    ListView lsTareas;
    ListaTareas lT= new ListaTareas();
    ArrayList<Tarea> lista= new ArrayList<Tarea>();

    ImageView imgPerfil;
    TextView tvNombreUsuario, tvNumNivel;
    ProgressBar pExp;
    LinearLayout llcontenedor;
    String avatarSelec= "";


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();


        setContentView(R.layout.activity_sesion_iniciada);
        btnAgregarTarea= findViewById(R.id.btnAgTare);
        btnPerfil= findViewById(R.id.btnPerfil);
        lsTareas= findViewById(R.id.lsTareas);

        imgPerfil= (ImageView) findViewById(R.id.imgPerfil);
        tvNumNivel = findViewById(R.id.tvNumNivel);
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        pExp= findViewById(R.id.pbNivel);

        llcontenedor =findViewById(R.id.llContenedor);
        llcontenedor.setVisibility(View.INVISIBLE);

        iniciarInformación();
        cargarTareas();





        adapterP= new Adapter(getApplicationContext(), lista) {};
        lsTareas.setAdapter(adapterP);

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r= new Intent(activity_sesionIniciada.this, activity_AgregarTarea.class);
                startActivity(r);
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent perfil= new Intent(activity_sesionIniciada.this, activity_perfil.class);
                startActivity(perfil);
            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void cargarTareas(){

        String userui = mAuth.getUid();
        db.collection("Tareas")
                .whereEqualTo("usuario", userui)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarea t= new Tarea();
                                t.setId( document.getId());
                                t.setUsuario(document.get("usuario").toString());
                                t.setTitulo(document.get("nombre").toString());
                                t.setDescripcion(document.get("descripción").toString());
                                t.setCategoria(document.get("categoria").toString());
                                t.setFecha_limite(document.get("fecha").toString());
                                lista.add(t);
                                adapterP= new Adapter(getApplicationContext(), lista) {};
                                lsTareas.setAdapter(adapterP);
                                llcontenedor.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Nooooooo", Toast.LENGTH_SHORT).show();


                        }
                    }
                });



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
                pExp.setProgress(experiencia(nivel));
                cambiarAvatar(img);
            }
        });
    }
    public int experiencia(String nivel){
        int i;
        i=Integer.parseInt(nivel)%10;
        return i*10;
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
        if(img.equalsIgnoreCase("imgBD")){
            imgPerfil.setImageResource(R.drawable.imgbd);
        }
    }
    public String obtenerNivel(String exp){
        int r= (Integer.parseInt(exp)/10);
        String n= String.valueOf(r);
        return n;
    }


}