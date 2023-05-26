package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myamazon.databinding.ActivityScreen3Binding;

public class screenActivity3 extends AppCompatActivity {
    ActivityScreen3Binding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding=ActivityScreen3Binding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        @SuppressLint("ResourceType") Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.drawable.set );
        binding.root.startAnimation(slideInAnimation);
        binding.floating.setOnClickListener ( v -> {
          startActivity ( new Intent ( getApplicationContext (),logInActivity.class ) );
        } );
    }
}