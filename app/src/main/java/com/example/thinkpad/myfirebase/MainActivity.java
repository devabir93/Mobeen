package com.example.thinkpad.myfirebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText pass;
    private Button register;
    private TextView status;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        register = findViewById(R.id.register);
        status = findViewById(R.id.status);

        register.setOnClickListener(this);
        status.setOnClickListener(this);

    }

    private void registerUser(){
        String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();

        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(this,"Please enter email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pass_text)){
            Toast.makeText(this,"Please enter password!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email_text,pass_text)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }else{
//                            Toast.makeText(MainActivity.this,"Could not register, please try again!",Toast.LENGTH_SHORT).show();
//                            progressDialog.hide();

                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(MainActivity.this,"You are already registered!",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == register){
            registerUser();
        }

        if(view == register){
            // open login activity here
        }
    }
}
