package com.example.javaproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    int overallamount;
    TextView totalpriceofcart;
    FirebaseAuth auth;
    FirebaseFirestore firebasestore;
    MyCartAdapter myadapter;
    ArrayList<Mycart> mycart;
    RecyclerView recyclerView;
    Button buyNowButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(messagereceiver, new IntentFilter("MyTotalAmount"));

        totalpriceofcart = view.findViewById(R.id.totalproce);
        auth = FirebaseAuth.getInstance();
        firebasestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerview4);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mycart = new ArrayList<>();
        myadapter = new MyCartAdapter(getContext(), mycart);
        recyclerView.setAdapter(myadapter);

        firebasestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mycart.clear(); // Clear the list before adding items
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Mycart category = document.toObject(Mycart.class);
                                category.setDocumentId(document.getId()); // Set the document ID
                                mycart.add(category);
                            }
                            myadapter.notifyDataSetChanged();
                            updateTotalAmount(); // Update total amount after fetching data
                        }
                    }
                });

        buyNowButton = view.findViewById(R.id.buynowbuttonincart);
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalAmountStr = totalpriceofcart.getText().toString();
                totalAmountStr = totalAmountStr.replaceAll("[^\\d]", ""); // Extract only the digits
                if (!totalAmountStr.isEmpty()) {
                    double totalAmount = Double.parseDouble(totalAmountStr); // Parse as double
                    Intent intent = new Intent(getContext(), PaymentActivity.class);
                    intent.putExtra("totalAmount", totalAmount); // Pass total amount to PaymentActivity
                    startActivity(intent);
                }
            }
        });

    }

    private void updateTotalAmount() {
        int totalAmount = 0;
        for (Mycart cartItem : mycart) {
            totalAmount += cartItem.getTotalPrice();
        }
        totalpriceofcart.setText("Total Amount: " + totalAmount + " RS");
    }

    public BroadcastReceiver messagereceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalbill = intent.getIntExtra("totalAmount", 0);
            totalpriceofcart.setText("Total Amount: " + totalbill + " RS");
        }
    };
}
