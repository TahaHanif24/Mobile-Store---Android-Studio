package com.example.javaproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    Context context;
    ArrayList<Mycart> mycartList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    public MyCartAdapter(Context context, ArrayList<Mycart> mycartList) {
        this.context = context;
        this.mycartList = mycartList;
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartmenu_list, parent, false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Mycart mycart = mycartList.get(position);
        holder.productName.setText(mycart.getProductName());
        holder.productPrice.setText(String.valueOf(mycart.getProductPrice()));
        holder.totalPrice.setText(String.valueOf(mycart.getTotalPrice()));
        holder.totalQuantity.setText(String.valueOf(mycart.getTotalQuantity())); // Convert int to String
        holder.currentDate.setText(mycart.getCurrentDate());
        holder.currentTime.setText(mycart.getCurrentTime());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User").document(mycart.getDocumentId())
                        .delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                mycartList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mycartList.size());
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mycartList.size();
    }

    public static class MyCartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, totalPrice, totalQuantity, currentDate, currentTime;
        Button deleteButton;

        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            currentDate = itemView.findViewById(R.id.current_date);
            currentTime = itemView.findViewById(R.id.current_time);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
