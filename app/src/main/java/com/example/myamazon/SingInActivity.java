package com.example.myamazon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivitySingInBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingInActivity extends AppCompatActivity {
    ActivitySingInBinding binding;
    mySql customerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivitySingInBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        customerDB=new mySql ( this );
        @SuppressLint("ResourceType") Animation slideInAnimation = AnimationUtils.loadAnimation ( this, R.drawable.set );
        binding.createBt.startAnimation ( slideInAnimation );
        binding.emileEt.startAnimation ( slideInAnimation );

        binding.PasswordEt.startAnimation ( slideInAnimation );
        binding.userNameEt.startAnimation ( slideInAnimation );
        binding.imageLogin.startAnimation ( slideInAnimation );
        binding.card.startAnimation ( slideInAnimation );
        binding.userNameEt.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password =s.toString ();
                binding.textInputLayout.setErrorEnabled ( true );
                if (password.length ()>=10){
                    Pattern pattern =Pattern.compile ( ".*[^a-zA-Z0-9].*" );
                    Matcher matcher =pattern.matcher ( password );
                    boolean isPassword= matcher.find ( );
                    if (isPassword){
                        binding.textInputLayout.setHelperText ( "Strong Password" );
                        binding.textInputLayout.setError ( "" );
                    }else {
                        binding.textInputLayout.setHelperText ( "" );
                        binding.textInputLayout.setErrorEnabled ( true );
                        binding.textInputLayout.setError ( "Weak password . Include minimum 1 special char " );
                    }


                }else {
                    binding.textInputLayout.setHelperText ( "Enter Minimum 10 char" );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        String name = binding.userNameEt.getText ( ).toString ( );
        binding.createBt.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String userName = binding.userNameEt.getText ( ).toString ( );
                String password = binding.PasswordEt.getText ( ).toString ( );
                String emile=   binding.emileEt.getText ( ).toString ( );
//                if (logInActivity.customerDB.isUserUnique ( userName,password,emile )){
                    if (isUsernameValid (userName)&&isPasswordValid ( password )&&isEmailValid ( emile )) {
                        boolean n = customerDB.insertCustomer ( new Customer ( userName, password, emile ) );
                        if (n)
                            Toast.makeText ( SingInActivity.this, "ok", Toast.LENGTH_SHORT ).show ( );
                        finish ( );
                    } else {
                        new AlertDialog.Builder ( SingInActivity.this ).setTitle ( "Fill the Informed" ).setMessage ( "username is at least 5 characters password is at least 8 characters " )
                                .setPositiveButton ( "Ok", (dialog, which) -> {
                                } ).setNegativeButton ( "Cancel", (dialog, which) -> {
                                } ).setCancelable ( false ).show ( );
                    }



                }


//            }
        } );



    }
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        // Check if email contains "@" symbol
        if (!email.contains("@")) {
            return false;
        }
        // Check if email contains a domain name after the "@" symbol
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String domainName = parts[1];
        if (domainName == null || domainName.isEmpty()) {
            return false;
        }
        // Check if the domain name contains a top-level domain (e.g. ".com", ".org")
        String[] domainParts = domainName.split("\\.");
        if (domainParts.length < 2) {
            return false;
        }
        String topLevelDomain = domainParts[domainParts.length - 1];
        if (topLevelDomain == null || topLevelDomain.isEmpty()) {
            return false;
        }
        // Check if the domain name contains a second-level domain (e.g. "example.com", "example.co.uk")
        String secondLevelDomain = domainParts[domainParts.length - 2];
        if (secondLevelDomain == null || secondLevelDomain.isEmpty()) {
            return false;
        }
        // Check if the email address has a local part (e.g. "username" in "username@example.com")
        String localPart = parts[0];
        if (localPart == null || localPart.isEmpty()) {
            return false;
        }
        return true;
    }
    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Check if password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Check if password contains at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Check if password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Check if password contains at least one special character (e.g. @, #, $)
        if (!password.matches(".*[!@#$%^&*+=?-].*")) {
            return false;
        }
        return true;
    }
    public static boolean isUsernameValid(String username) {
        if (username == null) {
            return false;
        }
        // Check if username is at least 5 characters long
        if (username.length() < 5) {
            return false;
        }
        // Check if username contains only alphanumeric characters and underscore
        if (!username.matches("[a-zA-Z0-9_]+")) {
            return false;
        }
        return true;
    }


}