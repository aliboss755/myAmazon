package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.ActivityViewProductCustomerBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewProductCustomerActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityViewProductCustomerBinding binding;
    ArrayList<Product> products;
    ProductAdepter adepter;
    Customer customer;
    String userName;
    mySql dataBase;
    boolean isCart;
    int id;
    int productId;
    @SuppressLint("NotifyDataSetChanged")
    ActivityResultLauncher<Intent> launcher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult ( ),
            result -> {
                if (result.getData ( ) != null) {
                    products = dataBase.getAllProduct ( );
                    adepter.setProducts ( products );
                    adepter.notifyDataSetChanged ( );
                } else if (result.getResultCode ( ) == 1) {
                    assert false;
                    productId = result.getData ( ).getIntExtra ( "productId", -1 );
                }
            }
    );

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityViewProductCustomerBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        userName = getIntent ( ).getStringExtra ( "userName" );
        setCategories ( );
        isCart = getIntent ( ).getBooleanExtra ( "is", false );
        dataBase = new mySql ( this );
        products = dataBase.getAllProduct ( );
        try {
            id = dataBase.getCustomerIdByName ( userName );
        } catch (Exception e) {
            Toast.makeText ( this, "Error", Toast.LENGTH_SHORT ).show ( );
        }
        adepter = new ProductAdepter ( products, this );

        customer = dataBase.getCustomer ( id );

        binding.BottomNavigationView.setOnNavigationItemSelectedListener ( item -> {
            // Handle item selection here
            switch (item.getItemId ( )) {
                case R.id.bag:
                    launcher.launch ( new Intent ( getBaseContext ( ), Ordering_CartActivity.class ).putExtra ( "customerId", id ).putExtra ( "productId", productId ) );
                    return true;
                case R.id.persone:
                    startActivity ( new Intent ( getBaseContext ( ), UserPageActivity.class ).putExtra ( "name", userName ).putExtra ( "id", id ) );

                    return true;
            }
            return true;
        } );
        binding.recyclerFood.setAdapter ( adepter );
        binding.recyclerFood.setLayoutManager ( new GridLayoutManager ( this, 2, LinearLayoutManager.VERTICAL, false ) );
        binding.recyclerFood.setHasFixedSize ( true );
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
        binding.profileIV.setOnClickListener ( v -> startActivity ( new Intent ( getBaseContext ( ), UserPageActivity.class ).putExtra ( "name", userName ).putExtra ( "id", id ) ) );
        return true;
    }


    @Override
    public void onItemClick(View view, int position, int id) {
        productId = id;
        Intent intent = new Intent ( getBaseContext ( ), InfoProductCustomerActivity.class );
        intent.putExtra ( "PRODUCT_KEY", position );
        intent.putExtra ( "userName", userName );
        intent.putExtra ( "description", products.get ( position ).getDescription ( ) );
        intent.putExtra ( "price", products.get ( position ).getPrice ( ) );
        intent.putExtra ( "q", products.get ( position ).getQuantity ( ) );
        intent.putExtra ( "name", products.get ( position ).getName ( ) );
        intent.putExtra ( "image", products.get ( position ).getImage ( ) );
        intent.putExtra ( "productId", id );
        try {
            intent.putExtra ( "id", dataBase.getCustomerIdByName ( userName ) );
        } catch (Exception ignored) {

        }

        Toast.makeText ( ViewProductCustomerActivity.this, String.valueOf ( position ), Toast.LENGTH_SHORT ).show ( );
        launcher.launch ( intent );
    }

    @Override
    public void onDelete(int index, int id) {

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories() {
        List<Category> data = new ArrayList<> ( );
        Category productCategory = new Category ( "burger", R.drawable.ic_burger );
        Category productCategory2 = new Category ( "short", R.drawable.ic_pizza );
        Category productCategory3 = new Category ( "dress", R.drawable.ic_pizza );
        Category productCategory4 = new Category ( "dress", R.drawable.ic_chicken );
        data.add ( productCategory );
        data.add ( productCategory2 );
        data.add ( productCategory3 );
        data.add ( productCategory4 );
        CategoryAdapter categoryAdapter = new CategoryAdapter ( data, ViewProductCustomerActivity.this );
        binding.recyclerCategories.setLayoutManager ( new LinearLayoutManager ( ViewProductCustomerActivity.this, RecyclerView.HORIZONTAL, false ) );
        binding.recyclerCategories.setAdapter ( categoryAdapter );
        categoryAdapter.notifyDataSetChanged ( );

    }
}