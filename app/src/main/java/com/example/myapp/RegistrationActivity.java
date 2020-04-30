package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
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

public class RegistrationActivity extends AppCompatActivity {

    private EditText userN, userPasswo, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        mAuth = FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String User_email = userEmail.getText().toString().trim();
                    String User_password = userPasswo.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword(User_email, User_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailverification();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews() {
        userN = (EditText) findViewById(R.id.etUserNm);
        userPasswo = (EditText) findViewById(R.id.etUserPassw);
        userEmail = (EditText) findViewById(R.id.etuserEmail);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserlogin);
    }

    private Boolean validate() {
        Boolean result = false;
        String name = userN.getText().toString();
        String password = userPasswo.getText().toString();
        String email = userEmail.getText().toString();
        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please Enter All details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }


    private void sendEmailverification() {
     FirebaseAuth mUser=FirebaseAuth.getInstance();
        if (mUser != null) {
            mUser.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(RegistrationActivity.this,"user Register succcessfully pleaase check your mail",Toast.LENGTH_SHORT).show();
                     finish();
                     FirebaseAuth.getInstance().signOut();
                     startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                   }
                 else{
                     Toast.makeText(RegistrationActivity.this,"Verification Email hasn't send!",Toast.LENGTH_SHORT).show();
                 }
                }
            });

        }

    }
}
