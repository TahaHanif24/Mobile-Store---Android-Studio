package com.example.javaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    Context context;
    ArrayList<AddressModel> addressModels;
    SelectedAddress selectedAddress;
    RadioButton radioButton;

    public AddressAdapter(Context context, ArrayList<AddressModel> addressModels, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModels = addressModels;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.MyViewHolder holder, int position) {
        holder.address.setText(addressModels.get(position).getUserAddress());
        holder.radiobutton.setChecked(addressModels.get(position).isSelected());
        holder.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel address : addressModels) {
                    address.setSelected(false);
                }
                addressModels.get(position).setSelected(true);
                if (radioButton != null) {
                    radioButton.setChecked(false);
                }
                radioButton = (RadioButton) v;
                radioButton.setChecked(true);
                selectedAddress.setAddress(addressModels.get(position).getUserAddress());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radiobutton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radiobutton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
