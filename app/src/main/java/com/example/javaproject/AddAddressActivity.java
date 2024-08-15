package com.example.javaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    EditText name, address, city, postalcode, phonenumber;
    Button addaddressbutton;
    FirebaseFirestore firebase;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        firebase = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize the views correctly
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalcode = findViewById(R.id.ad_code);
        phonenumber = findViewById(R.id.ad_phone);
        addaddressbutton = findViewById(R.id.ad_add_address);

        addaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String usercity = city.getText().toString();
                String useraddress = address.getText().toString();
                String usercode = postalcode.getText().toString();
                String usernumber = phonenumber.getText().toString();
                String final_address = "";

                if (!username.isEmpty()) {
                    final_address += username;
                }
                if (!useraddress.isEmpty()) {
                    final_address += useraddress;
                }
                if (!usercity.isEmpty()) {
                    final_address += usercity;
                }
                if (!usercode.isEmpty()) {
                    final_address += usercode;
                }
                if (!usernumber.isEmpty()) {
                    final_address += usernumber;
                }

                if (!username.isEmpty() && !useraddress.isEmpty() && !usercity.isEmpty() && !usercode.isEmpty() && !usernumber.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", final_address);

                    firebase.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddAddressActivity.this, "Address added successfully", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(AddAddressActivity.this, "Failed to add address", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(AddAddressActivity.this, "Kindly fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
