package com.example.javaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowallProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<newProducts> showproducts;
    private newproductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall_products);

        String category = getIntent().getStringExtra("category");

        recyclerView = findViewById(R.id.show_product_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        showproducts = new ArrayList<>();
        adapter = new newproductAdapter(this, showproducts);
        recyclerView.setAdapter(adapter);

        fetchProducts(category);
    }

    private void fetchProducts(String category) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (category != null) {
            db.collection(category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    newProducts product = document.toObject(newProducts.class);
                                    showproducts.add(product);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}
