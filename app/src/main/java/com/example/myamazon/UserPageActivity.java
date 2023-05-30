package com.example.myamazon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myamazon.databinding.ActivityUserPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class UserPageActivity extends AppCompatActivity {
    ActivityUserPageBinding binding;
    mySql mySql;
    int id;
    BottomNavigationView navigationView;
    Customer customer;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityUserPageBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        navigationView = (binding.toolbar);
        id = getIntent ( ).getIntExtra ( "id", -1 );
        String name2 = getIntent ( ).getStringExtra ( "name");
        mySql = new mySql ( this );
        binding.EmileEt.setEnabled ( false );
        binding.userNameEt.setEnabled ( false );
        binding.PasswordEt.setEnabled ( false );
        mySql mySql = new mySql ( this );
        customer = mySql.getCustomer ( getIntent ( ).getIntExtra ( "id", -1 ) );
        Toast.makeText ( this, customer.getPassword ( ), Toast.LENGTH_SHORT ).show ( );
        binding.userNameEt.setText ( customer.getUserName ( ) );
        binding.PasswordEt.setText ( customer.getPassword ( ) );
        binding.EmileEt.setText ( customer.getEmail ( ) );
        binding.toolbar.setSelectedItemId ( R.id.details );
        Menu m = binding.toolbar.getMenu ( );
        MenuItem save = m.findItem ( R.id.details );
        MenuItem edit = m.findItem ( R.id.edit );
        MenuItem delete = m.findItem ( R.id.delete );
        edit.setVisible ( false );
        delete.setVisible ( false );
        save.setVisible ( false );
        String name1 = Objects.requireNonNull ( binding.userNameEt.getText ( ) ).toString ( );
        String password1 = Objects.requireNonNull ( binding.PasswordEt.getText ( ) ).toString ( );
        String emile1 = Objects.requireNonNull ( binding.EmileEt.getText ( ) ).toString ( );
        binding.toolbar.setOnItemSelectedListener ( item -> {

            if (item.getItemId ( ) == R.id.details) {
                Menu menu = binding.toolbar.getMenu ( );
                menu.getItem ( 0 ).setVisible ( false );
                menu.getItem ( 1 ).setVisible ( false );
                menu.getItem ( 2 ).setVisible ( false );
                String name = Objects.requireNonNull ( binding.userNameEt.getText ( ) ).toString ( );
                String password = Objects.requireNonNull ( binding.PasswordEt.getText ( ) ).toString ( );
                String emile = Objects.requireNonNull ( binding.EmileEt.getText ( ) ).toString ( );
                if (SingInActivity.isUsernameValid ( name )&&SingInActivity.isPasswordValid ( password )&&SingInActivity.isEmailValid ( emile )){
                    mySql.updateCustomer ( customer.userName, name, password, emile );
                    finish ();
                }else {
                    binding.userNameEt.setText ( name1 );
                    binding.PasswordEt.setText ( password1 );
                    binding.EmileEt.setText ( emile1 );
                    new AlertDialog.Builder ( UserPageActivity.this ).setTitle ( "Fill in the data correctly" ).setMessage ( "Make sure to modify, taking into account the conditions" )
                            .setPositiveButton ( "Ok", (dialog, which) -> {
                            } ).setNegativeButton ( "Cancel", (dialog, which) -> {
                            } ).setCancelable ( false ).show ( );
                }
                return true;
            }
            return false;

        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.details_menu, menu );
        MenuItem save = menu.findItem ( R.id.details );
        MenuItem edit = menu.findItem ( R.id.edit );
        MenuItem delete = menu.findItem ( R.id.delete );
        save.setVisible ( false );
        edit.setVisible ( true );
        delete.setVisible ( false );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId ( ) == R.id.edit) {
            binding.EmileEt.setEnabled ( true );
            binding.userNameEt.setEnabled ( true );
            binding.PasswordEt.setEnabled ( true );
            Menu m = binding.toolbar.getMenu ( );
            MenuItem save = m.findItem ( R.id.details );
            MenuItem edit = m.findItem ( R.id.edit );
            MenuItem delete = m.findItem ( R.id.delete );
            edit.setVisible ( false );
            delete.setVisible ( false );
            save.setVisible ( true );
        } else if (item.getItemId ( ) == R.id.details) {
            String name = Objects.requireNonNull ( binding.userNameEt.getText ( ) ).toString ( );
            String password = Objects.requireNonNull ( binding.PasswordEt.getText ( ) ).toString ( );
            String emile = Objects.requireNonNull ( binding.EmileEt.getText ( ) ).toString ( );



        }
        return true;
    }


}