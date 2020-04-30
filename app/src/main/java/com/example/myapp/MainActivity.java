package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Pass;
    private TextView Info;
    private Button login;
    private int counter = 5;
    private TextView userregistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.etUsername);
        Pass = (EditText) findViewById(R.id.etPass);
        Info = (TextView) findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);
        userregistration = (TextView) findViewById(R.id.tvlogin);
        forgotpass = (TextView) findViewById(R.id.tvForgotPassword);

        Info.setText("No of attempts remaining: 5");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check()) {
                    validate(Name.getText().toString(), Pass.getText().toString());
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        userregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }

    private Boolean check() {
        Boolean result = false;
        String name = Name.getText().toString();
        String password = Pass.getText().toString();
        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Enter All details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    private void validate(String userName, String userPassword) {
        progressDialog.setMessage("Please wait!...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser firebaseuser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseuser.isEmailVerified();
        if (emailflag) {
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        } else {
            Toast.makeText(this, "Please Verifiy your E-mail", Toast.LENGTH_SHORT).show();
        }
    }
}
