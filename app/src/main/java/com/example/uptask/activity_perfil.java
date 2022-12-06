package com.example.uptask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

    ProgressBar pExp;
    ProgressBar Cargando;
    String avatarSelec= "";
    ScrollView pantallaP;
    LinearLayout LogroSalud1, LogroSocial1,LogroLaboral1,LogroEstudio1;

    int academico=0;
    int laboral=0;
    int salud=0;
    int social=0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        imgBEditarPerfil= (ImageButton) findViewById(R.id.btnPerfil);
        btnRegresarHome= (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSes= (Button) findViewById(R.id.btnRegresar);
        imgPerfil= (ImageView) findViewById(R.id.imgPerfil);
        tvNumNivel = findViewById(R.id.tvNumNivel);
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        pExp= findViewById(R.id.pbNivel);
        LogroSalud1=findViewById(R.id.LogroSalud1);
        LogroLaboral1=findViewById(R.id.LogroLaboral1);
        LogroSocial1=findViewById(R.id.LogroSocial1);
        LogroEstudio1=findViewById(R.id.LogroEstudio1);


        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        pantallaP = findViewById(R.id.scrollView2);
        pantallaP.setVisibility(View.INVISIBLE);
        LogroSalud1.setVisibility(View.GONE);
        LogroLaboral1.setVisibility(View.GONE);
        LogroSocial1.setVisibility(View.GONE);
        LogroEstudio1.setVisibility(View.GONE);

        Cargando = findViewById(R.id.Cargando);
        iniciarInformación();
        contarTareas();


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

    private void contarTareas() {
        String userui=mAuth.getUid();

        DocumentReference doctRefe=db.collection("Contador_tareas").document(userui);
        doctRefe.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                salud = Integer.parseInt(documentSnapshot.get("salud").toString());
                laboral = Integer.parseInt(documentSnapshot.get("laboral").toString());
                social = Integer.parseInt(documentSnapshot.get("social").toString());
                academico = Integer.parseInt(documentSnapshot.get("academico").toString());

                boolean saludb=false;
                boolean laboralb=false;
                boolean socialb=false;
                boolean academicob=false;

                if(salud>=100){
                    saludb=true;
                }if (laboral>=5){
                    laboralb=true;
                }if(social>=15){
                    socialb=true;
                }if(academico>=1){
                    academicob=true;
                }



                db.collection("Logros").document(userui).update(
                        "saludable", saludb,
                        "obrero", laboralb,
                        "fiestero", socialb,
                        "cerebrito", academicob
                );
                revisarLogros();
            }//Fin onSuccess


        });

    }//Fin metodo

    public void revisarLogros(){
        String userui=mAuth.getUid();
        DocumentReference doctRefe=db.collection("Logros").document(userui);
        doctRefe.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String saludable = documentSnapshot.get("saludable").toString();
                String obrero = documentSnapshot.get("obrero").toString();
                String fiestero = documentSnapshot.get("fiestero").toString();
                String cerebrito = documentSnapshot.get("cerebrito").toString();


                if(saludable.equalsIgnoreCase("true")){
                    LogroSalud1.setVisibility(View.VISIBLE);
                }if(obrero.equalsIgnoreCase("true")){
                    LogroLaboral1.setVisibility(View.VISIBLE);
                }if (fiestero.equalsIgnoreCase("true")){
                    LogroSocial1.setVisibility(View.VISIBLE);
                }if(cerebrito.equalsIgnoreCase("true")){
                    LogroEstudio1.setVisibility(View.VISIBLE);
                }
            }//Fin onSuccess
        });

    }//Fin metodo


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
                pExp.setProgress(experiencia(documentSnapshot.get("exp").toString()));
                cambiarAvatar(img);
                //Una vez realizado se desaparece
                Cargando.setVisibility(View.GONE);
                pantallaP.setVisibility(View.VISIBLE);
            }
        });

    }
    public int experiencia(String nivel){
        int i;
        i=Integer.parseInt(nivel);
        i=i%10;
        i=i*10;
        return i;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
}

