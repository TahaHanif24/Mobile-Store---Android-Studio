package com.example.javaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {
    EditText signupName, signupNumber, signupEmail, signupPassword;
    Button signupButton;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signuppage);
        auth=FirebaseAuth.getInstance();
        signupName = findViewById(R.id.NameSignup);
        signupEmail = findViewById(R.id.EmailSignup);
        signupNumber = findViewById(R.id.MobileNumberSignup);
        signupPassword = findViewById(R.id.passwordSignup);
        signupButton = findViewById(R.id.SignUPButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String phonenumber = signupNumber.getText().toString();
                String password = signupPassword.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;

                } else if (phonenumber.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Enter your PhoneNumber", Toast.LENGTH_SHORT).show();
                    return;

                } else if (password.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupPage.this,"Signup successful",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(SignupPage.this,LoginPage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignupPage.this,"Signup Failed"+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}
