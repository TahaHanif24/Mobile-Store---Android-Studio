package com.example.javaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ViewDetail extends AppCompatActivity {

    ImageView productimage;
    TextView productName, ProductDescription, ProductPrice, Productrating, quantity;
    Button addtocartButton, buynowbutton;
    private FirebaseFirestore firebase;
    newProducts productss = null;
    FirebaseAuth auth;
    ImageView additem, removeitem;
    int totalquantity = 1;
    int totalprice = 0;
    int productprice = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        firebase = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object object = getIntent().getSerializableExtra("product");

        if (object instanceof newProducts) {
            productss = (newProducts) object;
        }

        productimage = findViewById(R.id.view_detail_img);
        quantity = findViewById(R.id.quantity);
        additem = findViewById(R.id.add_image);
        removeitem = findViewById(R.id.remove_image);

        productName = findViewById(R.id.view_product_name);
        ProductPrice = findViewById(R.id.view_product_price_database);
        ProductDescription = findViewById(R.id.view_product_detail);
        addtocartButton = findViewById(R.id.view_product_addtocart);
        buynowbutton = findViewById(R.id.view_product_buy_now);

        if (productss != null) {
            Glide.with(getApplicationContext()).load(productss.getImg_url()).into(productimage);
            productName.setText(productss.getName());
            ProductDescription.setText(productss.getDescription());
            ProductPrice.setText(String.valueOf(productss.getPrice()));
            productprice = productss.getPrice();
            totalprice = productss.getPrice() * totalquantity;
        }

        addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        buynowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDetail.this, AddressActivity.class);
                if (productss != null) {
                    intent.putExtra("item", productss);
                }
                startActivity(intent);
            }
        });

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalquantity < 10) {
                    totalquantity++;
                    updateQuantityAndPrice();
                }
            }
        });

        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalquantity > 1) {
                    totalquantity--;
                    updateQuantityAndPrice();
                }
            }
        });
    }

    private void updateQuantityAndPrice() {
        quantity.setText(String.valueOf(totalquantity));
        if (productss != null) {
            totalprice = productss.getPrice() * totalquantity;
        }
    }

    private void addToCart() {
        String savecurrenttime, savecurrentdate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat("MM, dd, yyyy");
        savecurrentdate = simpledate.format(calendar.getTime());
        SimpleDateFormat simpletime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = simpletime.format(calendar.getTime());

        final HashMap<String, Object> cartmap = new HashMap<>();
        cartmap.put("productname", productName.getText().toString());
        cartmap.put("productPrice", productprice);
        cartmap.put("currentDate", savecurrentdate);
        cartmap.put("currentTime", savecurrenttime);
        cartmap.put("totalQuantity", totalquantity);
        cartmap.put("totalPrice", totalprice);

        firebase.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(ViewDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
