package com.example.myamazon;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityInfoProdectCustomerBinding;

import java.util.ArrayList;

public class InfoProductCustomerActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityInfoProdectCustomerBinding binding;
    private int productId;
    mySql dataBase;
    String image;
    int newQuantity = 1;
    int customerId;
   public static int q;
    ArrayList<Product> products;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityInfoProdectCustomerBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        dataBase = new mySql ( this );
        Intent intent = getIntent ( );
        int quantity = intent.getIntExtra ( "q", 10 );
        String name = intent.getStringExtra ( "name" );
        productId = intent.getIntExtra ( "productId", 20 );
        customerId = intent.getIntExtra ( "id", 20 );
        String description = intent.getStringExtra ( "description" );
        double price = intent.getDoubleExtra ( "price", 30 );
        image = intent.getStringExtra ( "image" );
        binding.productName.setText ( name );
        binding.productDescription.setText ( description );
        binding.productPrice.setText ( "$" + price );
        binding.ProductImage.setImageURI ( Uri.parse ( image ) );
        disable ( );

        binding.decrementBtn.setOnClickListener ( v -> {
            newQuantity = Integer.parseInt ( binding.quantity.getText ( ).toString ( ) );
            if (newQuantity <= 1) {
                binding.quantity.setText ( String.valueOf ( 1 ) );

            } else {
                newQuantity--;
            }

            binding.quantity.setText ( String.valueOf ( newQuantity ) );
            if (newQuantity <= 1) {
                binding.quantity.setText ( String.valueOf ( 1 ) );
            }
        } );
        binding.incrementBtn.setOnClickListener ( v -> {
            newQuantity = Integer.parseInt ( binding.quantity.getText ( ).toString ( ) );
            newQuantity++;
            binding.quantity.setText ( String.valueOf ( newQuantity ) );
        } );

        binding.addToCard.setOnClickListener ( v -> {
            try {
                if (quantity > newQuantity) {
                    dataBase.addToCart2 ( customerId
                            , productId,newQuantity );
                   q= Integer.parseInt ( binding.quantity.getText ().toString () );
                 int sum= dataBase.getProductQuantityForCustomer ( customerId );
                    Toast.makeText ( this, ""+sum, Toast.LENGTH_SHORT ).show ( );

                    setResult ( 1,new Intent (  ).putExtra ( "productId",productId ) );
                    finish ( );
                }
            } catch (Exception ignored) {
                Toast.makeText ( this, ""+Error.class, Toast.LENGTH_SHORT ).show ( );
            }
        } );
        binding.bayBt.setOnClickListener ( v1 -> {
            if (quantity > newQuantity) {
                dataBase.updateQuantity ( productId, quantity - newQuantity );
                startActivity ( new Intent ( getBaseContext ( ), activity_checkout_shopping.class ).putExtra ( "name", getIntent ( ).getStringExtra ( "userName" ) ) );
            }

        } );

    }

    private void disable() {
        binding.productDescription.setEnabled ( false );
        binding.productName.setEnabled ( false );
        binding.productPrice.setEnabled ( false );
    }

    @Override
    public void onItemClick(View view, int position, int id) {
        Intent intent = new Intent ( getBaseContext ( ), InfoProductCustomerActivity.class );
        intent.putExtra ( "PRODUCT_KEY", position );
        intent.putExtra ( "description", products.get ( position ).getDescription ( ) );
        intent.putExtra ( "price", products.get ( position ).getPrice ( ) );
        intent.putExtra ( "q", products.get ( position ).getQuantity ( ) );
        intent.putExtra ( "name", products.get ( position ).getName ( ) );
        intent.putExtra ( "image", image );
        intent.putExtra ( "id", id );
        Toast.makeText ( InfoProductCustomerActivity.this, String.valueOf ( position ), Toast.LENGTH_SHORT ).show ( );
        startActivity ( intent );
        finish ( );
    }

    @Override
    public void onDelete(int position, int id) {

    }
}
