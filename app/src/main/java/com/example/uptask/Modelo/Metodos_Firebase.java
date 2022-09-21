package com.example.uptask.Modelo;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.uptask.activity_inicioSesion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class Metodos_Firebase {


    //metodo para Registrar un nuevo usuario
    /*mAuth.createUserWithEmailAndPassword(txtUsuario.getText().toString().trim(),
                                                txtPassword.getText().toString().trim())
            .addOnCompleteListener(activity_inicioSesion .this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {

                Log.d("TAG", "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
                inicioSesion(view);
            } else {

                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                Toast.makeText(activity_inicioSesion.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        }
    });

    //Metodo inicio de sesión
    mAuth.signInWithEmailAndPassword(txtUsuario.getText().toString().trim(),
                                    txtPassword.getText().toString().trim())
            .addOnCompleteListener(activity_inicioSesion.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                inicioSesion(view);
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithEmail:failure", task.getException());
                Toast.makeText(activity_inicioSesion.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    });
*/


}
