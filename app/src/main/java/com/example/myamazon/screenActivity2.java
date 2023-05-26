package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myamazon.databinding.ActivityScreen2Binding;

public class screenActivity2 extends AppCompatActivity {
    ActivityScreen2Binding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding=ActivityScreen2Binding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        @SuppressLint("ResourceType") Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.drawable.set );
        binding.root.startAnimation(slideInAnimation);
        binding.floating.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext (),screenActivity3.class ) ) );

    }
}