package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myamazon.databinding.ActivityViewAllCustomerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewAllCustomerActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityViewAllCustomerBinding binding;
    public static final String Customer_Kay = "Customer_Kay";
    ArrayList<Customer> customers;
    CustomerAdepter adepter;
    mySql db;
    @SuppressLint("NotifyDataSetChanged")
    ActivityResultLauncher<Intent> launcher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult ( ),
            result -> {
                if (result.getData ( ) != null) {
                    customers = db.getAllCustomers ( );
                    adepter.setCustomers ( customers );
                    adepter.notifyDataSetChanged ( );
                }
            }
    );

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityViewAllCustomerBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        binding.BottomNavigationView.setBackground ( null );
        binding.BottomNavigationView.setOnNavigationItemSelectedListener ( new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item selection here
                switch (item.getItemId ( )) {

                    case R.id.home:
                        startActivity ( new Intent ( ViewAllCustomerActivity.this, ViewProductActivity.class ) );
                        return true;
                    case R.id.add:
                        startActivity ( new Intent ( ViewAllCustomerActivity.this, AddNewProductActivity.class ) );

                        return true;
                }
                return true;
            }

        } );
        db = new mySql ( this );
        customers = db.getAllCustomers ( );
        adepter = new CustomerAdepter ( customers, this );
        adepter.notifyDataSetChanged ( );
        binding.customerRv.setAdapter ( adepter );
        binding.customerRv.setLayoutManager ( new GridLayoutManager ( this, 1, LinearLayoutManager.VERTICAL, false ) );
        binding.customerRv.setHasFixedSize ( true );

    }

    @Override
    public void onItemClick(View view, int position, int id) {
    }

    @Override
    public void onDelete(int index, int id) {

    }
}