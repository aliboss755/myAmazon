package com.example.myamazon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myamazon.databinding.ActivityViewProdectBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ViewProductActivity extends AppCompatActivity implements OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 5;
    ActivityViewProdectBinding binding;

    public static final int DELETE_PRODUCT = 2;
    ProductAdepter adepter;
    mySql dataBase;

    public static ArrayList<Product> products;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult ( ),
            new ActivityResultCallback<ActivityResult> ( ) {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode ( ) == AddNewProductActivity.aDD_NEW_PRODUCT && result.getData ( ) != null) {
                        Intent intent = result.getData ( );
                        int q = intent.getIntExtra ( "q", -1 );
                        String name = intent.getStringExtra ( "name" );
                        double price = intent.getDoubleExtra ( "price", -1 );
                        String description = intent.getStringExtra ( "description" );
                        Toast.makeText ( ViewProductActivity.this, name, Toast.LENGTH_SHORT ).show ( );
                        String image = intent.getStringExtra ( "image" );
                        dataBase.insertProduct ( new Product ( name, image, description, price, q ) );
                        products = dataBase.getAllProduct ( );
                        adepter.setProducts ( products );
                        adepter.notifyDataSetChanged ( );

                    } else if (result.getResultCode ( ) == DELETE_PRODUCT && result.getData ( ) != null) {
                        int index = result.getData ( ).getIntExtra ( "index", -1 );
                        dataBase.deleteProduct ( index );
                        products = dataBase.getAllProduct ( );
                        adepter.setProducts ( products );
                        adepter.notifyDataSetChanged ( );
                        adepter.notifyItemRemoved ( index );
                    } else if (result.getResultCode ( ) == 0 && null != result.getData ( )) {
                        Intent intent = result.getData ( );
                        int q = intent.getIntExtra ( "q", -1 );
                        String name = intent.getStringExtra ( "name" );
                        double price = intent.getDoubleExtra ( "price", -1 );
                        String description = intent.getStringExtra ( "description" );
                        int index = intent.getIntExtra ( "index", -1 );
                        String image = intent.getStringExtra ( "image" );
                        Toast.makeText ( ViewProductActivity.this, String.valueOf ( index ), Toast.LENGTH_SHORT ).show ( );
                        dataBase.updateProduct ( index, name, image, description, price, q );
                        products = dataBase.getAllProduct ( );
                        adepter.setProducts ( products );
                        adepter.notifyDataSetChanged ( );
                    }
                    products = dataBase.getAllProduct ( );
                    adepter.setProducts ( products );
                }
            }
    );


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate ( savedInstanceState );
        binding = ActivityViewProdectBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        binding.BottomNavigationView.setBackground ( null );
        dataBase = new mySql ( this );
        products = dataBase.getAllProduct ( );
        adepter = new ProductAdepter ( products, this );
        binding.productRv.setAdapter ( adepter );
        binding.productRv.setLayoutManager ( new GridLayoutManager ( this, 2, LinearLayoutManager.VERTICAL, false ) );
        binding.productRv.setHasFixedSize ( true );
        //noinspection deprecation
        binding.BottomNavigationView.setOnNavigationItemSelectedListener ( this::onNavigationItemSelected2 );


        if (ContextCompat.checkSelfPermission ( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE );
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.search, menu );
        androidx.appcompat.widget.SearchView searchView = ( androidx.appcompat.widget.SearchView ) menu.findItem ( R.id.searchid ).getActionView ( );
        searchView.setSubmitButtonEnabled ( true );
        searchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener ( ) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                products = dataBase.getProduct ( query );
                adepter.setProducts ( products );
                adepter.notifyDataSetChanged ( );
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                products = dataBase.getProduct ( newText );
                adepter.setProducts ( products );
                adepter.notifyDataSetChanged ( );
                return false;
            }
        } );
        searchView.setOnCloseListener ( () -> {
            products = dataBase.getAllProduct ( );
            adepter.setProducts ( products );
            adepter.notifyDataSetChanged ( );
            return false;
        } );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId ( ) == R.id.customer) {
            startActivity ( new Intent ( getBaseContext ( ), ViewAllCustomerActivity.class ) );
        }
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClick(View view, int position, int id) {
        Intent intent = new Intent ( getBaseContext ( ), MainActivity.class );
        intent.putExtra ( "PRODUCT_KEY", id );
        intent.putExtra ( "description", products.get ( position ).getDescription ( ) );
        intent.putExtra ( "price", products.get ( position ).getPrice ( ) );
        intent.putExtra ( "q", products.get ( position ).getQuantity ( ) );
        intent.putExtra ( "name", products.get ( position ).getName ( ) );
        intent.putExtra ( "image", products.get ( position ).getImage ( ) );
        adepter.notifyDataSetChanged ( );
        launcher.launch ( intent );
    }

    @Override
    public void onDelete(int index, int id) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected2(MenuItem item) {
        // Handle item selection here
        switch (item.getItemId ( )) {

            case R.id.add:
                Intent intent = new Intent ( getBaseContext ( ), AddNewProductActivity.class );
                launcher.launch ( intent );
                return true;
            case R.id.persone:
                startActivity ( new Intent ( ViewProductActivity.this, ViewAllCustomerActivity.class ) );

                return true;
        }
        return true;
    }
}
