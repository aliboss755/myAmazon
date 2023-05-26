package com.example.myamazon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityPaymentBinding;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityPaymentBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

    }
}