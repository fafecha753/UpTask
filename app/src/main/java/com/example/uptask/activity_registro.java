package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uptask.Modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class activity_registro extends AppCompatActivity {
    // Declaracion de variables
    EditText txtUsuario;
    EditText txtCorreoElectronico;
    EditText txtPassword;
    ImageButton imgBA;
    ImageButton imgBB;
    ImageButton imgBC;
    ImageButton imgBD;
    Button btnRegresarRegistro;
    Button btnRegistro;

    //Se hace el patrón a seguir para la contraseña
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?_/\\-!@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    //Se hace el patrón a seguir para el nombre de usuario, que se acepten mayúsculas, minúsculas y
    private static final String USERNAME_REGEX =
            "^[A-Za-z0-9]{6,13}$";


    private static final Pattern USERNAME_PATTERN =
            Pattern.compile(USERNAME_REGEX);

    String avatarSelec= "";

    //declaracion de la variable que almacena el usuario  de firebase y la base de datos
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Referenciar elementos layout
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtCorreoElectronico = (EditText) findViewById(R.id.txtCorreoElectronico);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        imgBA = (ImageButton) findViewById(R.id.imgBA);
        imgBB = (ImageButton) findViewById(R.id.imgBB);
        imgBC = (ImageButton) findViewById(R.id.imgBC);
        imgBD = (ImageButton) findViewById(R.id.imgBD);
        btnRegresarRegistro = (Button) findViewById(R.id.btnRegresarRegistro);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        //toma el usuario que haya logueado, devolviendo null si no hay uno
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        //Obtiene los contenidos de los elemenos del layout y verifica que todos se hayan completado
        //si es así, llama el metodo de registrar
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsuario.getText().toString().isEmpty()
                        || txtPassword.getText().toString().isEmpty()
                        || txtCorreoElectronico.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Por favor complete los campos solicitados",
                            Toast.LENGTH_SHORT).show();
                }else{

                    String username = txtUsuario.getText().toString().trim();
                    if (!USERNAME_PATTERN.matcher(username).matches()) {
                        Toast.makeText(getApplicationContext(), "Formato de usuario invalido. ", Toast.LENGTH_SHORT).show();
                        txtUsuario.setError("Solo se permiten mayúsculas, minúsculas y/o números. Sin espacios. Mínimo 6 caracteres, máximo 13.");
                        return;
                    } else {

                        if(avatarSelec.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Por favor seleccione un avatar",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        String correo = txtCorreoElectronico.getText().toString().trim();
                        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                            Toast.makeText(getApplicationContext(), "Formato de correo invalido", Toast.LENGTH_SHORT).show();
                            txtCorreoElectronico.setError("Formato de correo invalido");
                            return;
                        }

                        String contrasena = txtPassword.getText().toString().trim();
                        if (!PASSWORD_PATTERN.matcher(contrasena).matches()) {
                            Toast.makeText(getApplicationContext(), "Formato de contraseña invalido.", Toast.LENGTH_SHORT).show();
                            txtPassword.setError("Debe incluir al menos una mayúscula, minúscula, un número y un signo. Mínimo 8 caracteres, máximo 18.");
                            return;
                        }

                        agregarUsuario(v);

                    }
                    }
                }
            }
        });

        btnRegresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar(v);
            }
        });

        //Cambia los atributos de los botones de selección de avatar, según sean presionados

        imgBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarSelec="imgBA";
                imgBA.setEnabled(false);
                imgBB.setEnabled(true);
                imgBC.setEnabled(true);
                imgBD.setEnabled(true);

                imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        imgBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarSelec="imgBB";
                imgBA.setEnabled(true);
                imgBB.setEnabled(false);
                imgBC.setEnabled(true);
                imgBD.setEnabled(true);

                imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        imgBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarSelec="imgBC";
                imgBA.setEnabled(true);
                imgBB.setEnabled(true);
                imgBC.setEnabled(false);
                imgBD.setEnabled(true);

                imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
                imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            }
        });
        imgBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarSelec="imgBD";
                imgBA.setEnabled(true);
                imgBB.setEnabled(true);
                imgBC.setEnabled(true);
                imgBD.setEnabled(false);

                imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
                imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            }
        });





    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void regresar(View view){
        finish();
    }

    public void agregarUsuario(View view){
        //metodo para Registrar un nuevo usuari
        mAuth.createUserWithEmailAndPassword(txtCorreoElectronico.getText().toString().trim(),
                        txtPassword.getText().toString().trim())
        .addOnCompleteListener(activity_registro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d("TAG", "createUserWithEmail:success");
                    crearDatosUsuario();
                    inicioSesion(view);
                } else {

                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                }
            }
        });
    }
    //Cuando se registra un nuevo usuario no solo se crea la autentificación del registro de usuario
    //sino que se crean colecciones en la base de datos donde se guarden los logros, perfil y tareas del usuario
    public void crearDatosUsuario(){
        FirebaseUser user = mAuth.getCurrentUser();
        String userui=user.getUid();

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("usuario", txtUsuario.getText().toString());
        usuario.put("correo", txtCorreoElectronico.getText().toString());
        usuario.put("avatar", avatarSelec);
        usuario.put("exp", 0);

        DocumentReference documentReferenceU= db.collection("Users")
                .document(userui);
        documentReferenceU.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Contenido agregado",
                        Toast.LENGTH_SHORT).show();

            }
        });

        Map<String, Object> logros = new HashMap<>();
        logros.put("fiestero", false);
        logros.put("cerebrito", false);
        logros.put("obrero", false);
        logros.put("saludable", false);

        DocumentReference documentReferenceCT= db.collection("Logros")
                .document(userui);
        documentReferenceCT.set(logros).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Contenido agregado",
                        Toast.LENGTH_SHORT).show();

            }
        });

        Map<String, Object> contadorTareas = new HashMap<>();
        contadorTareas.put("academico", 0);
        contadorTareas.put("laboral", 0);
        contadorTareas.put("salud", 0);
        contadorTareas.put("social", 0);
        contadorTareas.put("total", 0);

        DocumentReference documentReferenceL= db.collection("Contador_tareas")
                .document(userui);
        documentReferenceL.set(contadorTareas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Contenido agregado",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void main(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    public void inicioSesion(View view){
        Intent inicioSesion = new Intent(this, activity_sesionIniciada.class);
        startActivity(inicioSesion);
        finish();
    }




}