package com.example.javaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Products> productList;
    private ArrayList<newProducts> newproductList;

    private RecyclerView recyclerView, recyclerView2;
    private TextView seeallnewproduct, seeallrecommendation;
    private Toolbar toolbar;
    private Button logoutButton;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        seeallnewproduct = view.findViewById(R.id.see_all_newarrival);
        seeallrecommendation = view.findViewById(R.id.see_all_recommendation);
        logoutButton = view.findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                // Clear the onboarding complete status
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("OnboardingComplete", false);
                editor.apply();

                // Navigate to login screen
                Intent intent = new Intent(getContext(), LoginPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        seeallnewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowallProducts.class);
                intent.putExtra("category", "newArrival");
                startActivity(intent);
            }
        });

        seeallrecommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowallProducts.class);
                intent.putExtra("category", "Samsung");
                startActivity(intent);
            }
        });

        // New Products RecyclerView
        recyclerView2 = view.findViewById(R.id.newArrival);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newproductList = new ArrayList<>();
        newproductAdapter adapter = new newproductAdapter(getContext(), newproductList);
        recyclerView2.setAdapter(adapter);

        // Samsung Products RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        productList = new ArrayList<>();
        HomePageAdapter myAdapter = new HomePageAdapter(getContext(), productList);
        recyclerView.setAdapter(myAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch new arrivals
        db.collection("newArrival")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                newProducts category = document.toObject(newProducts.class);
                                newproductList.add(category);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        // Fetch Samsung products
        db.collection("Samsung")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Products product = document.toObject(Products.class);
                                productList.add(product);
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
