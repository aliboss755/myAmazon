package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myamazon.databinding.ActivityCustomeInfoBinding;

import java.util.ArrayList;

public class CustomInfoActivity extends AppCompatActivity {
ActivityCustomeInfoBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding =ActivityCustomeInfoBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
//        ArrayList<SlideModel> models =new ArrayList<> (  );
//        models.add ( new SlideModel ( R.drawable.grill_chicken_1, ScaleTypes.FIT ) );
//        models.add ( new SlideModel ( R.drawable.burger, ScaleTypes.FIT ) );
//        models.add ( new SlideModel ( R.drawable.hamburger, ScaleTypes.FIT ) );
//        models.add ( new SlideModel ( R.drawable.burger_two, ScaleTypes.FIT ) );
//        binding.image.setImageList ( models,ScaleTypes.FIT );
    }
}