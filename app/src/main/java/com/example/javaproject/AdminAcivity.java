package com.example.javaproject;


import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javaproject.databinding.ActivityAdminAcivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AdminAcivity extends AppCompatActivity {

    private String productName;
    private String productPrice;
    private String productDescription;
    private String productCategory;
    private Uri productImage = null;

    // Firebase
    FirebaseAuth auth;
    FirebaseDatabase database;
    private ActivityResultLauncher<String> mGetContent;

    ActivityAdminAcivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAcivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            binding.selectedImage.setImageURI(uri);
                            productImage = uri;
                        }
                    }
                });

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting data from fields
                productName = binding.FoodName.getText().toString();
                productPrice = binding.Price.getText().toString();
                productDescription = binding.descrption.getText().toString();
                productCategory = binding.category.getText().toString();

                if (!(productName.isEmpty() || productPrice.isEmpty() || productDescription.isEmpty() || productCategory.isEmpty())) {
                    uploadData();
                    Toast.makeText(AdminAcivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminAcivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        // Get database reference
        DatabaseReference categoryRef = database.getReference(productCategory);
        // Generate a unique key
        String newItemKey = categoryRef.push().getKey();

        if (productImage != null) {
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            StorageReference imgRef = storage.child("product_images/" + newItemKey + ".jpg");
            UploadTask uploadTask = imgRef.putFile(productImage);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
                            String key = categoryRef.push().getKey();
                            // Create a new product
                            newProducts newProduct = new newProducts(downloadUrl, productName ,Integer.parseInt(productPrice) ,productDescription );

                            if (key != null) {
                                categoryRef.child(key).setValue(newProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AdminAcivity.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AdminAcivity.this, "Failed to upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminAcivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
}
