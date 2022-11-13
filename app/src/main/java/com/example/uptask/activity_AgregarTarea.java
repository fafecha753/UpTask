package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_AgregarTarea extends AppCompatActivity {

    // Declaracion de variables
    private TextView txtNombreT, txtDescripcionT, txtFechaT, txtHoraT;
    private ImageButton   btnCatUno, btnCatDos, btnCatTres, btnCatCuatro;
    private Button btnAgregarTarea, btnCancelar;
    private Switch swDiario;
    private boolean diario= false;
    private int alarmID;

    String catSelec= "";
    //declaracion de la variable que almacena el usuario  de firebase y la base de datos
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);


        //Referenciar elementos layout
        txtFechaT = findViewById(R.id.txtFecha);
        txtHoraT = findViewById(R.id.txtHora);
        txtDescripcionT = findViewById(R.id.txtDescripcionTarea);
        txtNombreT = findViewById(R.id.txtNombreTarea);
        swDiario = findViewById(R.id.swDiaria);

        btnAgregarTarea= findViewById(R.id.btnAgregarTarea);
        btnCancelar= findViewById(R.id.btnCancelarTarea);
        btnCatUno= findViewById(R.id.btnCatUno);
        btnCatDos= findViewById(R.id.btnCatDos);
        btnCatTres= findViewById(R.id.btnCatTres);
        btnCatCuatro= findViewById(R.id.btnCatCuatro);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();


        //Seleccionar fecha a traves de un date picker
        txtFechaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(activity_AgregarTarea.this, listenerDeDatePicker, anio, mes, diaDelMes);
                dialogoFecha.show();
            }
        });

        txtHoraT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();;

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_AgregarTarea.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtHoraT.setText(hourOfDay + ":" + minute);
                            }
                        }, 12, 00, false);
                timePickerDialog.show();
            }
        });

        swDiario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diario=true;
                }else{
                    diario=false;
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        //Cambia los atributos de los botones de selección de avatar, según sean presionados
        btnCatUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat1";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat3";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat2";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat4";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            }
        });

        //Obtiene los contenidos de los elemenos del layout y verifica que todos se hayan completado
        //si es así, llama el metodo de agregar tarea
        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtDescripcionT.getText().toString().isEmpty()
                        || txtFechaT.getText().toString().isEmpty()
                        || txtNombreT.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Por favor complete los campos solicitados",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if(catSelec.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Por favor seleccione un avatar",
                                Toast.LENGTH_SHORT).show();
                    }else {


                        agregarTarea(v);

                        volver();
                    }
                }
            }
        });
    }//fin on create


    //metodo que se encarga de crear el modulo a traes del cual se selecciona la fecha
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int ano, int mess, int dia) {
            anio=ano;
            mes=mess+1;
            diaDelMes=dia;
            refrescarFechaEnEditText();
        }
    };
    //cambia el contenido del campo de fecha
    public void refrescarFechaEnEditText() {
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", anio, mes, diaDelMes);
        txtFechaT.setText(fecha);
    }
    //metodo de retorno a la pantalla anterior
    public void volver(){
        Intent main = new Intent(this, activity_sesionIniciada.class);
        startActivity(main);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        volver();
    }
    //Agrega los datos a los campos correspondientes en la base de datos firestore
    //
    public void agregarTarea(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        String userui = user.getUid();

        Map<String, Object> tarea = new HashMap<>();
        tarea.put("usuario", userui);
        tarea.put("nombre", txtNombreT.getText().toString());
        tarea.put("descripción", txtDescripcionT.getText().toString());
        tarea.put("fecha", txtFechaT.getText().toString());
        tarea.put("hora", txtHoraT.getText().toString());
        tarea.put("diaria", diario);
        tarea.put("categoria", catSelec);

        DocumentReference documentReferenceU = db.collection("Tareas")
                .document();
        documentReferenceU.set(tarea).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Tarea agregada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void setAlarm ( int i, Long timestamp, Context ctx){

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent= new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent= PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom//"+System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }









}