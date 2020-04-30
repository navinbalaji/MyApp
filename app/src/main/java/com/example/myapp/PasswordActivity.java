package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.DataOutputStream;

public class PasswordActivity extends AppCompatActivity {



    private EditText passwordReset;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passwordReset=(EditText)findViewById(R.id.etPasswordEmail);
        resetpassword=(Button)findViewById(R.id.btnPasswordReset);
        firebaseAuth=FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=passwordReset.getText().toString().trim();
                if(useremail.equals((""))){
                    Toast.makeText(PasswordActivity.this, "Please Enter the Registered Email", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this,"Password reset email has sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(PasswordActivity.this,"Error in Sending Email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
