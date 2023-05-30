package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myamazon.databinding.ActivityCustomeInfoBinding;

public class CustomInfoActivity extends AppCompatActivity {
ActivityCustomeInfoBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding =ActivityCustomeInfoBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        Intent intent =getIntent ();
        String name =intent.getStringExtra ( "name" );
        String password =intent.getStringExtra ( "password" );
        int id =intent.getIntExtra ( "customerId",-1 ) ;

    }
}