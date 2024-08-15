package com.example.javaproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaproject.databinding.ActivityHomePageBinding;
import com.google.android.material.bottomappbar.BottomAppBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_logout_24);


        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.navigationView.setBackground(null);

        binding.navigationView.setOnItemSelectedListener(menuItem -> {
           int itemID =menuItem.getItemId();
           if(itemID==R.id.home){
               replaceFragment(new HomeFragment());
           }
            else if (itemID==R.id.category) {
               replaceFragment(new CategoryFragment());

           } else if (itemID==R.id.cart) {
               replaceFragment(new CartFragment());

           } else if (itemID==R.id.account) {
               replaceFragment(new AccountFragment());

           }
           return true;
        });
        BottomAppBar br=new BottomAppBar(this);

    }
        private void replaceFragment(Fragment fragment){
    FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,  fragment);
            fragmentTransaction.commit();
        }
    }