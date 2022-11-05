package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


    private TextView txtNombreT, txtDescripcionT, txtFechaT;
    private ImageButton   btnCatUno, btnCatDos, btnCatTres, btnCatCuatro;
    private Button btnAgregarTarea, btnCancelar;

    String catSelec= "";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // Y luego ya podrás obtener la fecha
    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);
        txtFechaT = findViewById(R.id.txtFecha);
        txtDescripcionT = findViewById(R.id.txtDescripcionTarea);
        txtNombreT = findViewById(R.id.txtNombreTarea);

        btnAgregarTarea= findViewById(R.id.btnAgregarTarea);
        btnCancelar= findViewById(R.id.btnCancelarTarea);
        btnCatUno= findViewById(R.id.btnCatUno);
        btnCatDos= findViewById(R.id.btnCatDos);
        btnCatTres= findViewById(R.id.btnCatTres);
        btnCatCuatro= findViewById(R.id.btnCatCuatro);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        txtFechaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(activity_AgregarTarea.this, listenerDeDatePicker, anio, mes, diaDelMes);
                dialogoFecha.show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

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
                catSelec="cat2";
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnCatTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat3";
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
    }

    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int ano, int mess, int dia) {
            anio=ano;
            mes=mess+1;
            diaDelMes=dia;
            refrescarFechaEnEditText();
        }
    };
    public void refrescarFechaEnEditText() {
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", anio, mes, diaDelMes);
        txtFechaT.setText(fecha);
    }

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

    public void agregarTarea(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        String userui = user.getUid();

        Map<String, Object> tarea = new HashMap<>();
        tarea.put("usuario", userui);
        tarea.put("nombre", txtNombreT.getText().toString());
        tarea.put("descripción", txtDescripcionT.getText().toString());
        tarea.put("fecha", txtFechaT.getText().toString());
        tarea.put("categoria", catSelec);

        DocumentReference documentReferenceU = db.collection("Tareas")
                .document(txtDescripcionT.getText().toString());
        documentReferenceU.set(tarea).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                    }





        });
    }

}