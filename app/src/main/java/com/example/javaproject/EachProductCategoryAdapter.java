package com.example.javaproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class EachProductCategoryAdapter extends RecyclerView.Adapter<EachProductCategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<newProducts> categoryProducts;

    public EachProductCategoryAdapter(Context context, ArrayList<newProducts> categoryProducts) {
        this.context = context;
        this.categoryProducts = categoryProducts;
    }

    @NonNull
    @Override
    public EachProductCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        newProducts product = categoryProducts.get(position);
        Glide.with(context).load(product.getImg_url()).into(holder.image);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice())); // Convert int to String

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetail.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.new_product_image);
            name = itemView.findViewById(R.id.new_product_name);
            price = itemView.findViewById(R.id.new_product_price);
        }
    }
}
