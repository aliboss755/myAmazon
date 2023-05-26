package com.example.myamazon;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityAddNewProductBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AddNewProductActivity extends AppCompatActivity {
    ActivityAddNewProductBinding binding;
    private Uri imageBitmap = null;
    public static final int aDD_NEW_PRODUCT = 444;
    private ActivityResultLauncher<Intent> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityAddNewProductBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        binding.BottomNavigationView.setBackground ( null );
        binding.BottomNavigationView.setOnNavigationItemSelectedListener ( new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item selection here
                switch (item.getItemId ( )) {

                    case R.id.home:
                        startActivity ( new Intent ( AddNewProductActivity.this, ViewProductActivity.class ) );
                        return true;
                    case R.id.persone:
                        startActivity ( new Intent ( AddNewProductActivity.this, ViewAllCustomerActivity.class ) );

                        return true;
                }
                return true;
            }

        } );
        mGetContent = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( )
                , result -> {
                    if (result.getData ( ) != null && result.getResultCode ( ) == RESULT_OK) {
                        Intent intent = result.getData ( );
                        imageBitmap = intent.getData ( );
                        binding.image.setImageURI ( imageBitmap );


                    }
                } );


        binding.floatingBt.setOnClickListener ( v -> {
            Intent intent = new Intent ( );
            String description = Objects.requireNonNull ( binding.productDescription.getText ( ) ).toString ( );
            String name = Objects.requireNonNull ( binding.productName.getText ( ) ).toString ( );
            double price = Double.parseDouble ( Objects.requireNonNull ( binding.productPrice.getText ( ) ).toString ( ) );
            int q = Integer.parseInt ( Objects.requireNonNull ( binding.productQuantity.getText ( ) ).toString ( ) );
            intent.putExtra ( "name", name );
            intent.putExtra ( "description", description );
            intent.putExtra ( "price", price );
            intent.putExtra ( "q", q );
            if (imageBitmap != null) {
                intent.putExtra ( "image", imageBitmap.toString ( ) );
            }
            setResult ( aDD_NEW_PRODUCT, intent );
            finish ( );

        } );
        binding.image.setOnClickListener ( v -> {
            Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            mGetContent.launch ( intent );
        } );


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        if (requestCode == ViewProductActivity.STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                makeText ( this, "Permission Denied. Cannot access media files.", Toast.LENGTH_SHORT ).show ( );
            }
        }
    }

}