package com.example.uptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class activity_editarPerfil extends AppCompatActivity {

    Button btnGuardar, btnCancelar;
    EditText txtEditUsuario;
    TextView tvContra;
    ImageButton imgBA, imgBB,imgBC,imgBD;
    String _USERNAME, _IMG;
    private FirebaseAuth mAuth;

    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    String avatarSelec= "", usernameTemp, passwordTemp;

    FirebaseFirestore reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        reference = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        btnGuardar=findViewById(R.id.btnGuardar);
        btnCancelar=findViewById(R.id.btnCancelar);

        txtEditUsuario=findViewById(R.id.txtEditUsuario);

        tvContra = findViewById(R.id.tvContra);

        imgBA=findViewById(R.id.imgBA);
        imgBB=findViewById(R.id.imgBB);
        imgBC=findViewById(R.id.imgBC);
        imgBD=findViewById(R.id.imgBD);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentCambiarContra(view);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent salirEditar= new Intent(activity_editarPerfil.this, MainActivity.class);
                startActivity(salirEditar);
                finish();
            }
        });

        //Boton de avatar, no tuve tiempo de hacer lo de los bordes ya marcados
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

        /*---------------------------PLAN B------------------------------
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String _USERNAME = snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ----------------------------------------------------------------*/


        mostrarDatos();


    }//Fin onCreate


    //Agarra los datos desde el intent
    public void mostrarDatos(){
        String userui = mAuth.getUid();
        DocumentReference docRef = reference.collection("Users").document(userui);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre= documentSnapshot.get("usuario").toString();
                String img = documentSnapshot.get("avatar").toString();
                txtEditUsuario.setText(nombre);
                _USERNAME = nombre;
                _IMG = img;

                cambiarImg(img);
            }
        });

    }

    public void actualizar(View view){
        if(isNameChanged() /* || isPasswordChanged() */){
            Toast.makeText(this, "Los datos se han actualizado", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Los datos son los mismos, no se puede actualizar", Toast.LENGTH_SHORT).show();
    }

    private boolean isNameChanged() {
        if(!_USERNAME.equals(txtEditUsuario.getText().toString().trim())){
            //Actualiza
            String userui = mAuth.getUid();
            DocumentReference docRef = reference.collection("Users").document(userui);

            reference.collection("Users").document(userui).update(
                    "usuario", txtEditUsuario.getText().toString().trim(),
                    "avatar", _IMG
            );
            return true;
        }else{
            return false;
        }
    }

    /*
    private boolean isPasswordChanged() {
        if(!passwordTemp.equals(txtEditContraseña.getText().toString().trim())){
            //Ocupo el nombre de la columna de la contraseña
            reference.child(usernameTemp).child("").setValue(txtEditContraseña.getText().toString().trim());
            passwordTemp = txtEditContraseña.getText().toString().trim();
            return true;
        }else{
            return false;
        }
    }
    */

    public void IntentCambiarContra(View view){
        Intent inicioSesion = new Intent(this, activity_olvidoContrasena.class);
        startActivity(inicioSesion);
        finish();
    }

    public void cambiarImg(String variable){
        if(variable.equals("imgBA")){
            avatarSelec="imgBA";
            imgBA.setEnabled(false);
            imgBB.setEnabled(true);
            imgBC.setEnabled(true);
            imgBD.setEnabled(true);

            imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("imgBB")){
            avatarSelec="imgBB";
            imgBA.setEnabled(true);
            imgBB.setEnabled(false);
            imgBC.setEnabled(true);
            imgBD.setEnabled(true);

            imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("imgBC")) {
            avatarSelec="imgBC";
            imgBA.setEnabled(true);
            imgBB.setEnabled(true);
            imgBC.setEnabled(false);
            imgBD.setEnabled(true);

            imgBA.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBB.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
            imgBC.setBackgroundTintList(getResources().getColorStateList(R.color.colorDos));
            imgBD.setBackgroundTintList(getResources().getColorStateList(R.color.colorTres));
        }else if(variable.equals("imgBD")) {
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
    }
    

}//Final clase