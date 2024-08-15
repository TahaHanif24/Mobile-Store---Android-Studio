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

public class newproductAdapter extends RecyclerView.Adapter<newproductAdapter.MyViewHolder> {
    Context context;
    ArrayList<newProducts> newproductList;

    public newproductAdapter(Context context, ArrayList<newProducts> newproductList) {
        this.context = context;
        this.newproductList = newproductList;
    }

    @NonNull
    @Override
    public newproductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newproductAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        newProducts category = newproductList.get(position);
        Glide.with(context).load(category.getImg_url()).into(holder.image);
        holder.name.setText(category.name);
        holder.price.setText(String.valueOf(category.price)); // Convert int to String here
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetail.class);
                intent.putExtra("product", newproductList.get(position)); // Use "product" as the key
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newproductList.size();
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
