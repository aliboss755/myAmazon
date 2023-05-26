package com.example.myamazon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myamazon.databinding.ActivityAleatDilogeSuccsessBinding;
import com.example.myamazon.databinding.SuccessDialogBinding;

public class AlertDislodgeSuccess extends AppCompatActivity {
    ActivityAleatDilogeSuccsessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding=ActivityAleatDilogeSuccsessBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        binding.successBt.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                showSuccessDialog ();
            }
        } );
    }
    public void showSuccessDialog(){
        View view = LayoutInflater.from ( AlertDislodgeSuccess.this ).inflate ( R.layout.success_dialog,binding.getRoot () );
        Button button =view.findViewById ( R.id.successDone );
        AlertDialog.Builder builder =new AlertDialog.Builder ( AlertDislodgeSuccess.this );
        builder.setView ( view );
        final AlertDialog alertDialog =builder.create ();
        button.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss ();
            }
        } );

        if (alertDialog.getWindow ()!=null){
            alertDialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( 0 ) );

        }else {
            alertDialog.show ();
        }


    }
    private void showcreatcustame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SuccessDialogBinding dialogbinding = SuccessDialogBinding.inflate(getLayoutInflater());
        builder.setView(dialogbinding.getRoot());
         AlertDialog alertDialog =builder.create ();
        AlertDialog finalAlertDialog = alertDialog;
        dialogbinding.successDone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText ( AlertDislodgeSuccess.this, "", Toast.LENGTH_SHORT ).show ( );
                finalAlertDialog.dismiss();
            }
        });


        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
}
}