package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class activity_sesionIniciada extends AppCompatActivity {

    FloatingActionButton btnAgregarTarea ;
    ImageButton btnPerfil;
    Adapter adapterP;
    ListView lsTareas;
    ListaTareas lT= new ListaTareas();
    ArrayList<Tarea> lista;

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

        lsTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarea tarea = lista.get(position);
                String idTarea =tarea.getId();
                String fecha = tarea.getFecha_limite();
                String categoria = tarea.getCategoria();
                try {
                    if (compararFechaLimite(fecha)) {
                        menuExpirado(idTarea, position);
                    } else {
                        menuCompletar(idTarea, position, categoria);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });





    }
    // Mostrara un alert con las opciones de borrar la tarea
    // o editarla
    private void menuExpirado(String idTarea, int p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué acción desea realizar?")
                .setTitle("Tarea a expirado");

        builder.setPositiveButton("Editar Tarea", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent editarTarea = new Intent(activity_sesionIniciada.this, activity_editarTarea.class);
                //editarTarea.putExtra("id",idTarea);
                startActivity(editarTarea);
                finish();
                //Aquí se llamaria la ventana de editar y se enviaria el id de la tarea desde
                // el intent
            }
        });

        builder.setNegativeButton("Eliminar Tarea", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        eliminarTarea(idTarea, "Tarea eliminada");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Mostrara un alert con las opciones de borrar la tarea
    // o darla por completa
    private void menuCompletar(String idTarea, int p, String categoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué acción desea realizar?")
                .setTitle("Tarea vigente");

        builder.setNegativeButton("Eliminar Tarea", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarTarea(idTarea, "Tarea eliminada");
            }
        });

        builder.setNeutralButton("Editar Tarea", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent editarTarea = new Intent(activity_sesionIniciada.this, activity_editarTarea.class);
                //editarTarea.putExtra("id",idTarea);
                startActivity(editarTarea);
                finish();
                //Aquí se llamaria la ventana de editar y se enviaria el id de la tarea desde
                // el intent
            }
        });

        builder.setPositiveButton("Completar Tarea", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                completarTarea(idTarea,  categoria);
            }
        });



        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean compararFechaLimite(String fechaTarea) throws ParseException {
        SimpleDateFormat dateFormat = new
                SimpleDateFormat ("yyyy-MM-dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        Date fTarea = dateFormat.parse(fechaTarea);
        Date fActual = dateFormat.parse(date);

        if(fActual.compareTo(fTarea)>=0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void cargarTareas(){
        lista= new ArrayList<Tarea>();
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


                            }
                            adapterP= new Adapter(getApplicationContext(), lista) {};
                            lsTareas.setAdapter(adapterP);
                            llcontenedor.setVisibility(View.VISIBLE);

                        } else {
                            llcontenedor.setVisibility(View.VISIBLE);

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
                pExp.setProgress(experiencia(documentSnapshot.get("exp").toString()));
                cambiarAvatar(img);
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

    public void eliminarTarea(String idTarea, String mensaje){
        db.collection("Tareas").document(idTarea)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),
                                mensaje,
                                Toast.LENGTH_SHORT).show();
                        cargarTareas();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Error al eliminar tarea",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void completarTarea(String idTarea, String categoria){
        categoria= selectCategoria(categoria);
        String userUi = mAuth.getUid();

        DocumentReference contadorTareas= db.collection("Contador_tareas").document(userUi);
        contadorTareas.update(categoria, FieldValue.increment(1));
        eliminarTarea(idTarea, "Tarea completada");

        DocumentReference usuarios= db.collection("Users").document(userUi);
        usuarios.update("exp", FieldValue.increment(1));
        eliminarTarea(idTarea, "Tarea completada");

        iniciarInformación();

    }

    private String selectCategoria(String categoria) {
        if (categoria.equalsIgnoreCase("cat1")){
            return "academico";
        }
        if (categoria.equalsIgnoreCase("cat2")){
            return "laboral";
        }
        if (categoria.equalsIgnoreCase("cat3")){
            return "social";
        }
        if (categoria.equalsIgnoreCase("cat4")){
            return "salud";
        }
        return null;
    }


}