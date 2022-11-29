package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
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
    ProgressBar Cargando;
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
        btnAgregarTarea.setVisibility(View.INVISIBLE);
        llcontenedor.setVisibility(View.INVISIBLE);

        Cargando = findViewById(R.id.Cargando);

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
                finish();
            }
        });

        //Al precionar uno de los items de la lista de tareas
        lsTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarea tarea = lista.get(position);
                String idTarea =tarea.getId();
                try {
                    if (compararFechaLimite(getFecha(tarea))) {
                        menuExpirado(idTarea, position);
                    } else {
                        menuCompletar(idTarea, position, tarea.getCategoria());
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
                editarTarea.putExtra("id",idTarea);
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
                editarTarea.putExtra("id",idTarea);
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

    // compara la echa actual con la fecha que entra por parametro,
    // si la fecha actual es más antigua que la que entra por
    // parametro devuelve false
    private boolean compararFechaLimite(Calendar t) throws ParseException {

        Date  fActual= Calendar.getInstance().getTime();
        Date  fTarea= t.getTime();



        if(fActual.compareTo(fTarea)>=0){
            return true;
        }else{
            return false;
        }
    }


    // Carga en el ListView todas las tareas que pertenecen al
    // usuario logueado
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
                                t.setHora(document.get("hora").toString());
                                t.setDiaria((boolean)document.get("diaria"));
                                t.setAlarmID(Integer.parseInt(document.get("alarmID").toString()));
                                lista.add(t);

                                Calendar fecha= getFecha(t);

                                if(fecha.getTimeInMillis()>Calendar.getInstance().getTimeInMillis()) {

                                    setAlarm(t.getAlarmID(), fecha.getTimeInMillis(), activity_sesionIniciada.this, t);
                                }



                            }
                            adapterP= new Adapter(getApplicationContext(), lista) {};
                            lsTareas.setAdapter(adapterP);
                            llcontenedor.setVisibility(View.VISIBLE);
                            btnAgregarTarea.setVisibility(View.VISIBLE);

                        } else {
                            llcontenedor.setVisibility(View.VISIBLE);
                            btnAgregarTarea.setVisibility(View.VISIBLE);
                        }
                    }
                });




    }

    // retorna un objeto Calendar construido a partir de la
    // informacion del objeto tarea que entra por parametro
    private Calendar getFecha(Tarea t) {
        String[] dia= t.getFecha_limite().split("-");
        String[] hora= t.getHora().split(":");
        Calendar fecha= Calendar.getInstance();
        fecha.set(Calendar.YEAR, Integer.parseInt( dia[0]));
        fecha.set(Calendar.MONTH, Integer.parseInt( dia[1])-1);
        fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt( dia[2]));
        fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt( hora[0]));
        fecha.set(Calendar.MINUTE, Integer.parseInt( hora[1]));
        fecha.set(Calendar.SECOND, 0);

        return fecha;
    }

    // setea la informacion del perfil del usuario en los espacios
    // UI correspondientes

    //ANDRES FUI AL BAÑO POR SI ACASO
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

    // devuelve el valor que se le asigna a la barra de progreso
    public int experiencia(String nivel){
        int i;
        i=Integer.parseInt(nivel);
        i=i%10;
        i=i*10;
        return i;
    }

    // asigna la imagen correspondiente al avatar del erfil del
    // isuario, basandose en el ID de la imagen de la ase de datos
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

    // devuelve el nivel que tiene actualmente el usuario
    // dependiendo del numero de tareas realizado
    public String obtenerNivel(String exp){
        int r= (Integer.parseInt(exp)/10);
        String n= String.valueOf(r);
        return n;
    }

    // elimina de la base de datos la tarea correspondiente con
    // el ID que le entra por parametro
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

    //Actualiza los valores de los cotadores
    // correspondientes a las tareas en la base de datos
    public void completarTarea(String idTarea, String categoria){
        categoria= selectCategoria(categoria);
        String userUi = mAuth.getUid();

        DocumentReference contadorTareas= db.collection("Contador_tareas").document(userUi);
        contadorTareas.update(categoria, FieldValue.increment(1));


        DocumentReference usuarios= db.collection("Users").document(userUi);
        usuarios.update("exp", FieldValue.increment(1));


        iniciarInformación();
        eliminarTarea(idTarea, "Tarea completada");

    }

    // Devuelve el valor de la categoría correspondiente
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

    // Crea la alarma correspondiete a la tarea que se designa
    public static void setAlarm(int i, Long timestamp, Context ctx, Tarea t) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        alarmIntent.putExtra("titulo", t.getTitulo());
        alarmIntent.putExtra("alarmId", t.getAlarmID());
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp-300000, pendingIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}


























