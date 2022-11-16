package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.uptask.Modelo.Tarea;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class activity_editarTarea extends AppCompatActivity {

    Button btnRegresarPrincipal, btnGuardarTarea;
    EditText txtEditNombreTarea,txtEditDescripcionTarea,txtEditFecha, txtEditHora;
    private ImageButton btnCatUno, btnCatDos, btnCatTres, btnCatCuatro;
    private Switch swEditDiaria;
    private boolean diario= false;

    String idTarea;
    String catSelec= "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        //Referencia de elementos Layout
        txtEditNombreTarea=findViewById(R.id.txtEditNombreTarea);
        txtEditDescripcionTarea=findViewById(R.id.txtEditDescripcionTarea);
        txtEditFecha=findViewById(R.id.txtEditFecha);
        txtEditHora=findViewById(R.id.txtEditHora);
        swEditDiaria=findViewById(R.id.swEditDiaria);

        //Extraiga el id
        Bundle extras = getIntent().getExtras();
        idTarea= extras.getString("id");

        btnRegresarPrincipal= (Button) findViewById(R.id.btnRegresarPrincipal);
        btnGuardarTarea=(Button) findViewById(R.id.btnGuardarTarea);
        btnCatUno= findViewById(R.id.btnCatUno);
        btnCatDos= findViewById(R.id.btnCatDos);
        btnCatTres= findViewById(R.id.btnCatTres);
        btnCatCuatro= findViewById(R.id.btnCatCuatro);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        mostrarDatosTarea();

        //Seleccionar fecha a traves de un date picker
        txtEditFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(activity_editarTarea.this, listenerDeDatePicker, anio, mes, diaDelMes);
                dialogoFecha.show();
            }
        });

        txtEditHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();;

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_editarTarea.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtEditHora.setText(hourOfDay + ":" + minute);
                            }
                        }, 12, 00, false);
                timePickerDialog.show();
            }
        });

        swEditDiaria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diario=true;
                }else{
                    diario=false;
                }
            }
        });

        btnCatUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat1";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat2";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat3";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat4";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            }
        });

        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarTarea(view);
                volver();
            }
        });

        btnRegresarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresar = new Intent(activity_editarTarea.this, activity_sesionIniciada.class);
                startActivity(regresar);
                finish();
            }
        });
    }//Fin onCreate

    public void mostrarDatosTarea(){
        DocumentReference documentReference= db.collection("Tareas").document(idTarea);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre= documentSnapshot.get("nombre").toString();
                String descripcion= documentSnapshot.get("descripción").toString();
                String fecha=documentSnapshot.get("fecha").toString();
                String categoria=documentSnapshot.get("categoria").toString();
                String horaLimite=documentSnapshot.get("hora").toString();
                diario=documentSnapshot.getBoolean("diaria");
                txtEditNombreTarea.setText(nombre);
                txtEditDescripcionTarea.setText(descripcion);
                txtEditFecha.setText(fecha);
                txtEditHora.setText(horaLimite);
                swEditDiaria.setChecked(diario);
                cambiarCatego(categoria);
            }
        });
    }//Fin metodo mostrarDatosTarea

    public void editarTarea(View view){
        DocumentReference documentReference= db.collection("Tareas").document(idTarea);
        db.collection("Tareas").document(idTarea).update(
                "nombre", txtEditNombreTarea.getText().toString().trim(),
                "descripción", txtEditDescripcionTarea.getText().toString().trim(),
                "fecha", txtEditFecha.getText().toString().trim(),
                "hora", txtEditHora.getText().toString().trim(),
                "categoria",catSelec,
                "diaria", diario
        );
    }//Fin del metodo

    public void volver(){
        Intent main = new Intent(this, activity_sesionIniciada.class);
        startActivity(main);
        finish();
    }

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
        txtEditFecha.setText(fecha);
    }

    //Cambiar categoria
    public void cambiarCatego(String variable){
        if (variable.equals("cat1")){
            catSelec="cat1";
            btnCatUno.setEnabled(false);
            btnCatDos.setEnabled(true);
            btnCatTres.setEnabled(true);
            btnCatCuatro.setEnabled(true);

            btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));

        }else if(variable.equals("cat2")){
            catSelec="cat2";
            btnCatUno.setEnabled(true);
            btnCatDos.setEnabled(false);
            btnCatTres.setEnabled(true);
            btnCatCuatro.setEnabled(true);

            btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("cat3")){
            catSelec="cat3";
            btnCatUno.setEnabled(true);
            btnCatDos.setEnabled(true);
            btnCatTres.setEnabled(false);
            btnCatCuatro.setEnabled(true);

            btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("cat4")){
            catSelec="cat4";
            btnCatUno.setEnabled(true);
            btnCatDos.setEnabled(true);
            btnCatTres.setEnabled(true);
            btnCatCuatro.setEnabled(false);

            btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
        }
    }//Fin metodo

}//Fin clase