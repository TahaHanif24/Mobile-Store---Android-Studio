package com.example.javaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_ONBOARDING_COMPLETE = "OnboardingComplete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        checkUserStatus();
    }

    private void checkUserStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean onboardingComplete = sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETE, false);

        Intent intent;
        if (currentUser != null) {
            if (onboardingComplete) {
                intent = new Intent(MainActivity.this, HomePage.class);
            } else {
                intent = new Intent(MainActivity.this, OnBoardingActivity.class);
            }
        } else {
            intent = new Intent(MainActivity.this, LoginPage.class);
        }
        startActivity(intent);
        finish();
    }
}
