package com.example.myamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityVisaPaymentBinding;

import java.util.Objects;

public class activity_visa_payment extends AppCompatActivity {
    ActivityVisaPaymentBinding binding;
    private String name, date;
    private String cardNumber, cvvCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityVisaPaymentBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        try {
            binding.payNowBtn.setOnClickListener ( v -> {
                name = Objects.requireNonNull ( binding.nameEt.getText ( ) ).toString ( );
                date = Objects.requireNonNull ( binding.dateVisaTextViewEt.getText ( ) ).toString ( );
                cvvCard =  Objects.requireNonNull ( binding.cvvVisaTextViewEt.getText ( ) ).toString ( ) ;
                cardNumber =  Objects.requireNonNull ( binding.visaNumberTextViewEt.getText ( ) ).toString ( ) ;


                boolean check=(validationDataCard ( name,date,cardNumber,cvvCard ));
                if (check)
                {
                    startActivity ( new Intent (getBaseContext (),Complete_Order_Successfully.class).putExtra ( "name", getIntent ( ).getStringExtra ( "name" ) )  );
                    finish ();

                }else
                {
                    Toast.makeText ( this, "date Field is Wrong!!!", Toast.LENGTH_SHORT ).show ( );
                }
            } );

        }catch (Exception e ){
            Toast.makeText ( this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ( );
        }




    }


    boolean validationDataCard(String name, String date, String number, String cvv) {
        if (name.equals ( "" )) {
            Toast.makeText ( this, "Please Enter name", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else if (name.contains ( "@#$%&*/*" )) {
            Toast.makeText ( this, "Please Correct the name date", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else if (date.equals ( "" )) {
            Toast.makeText ( this, "Please Enter date of Card Number", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else if (date.contains ( "#@$%^&*!/" )) {
            Toast.makeText ( this, "Please Correct the date ", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else if (number.length () < 16) {
            Toast.makeText ( this, "Number Of Cark must be contains 16 number", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else if (cvv.length () < 3) {
            Toast.makeText ( this, "Cvv Must Be 3 Number", Toast.LENGTH_SHORT ).show ( );
            return false;
        } else {
            return true;
        }
    }
}