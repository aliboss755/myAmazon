package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myamazon.databinding.ActivityOrdringCartBinding;

import java.util.ArrayList;

public class Ordering_CartActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityOrdringCartBinding binding;
    int productId;
    int customerId;
    mySql mySql;
    ArrayList<Product> products;
    ProductAdepter adepter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityOrdringCartBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        mySql = new mySql ( this );
        Intent intent = getIntent ( );
        productId = intent.getIntExtra ( "productId", -1 );
        customerId = intent.getIntExtra ( "customerId", -1 );
        mySql.associateProductWithCustomer ( productId, customerId );
        products = ( ArrayList<Product> ) mySql.getProductsByCustomer2 ( customerId );
         adepter = new ProductAdepter ( products, this );
        binding.productRv.setAdapter ( adepter );
        adepter.notifyDataSetChanged ( );
        binding.productRv.setLayoutManager ( new GridLayoutManager ( this, 2, LinearLayoutManager.VERTICAL, false ) );
        binding.productRv.setHasFixedSize ( true );
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClick(View view, int position, int id) {
        mySql.deleteItemFromBasket ( customerId,productId );
        products = ( ArrayList<Product> ) mySql.getProductsByCustomer2 (customerId );
        adepter.notifyDataSetChanged ( );

    }

    @Override
    public void onDelete(int position, int id) {

    }
}