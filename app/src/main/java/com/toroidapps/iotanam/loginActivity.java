package com.toroidapps.iotanam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    Button btnLogin,btnRegister;

    private FirebaseAuth user_mAuth;
    private FirebaseAuth.AuthStateListener user_mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        user_mAuth = FirebaseAuth.getInstance();
        user_mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth_user) {
                if (firebaseAuth_user.getCurrentUser() != null){
                    startActivity(new Intent(loginActivity.this, HomeActivity.class));
                    finish();
                }
            }
        };
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        user_mAuth.addAuthStateListener(user_mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(user_mAuthListener !=null){
            user_mAuth.removeAuthStateListener(user_mAuthListener);
        }
    }
    private void startSignIn(){
        String email = txtEmail.getText().toString();
        String pass = txtPassword.getText().toString();

        if(TextUtils.isEmpty(email) | TextUtils.isEmpty(pass)){
            Toast.makeText(loginActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else{
            user_mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(loginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
