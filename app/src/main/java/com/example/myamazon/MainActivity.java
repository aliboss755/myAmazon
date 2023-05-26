package com.example.myamazon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myamazon.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int productId;
    mySql dataBase;
    ActivityResultLauncher<Intent> mGetContent;
    private Uri imageBitmap = null;

    @SuppressLint({"SuspiciousIndentation", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityMainBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        mGetContent = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( )
                , result -> {
                    if (result.getData ( ) != null && result.getResultCode ( ) == RESULT_OK) {
                        Intent intent = result.getData ( );
                        imageBitmap = intent.getData ( );
                        binding.image.setImageURI ( imageBitmap );

                    }
                } );
        binding.image.setOnClickListener ( v -> {
            Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            mGetContent.launch ( intent );
        } );
        if (ContextCompat.checkSelfPermission ( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ViewProductActivity.STORAGE_PERMISSION_REQUEST_CODE );
        }
        binding.toolbar.setVisibility ( View.INVISIBLE );
        binding.toolbar.setSelectedItemId ( R.id.details );
        Menu m = binding.toolbar.getMenu ( );
        MenuItem save = m.findItem ( R.id.details );
        MenuItem edit = m.findItem ( R.id.edit );
        MenuItem delete = m.findItem ( R.id.delete );
        edit.setVisible ( false );
        delete.setVisible ( false );
        save.setVisible ( false );


        dataBase = new mySql ( this );
        productId = getIntent ( ).getIntExtra ( "PRODUCT_KEY", -1 );
        Toast.makeText ( this, String.valueOf ( productId ), Toast.LENGTH_SHORT ).show ( );
        binding.toolbar.setOnItemSelectedListener ( item -> {
            edit.setVisible ( false );
            delete.setVisible ( false );
            save.setVisible ( false );
            switch (item.getItemId ( )) {
                case R.id.details:
                    edit.setVisible ( false );
                    delete.setVisible ( false );
                    save.setVisible ( false );
                    Intent intent = new Intent ( );
                    String description = Objects.requireNonNull ( binding.productDescription.getText ( ) ).toString ( );
                    String name = Objects.requireNonNull ( binding.productName.getText ( ) ).toString ( );
                    double price = Double.parseDouble ( Objects.requireNonNull ( binding.productPrice.getText ( ) ).toString ( ) );
                    int q = Integer.parseInt ( Objects.requireNonNull ( binding.productQuantity.getText ( ) ).toString ( ) );
                    intent.putExtra ( "name", name );
                    intent.putExtra ( "description", description );
                    intent.putExtra ( "price", price );
                    intent.putExtra ( "q", q );
                    intent.putExtra ( "index", productId );
                    if (imageBitmap != null)
                        intent.putExtra ( "image", imageBitmap.toString ( ) );
                    setResult ( 0, intent );
                    finish ( );
                    break;
                case R.id.delete:
                    edit.setVisible ( false );
                    delete.setVisible ( false );
                    save.setVisible ( false );
                    Intent intent1 = new Intent ( );
                    intent1.putExtra ( "index", productId );
                    setResult ( ViewProductActivity.DELETE_PRODUCT, intent1 );
                    finish ( );
                    break;
            }
            edit.setVisible ( false );
            delete.setVisible ( false );
            save.setVisible ( false );
            return false;


        } );
        disable ( false );
        clear ( );
        //view
        Toast.makeText ( this, String.valueOf ( productId ), Toast.LENGTH_SHORT ).show ( );
        Intent intent = getIntent ( );
        int q = intent.getIntExtra ( "q", -1 );
        String name = intent.getStringExtra ( "name" );
        String description = intent.getStringExtra ( "description" );
        double price = intent.getDoubleExtra ( "price", -1 );
        String image = intent.getStringExtra ( "image" );
        binding.productName.setText ( name );
        binding.productDescription.setText ( description );
        binding.productQuantity.setText ( String.valueOf ( q ) );
        binding.productPrice.setText ( String.valueOf ( price ) );
        if (!image.equals ( "" ))
            binding.image.setImageURI ( Uri.parse ( image ) );


    }

    private void disable(boolean b) {
        binding.productQuantity.setEnabled ( b );
        binding.productDescription.setEnabled ( b );
        binding.productName.setEnabled ( b );
        binding.productPrice.setEnabled ( b );
        binding.image.setEnabled ( b );
    }

    private void clear() {
        binding.image.setImageURI ( null );
        binding.productQuantity.setText ( "" );
        binding.productDescription.setText ( "" );
        binding.productName.setText ( "" );
        binding.productPrice.setText ( "" );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.details_menu, menu );
        MenuItem save = menu.findItem ( R.id.details );
        MenuItem edit = menu.findItem ( R.id.edit );
        MenuItem delete = menu.findItem ( R.id.delete );
        save.setVisible ( false );
        edit.setVisible ( true );
        delete.setVisible ( true );
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ( )) {
            case R.id.details:
                Intent intent = new Intent ( );
                String description = Objects.requireNonNull ( binding.productDescription.getText ( ) ).toString ( );
                String name = Objects.requireNonNull ( binding.productName.getText ( ) ).toString ( );
                double price = Double.parseDouble ( Objects.requireNonNull ( binding.productPrice.getText ( ) ).toString ( ) );
                int q = Integer.parseInt ( Objects.requireNonNull ( binding.productQuantity.getText ( ) ).toString ( ) );
                intent.putExtra ( "name", name );
                intent.putExtra ( "description", description );
                intent.putExtra ( "price", price );
                intent.putExtra ( "q", q );
                intent.putExtra ( "index", productId );
//                intent.putExtra ( "image", imageUri.toString ( ) );
                setResult ( 0, intent );
                finish ( );
                break;
            case R.id.delete:
                Intent intent1 = new Intent ( );
                intent1.putExtra ( "index", productId );
                setResult ( ViewProductActivity.DELETE_PRODUCT, intent1 );
                finish ( );
                break;
            case R.id.edit:

                Menu m = binding.toolbar.getMenu ( );
                MenuItem save = m.findItem ( R.id.details );
                MenuItem edit = m.findItem ( R.id.edit );
                MenuItem delete = m.findItem ( R.id.delete );
                save.setVisible ( true );
                edit.setVisible ( false );
                delete.setVisible ( false );
                disable ( true );
                binding.toolbar.setVisibility ( View.VISIBLE );
                break;

        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
    }

}