package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Complete_Order_Successfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getWindow().requestFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView ( R.layout.activity_complete_order_successfully );
        new Handler (  ).postDelayed ( () -> {
            startActivity ( new Intent (getBaseContext (),ViewProductCustomerActivity.class).putExtra ( "userName", getIntent ().getStringExtra ( "name" ) )  );
            finish ();
        },2000 );


    }
}