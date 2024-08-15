package com.example.javaproject;

import android.annotation.SuppressLint;
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

public class EachCategoryProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<newProducts> categoryProducts;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_each_category_product);

        String categoryName = getIntent().getStringExtra("categoryName");

        recyclerView = findViewById(R.id.showallcategoryrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        categoryProducts = new ArrayList<>();
        EachProductCategoryAdapter myAdapter = new EachProductCategoryAdapter(this, categoryProducts);
        recyclerView.setAdapter(myAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(categoryName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                newProducts product = document.toObject(newProducts.class);
                                categoryProducts.add(product);
                            }
                            myAdapter.notifyDataSetChanged();
                        } else {
                            // Handle error
                        }
                    }
                });
    }
}
