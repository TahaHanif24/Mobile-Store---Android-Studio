package com.example.javaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    TextView subtotal, discount, shipping, total;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Retrieve total amount from intent
        double productPrice = getIntent().getDoubleExtra("amount", 0.0); // Change "totalAmount" to "amount"

        // Initialize TextViews
        subtotal = findViewById(R.id.sub_total);

        total = findViewById(R.id.total_amt);

        // Set subtotal amount to the TextView
        subtotal.setText(String.valueOf(productPrice)); // Set the product price as the subtotal

        // Set discount and shipping to 0 for now (you can customize this part)
        double discountAmount = 0.0;
        double shippingAmount = 0.0;

        // Calculate total amount
        double finalTotalAmount = productPrice + discountAmount + shippingAmount;

        // Set total amount to the TextView
        total.setText(String.valueOf(finalTotalAmount));

        btn = findViewById(R.id.pay_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, Paymentdone.class);
                startActivity(intent);
            }
        });
    }

}
