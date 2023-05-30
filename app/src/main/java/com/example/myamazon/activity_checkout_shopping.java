package com.example.myamazon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityCheckoutShoppingBinding;

public class activity_checkout_shopping extends AppCompatActivity {
    ActivityCheckoutShoppingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityCheckoutShoppingBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        binding.card1.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext ( ), activity_visa_payment.class ).putExtra ( "name", getIntent ( ).getStringExtra ( "name" ) ) ) );
        binding.arrowMovedBtn.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext ( ), activity_visa_payment.class ).putExtra ( "name", getIntent ( ).getStringExtra ( "name" ) ) ) );
        binding.cash.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext ( ), Complete_Order_Successfully.class ).putExtra ( "name", getIntent ( ).getStringExtra ( "name" ) ) ) );
        binding.arrowMoveddBtn.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext ( ), Complete_Order_Successfully.class ).putExtra ( "name", getIntent ( ).getStringExtra ( "name" ) ) ) );


    }
}