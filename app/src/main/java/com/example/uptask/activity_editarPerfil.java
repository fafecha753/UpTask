package com.example.uptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class activity_editarPerfil extends AppCompatActivity {

    Button btnGuardar, btnCancelar;
    EditText txtEditUsuario, txtEditContraseña;
    ImageButton imgBA, imgBB,imgBC,imgBD;

    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    String avatarSelec= "", usernameTemp, passwordTemp;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //Como se llama la tabla?
        reference = FirebaseDatabase.getInstance().getReference("");

        btnGuardar=findViewById(R.id.btnGuardar);
        btnCancelar=findViewById(R.id.btnCancelar);

        txtEditUsuario=findViewById(R.id.txtEditUsuario);

        txtEditContraseña=findViewById(R.id.txtEditContraseña);

        imgBA=findViewById(R.id.imgBA);
        imgBB=findViewById(R.id.imgBB);
        imgBC=findViewById(R.id.imgBC);
        imgBD=findViewById(R.id.imgBD);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        mostrarDatos();


    }//Fin onCreate


    //Necesito que el perfil este listo para poder agarrar los datos desde el intent
    public void mostrarDatos(){
        Intent intent = getIntent();
        usernameTemp = intent.getStringExtra("username");
        passwordTemp = intent.getStringExtra("password");

        txtEditUsuario.setText(usernameTemp);
        txtEditContraseña.setText(passwordTemp);
    }

    public void actualizar(View view){
        if(isNameChanged() || isPasswordChanged()){
            Toast.makeText(this, "Los datos se han actualizado", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Los datos son los mismos, no se puede actualizar", Toast.LENGTH_SHORT).show();
    }

    private boolean isNameChanged() {
        if(!usernameTemp.equals(txtEditUsuario.getText().toString().trim())){
            //Ocupo el nombre de la columna del username
            reference.child(usernameTemp).child("").setValue(txtEditUsuario.getText().toString().trim());
            usernameTemp = txtEditUsuario.getText().toString().trim();
            return true;
        }else{
            return false;
        }
    }

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
    

}//Final clase