package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityLogInBinding;

import java.util.Objects;

public class logInActivity extends AppCompatActivity {
    mySql mySql;
    ActivityLogInBinding binding;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult ( ),
            result -> {

            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityLogInBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        @SuppressLint("ResourceType") Animation slideInAnimation = AnimationUtils.loadAnimation ( this, R.drawable.set );
        binding.singInBt.startAnimation ( slideInAnimation );
        binding.PasswordEt.startAnimation ( slideInAnimation );
        binding.userNameEt.startAnimation ( slideInAnimation );
        binding.textInputLayout.startAnimation ( slideInAnimation );
        binding.card.startAnimation ( slideInAnimation );
        binding.loginBt.startAnimation ( slideInAnimation );
        mySql = new mySql ( this );
        mySql.insertAdmin ( new Admin ( "admin", "1", "" ) );
        binding.loginBt.setOnClickListener ( v -> {
            String userName = Objects.requireNonNull ( binding.userNameEt.getText ( ) ).toString ( );
            String password = Objects.requireNonNull ( binding.PasswordEt.getText ( ) ).toString ( );
            if (mySql.checkUserAdmin ( userName, password )) {

                binding.userNameEt.setText ( "" );
                binding.PasswordEt.setText ( "" );
                startActivity ( new Intent ( getBaseContext ( ), ViewProductActivity.class ) );
            } else if (mySql.checkUserNameAdmin ( userName ) && !mySql.checkPasswordAdmin ( password )) {
                new AlertDialog.Builder ( logInActivity.this ).setTitle ( "The password is wrong" ).setMessage ( "Enter the password correctly" )
                        .setPositiveButton ( "Ok", (dialog, which) -> {
                        } ).setNegativeButton ( "Cancel", (dialog, which) -> {
                        } ).setCancelable ( false ).show ( );
            } else if (userName.isEmpty ( ) && password.isEmpty ( )) {
                new AlertDialog.Builder ( logInActivity.this ).setTitle ( "Fill the Informed" ).setMessage ( "User Name and Password id Empty" )
                        .setPositiveButton ( "Ok", (dialog, which) -> {
                        } ).setNegativeButton ( "Cancel", (dialog, which) -> {
                        } ).setCancelable ( false ).show ( );
            } else if (mySql.checkUserCustomer ( userName, password )) {
                binding.userNameEt.setText ( "" );
                binding.PasswordEt.setText ( "" );
                int id = mySql.getCustomerIdByName ( userName );
                launcher.launch ( new Intent ( getBaseContext ( ), ViewProductCustomerActivity.class ).putExtra ( "userName", userName ).putExtra ( "id", id ) );
            } else if (mySql.checkUserNameCustomer ( userName ) && !mySql.checkPasswordCustomer ( password )) {
                new AlertDialog.Builder ( logInActivity.this ).setTitle ( "The password is wrong" ).setMessage ( "Enter the password correctly" )
                        .setPositiveButton ( "Ok", (dialog, which) -> {
                        } ).setNegativeButton ( "Cancel", (dialog, which) -> {
                        } ).setCancelable ( false ).show ( );
            } else {
                binding.userNameEt.setText ( "" );
                binding.PasswordEt.setText ( "" );
                new AlertDialog.Builder ( logInActivity.this ).setTitle ( "Not Found User " ).setMessage ( "Create an account if you do not have one" )
                        .setPositiveButton ( "Ok", (dialog, which) -> launcher.launch ( new Intent ( getBaseContext ( ), SingInActivity.class ) ) ).setNegativeButton ( "Cancel", (dialog, which) -> {
                        } ).setCancelable ( false ).show ( );
            }
        } );

        binding.singInBt.setOnClickListener ( v -> launcher.launch ( new Intent ( getBaseContext ( ), SingInActivity.class ) ) );

    }


}