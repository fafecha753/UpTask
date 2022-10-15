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
    EditText txtUsuario;
    EditText txtCorreoElectronico;
    EditText txtPassword;
    ImageButton imgBA;
    ImageButton imgBB;
    ImageButton imgBC;
    ImageButton imgBD;
    Button btnRegresarRegistro;
    Button btnRegistro;
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    String avatarSelec= "";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtCorreoElectronico = (EditText) findViewById(R.id.txtCorreoElectronico);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        imgBA = (ImageButton) findViewById(R.id.imgBA);
        imgBB = (ImageButton) findViewById(R.id.imgBB);
        imgBC = (ImageButton) findViewById(R.id.imgBC);
        imgBD = (ImageButton) findViewById(R.id.imgBD);
        btnRegresarRegistro = (Button) findViewById(R.id.btnRegresarRegistro);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

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
                    if(avatarSelec.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Por favor seleccione un avatar",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        String correo = txtCorreoElectronico.getText().toString().trim();
                        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                            Toast.makeText(getApplicationContext(), "Formato de correo invalido", Toast.LENGTH_SHORT).show();
                            txtUsuario.setError("Formato de correo invalido");
                            return;
                        }

                        String contrasena = txtPassword.getText().toString().trim();
                        if (!PASSWORD_PATTERN.matcher(contrasena).matches()) {
                            Toast.makeText(getApplicationContext(), "Formato de contraseña invalido", Toast.LENGTH_SHORT).show();
                            txtUsuario.setError("Formato de contraseña invalido");
                            return;
                        }
                        agregarUsuario(v);

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

    public void crearDatosUsuario(){
        FirebaseUser user = mAuth.getCurrentUser();
        String userui=user.getUid();

        Map<String, Object> map = new HashMap<>();
        map.put("usuario", txtUsuario.getText().toString());
        map.put("correo", txtCorreoElectronico.getText().toString());
        map.put("avatar", avatarSelec);
        map.put("exp", 0);

        DocumentReference documentReference= db.collection("Users")
                .document(userui);
        documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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