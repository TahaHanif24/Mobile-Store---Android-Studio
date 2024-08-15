package com.example.javaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    Button addaddress;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ArrayList<AddressModel> addressModel;
    AddressAdapter addressAdapter;
    String maddress;
    Button addaddressbtn, paymentbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address); // Move this line before findViewById

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Object obj=getIntent().getSerializableExtra("item");

        recyclerView = findViewById(R.id.address_recycler);
        paymentbtn = findViewById(R.id.payment_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use `this` instead of `getApplicationContext()`

        addaddress = findViewById(R.id.add_address_btn);
        addressModel = new ArrayList<>();

        addressAdapter = new AddressAdapter(this, addressModel, this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddressModel category = document.toObject(AddressModel.class);
                                addressModel.add(category);
                            }
                            addressAdapter.notifyDataSetChanged();
                        } else {
                            // Handle the error
                        }
                    }
                });

        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount =0.0;
            if(obj instanceof newProducts){
                newProducts productss=(newProducts) obj;
                amount =productss.getPrice();
                }
            Intent intent=new Intent(AddressActivity.this,PaymentActivity.class);
            intent.putExtra("amount",amount);
            startActivity(intent);
            }
        });

        addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
        this.maddress = address;
    }
}
