package com.example.myamazon;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.myamazon.databinding.ActivityScreen2Binding;
import com.example.myamazon.databinding.ActivityScrren1Binding;

public class screenActivity1 extends AppCompatActivity {
ActivityScrren1Binding binding ;
 ActivityScreen2Binding binding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding=ActivityScrren1Binding.inflate ( getLayoutInflater () );
        binding2=ActivityScreen2Binding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        @SuppressLint("ResourceType") Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.drawable.set );
        binding.root.startAnimation(slideInAnimation);
        binding.floating.setOnClickListener ( v -> {
                startActivity (new Intent (   getBaseContext (),screenActivity2.class) );
        } );




    }


}