package com.example.javaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaproject.R;

public class Paymentdone extends AppCompatActivity {

    EditText paymentNameEditText, cardNumberEditText, pinCodeEditText;
    Button payAmountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdone);

        paymentNameEditText = findViewById(R.id.ad_name);
        cardNumberEditText = findViewById(R.id.ad_address);
        pinCodeEditText = findViewById(R.id.ad_city);
        payAmountButton = findViewById(R.id.ad_add_address);

        payAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentName = paymentNameEditText.getText().toString().trim();
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String pinCode = pinCodeEditText.getText().toString().trim();

                if (paymentName.isEmpty() || cardNumber.isEmpty() || pinCode.isEmpty()) {
                    Toast.makeText(Paymentdone.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Transaction completed, navigate to home page
                    Toast.makeText(Paymentdone.this, "Transaction done!", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(Paymentdone.this, HomePage.class));
                }
            }
        });
    }
}
