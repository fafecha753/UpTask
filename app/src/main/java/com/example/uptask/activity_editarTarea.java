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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uptask.Modelo.Tarea;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_editarTarea extends AppCompatActivity {

    Button btnRegresarPrincipal, btnGuardarTarea;
    EditText txtEditNombreTarea,txtEditDescripcionTarea,txtEditFecha, txtEditHora;
    ProgressBar Cargando;
    private ImageButton btnEditCatUno, btnEditCatDos, btnEditCatTres, btnEditCatCuatro;
    private Switch swEditDiaria;
    ScrollView pantallaT;
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
        btnEditCatUno= findViewById(R.id.btnEditCatUno);
        btnEditCatDos= findViewById(R.id.btnEditCatDos);
        btnEditCatTres= findViewById(R.id.btnEditCatTres);
        btnEditCatCuatro= findViewById(R.id.btnEditCatCuatro);



        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        pantallaT = findViewById(R.id.scrollView4);
        pantallaT.setVisibility(View.INVISIBLE);
        Cargando = findViewById(R.id.Cargando);
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
                        }, 00, 00, true);
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

        btnEditCatUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat1";
                btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnEditCatDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat3";
                btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnEditCatTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat2";
                btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        btnEditCatCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSelec="cat4";
                btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            }
        });

        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtEditDescripcionTarea.getText().toString().isEmpty()
                        || txtEditFecha.getText().toString().isEmpty()
                        || txtEditNombreTarea.getText().toString().isEmpty()
                        || txtEditHora.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Por favor complete los campos solicitados",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(catSelec.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Por favor seleccione una categoría",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Tarea t= new Tarea();
                        t.setHora(txtEditHora.getText().toString());
                        t.setFecha_limite(txtEditFecha.getText().toString());
                        try {
                            if(compararFechaLimite(getFecha(t))){
                                Toast.makeText(getApplicationContext(),
                                        "No se aceptan valores anteriores a la hora y fecha actual",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                editarTarea(view);
                                volver();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }//Fin onClick
        }); //Fin btnGuardarTarea

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

                Cargando.setVisibility(View.GONE);
                pantallaT.setVisibility(View.VISIBLE);
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
            btnEditCatUno.setEnabled(false);
            btnEditCatDos.setEnabled(true);
            btnEditCatTres.setEnabled(true);
            btnEditCatCuatro.setEnabled(true);

            btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));

        }else if(variable.equals("cat3")){
            catSelec="cat3";
            btnEditCatUno.setEnabled(true);
            btnEditCatDos.setEnabled(false);
            btnEditCatTres.setEnabled(true);
            btnEditCatCuatro.setEnabled(true);

            btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("cat2")){
            catSelec="cat2";
            btnEditCatUno.setEnabled(true);
            btnEditCatDos.setEnabled(true);
            btnEditCatTres.setEnabled(false);
            btnEditCatCuatro.setEnabled(true);

            btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("cat4")){
            catSelec="cat4";
            btnEditCatUno.setEnabled(true);
            btnEditCatDos.setEnabled(true);
            btnEditCatTres.setEnabled(true);
            btnEditCatCuatro.setEnabled(false);

            btnEditCatUno.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatDos.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatTres.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            btnEditCatCuatro.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
        }
    }//Fin metodo

    // compara la echa actual con la fecha que entra por parametro,
    // si la fecha actual es más antigua que la que entra por
    // parametro devuelve false
    private boolean compararFechaLimite(Calendar t) throws ParseException {
        Date fActual= Calendar.getInstance().getTime();
        Date  fTarea= t.getTime();
        if(fActual.compareTo(fTarea)>=0){
            return true;
        }else{
            return false;
        }
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

}//Fin clase