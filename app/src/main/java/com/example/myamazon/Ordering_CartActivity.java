package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.ActivityOrdringCartBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class Ordering_CartActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityOrdringCartBinding binding;
    int productId;
    int customerId;

    ItemTouchHelper itemTouchHelper;
    mySql mySql;
    ArrayList<Product> products;
    CategoryAdapter.ProductAdepter2 adepter;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        binding = ActivityOrdringCartBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        mySql = new mySql ( this );
        Intent intent = getIntent ( );
        int q=0;
        productId = intent.getIntExtra ( "productId", -1 );
        customerId = intent.getIntExtra ( "customerId", -1 );
        adepter.notifyDataSetChanged ();
        products = ( ArrayList<Product> ) mySql.getAllProductsInCart ( customerId );
        adepter.notifyDataSetChanged ();
        adepter = new CategoryAdapter.ProductAdepter2 ( products, this );
        binding.productRv.setAdapter ( adepter );
        for (Product product :products){
            q+= product.getQuantity ();
        }
        binding.quantitys.setText ( String.valueOf ( q ) );
        adepter.notifyDataSetChanged ( );
        binding.productRv.setLayoutManager ( new LinearLayoutManager ( this ) );
        binding.productRv.setHasFixedSize ( true );
        itemTouchHelper = new ItemTouchHelper ( simpleCallback );
        itemTouchHelper.attachToRecyclerView ( binding.productRv );
        int sum =0;
        for (Product product:products){
           sum+= mySql.getTotalProductCostForCustomer (customerId,product.getId ()  );
        }
        adepter.notifyDataSetChanged ();
        binding.total.setText ( String.valueOf ( sum ) );
        adepter.notifyDataSetChanged ();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClick(View view, int position, int id) {
        mySql.deleteProductCustomer ( customerId );
        products = ( ArrayList<Product> ) mySql.getProductsByCustomer2 ( customerId );
        adepter.notifyDataSetChanged ( );
        products = ( ArrayList<Product> ) mySql.getProductsByCustomer2 ( customerId );
        adepter.setProducts ( products );
        adepter.notifyDataSetChanged ( );
    }

    @Override
    public void onDelete(int position, int id) {

    }

    String deleteMovie = null;
    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback ( 0, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition ( );
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deleteMovie = String.valueOf ( products.get ( position ) );
                    products.remove ( position );
                    mySql.deleteFromCart ( customerId,productId);
                    adepter.notifyItemRemoved ( position );
                    adepter.notifyDataSetChanged ();
                    Snackbar.make ( binding.productRv, "Delete Recycler" + (position + 1), Snackbar.LENGTH_LONG )
                            .setAction ( "Undo", v -> {
                                products.add ( position, products.get ( position ) );
                                adepter.notifyItemInserted ( position );
                            } ).show ( );
                    break;
                case ItemTouchHelper.RIGHT:
//                    Student2 productName=student2s.get(position);
//                    student2List.add(productName);
//                    student2s.remove(position);
//                    adapter.notifyItemRemoved(position);
//                    Snackbar.make(binding.studentRv,"Archive Now"+(position+1),Snackbar.LENGTH_LONG)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }).show();
                    break;

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive )
                    .addSwipeLeftBackgroundColor ( Color.RED )
                    .addSwipeLeftActionIcon ( R.drawable.ic_baseline_delete_24 )
//                    .addSwipeRightBackgroundColor(Color.GREEN)
//                    .addSwipeRightActionIcon(R.drawable.ic_star_24)
//                    .addBackgroundColor(ContextCompat.getColor(Student_Activity.this, R.color.my_background))
//                    .addActionIcon(R.drawable.my_icon)
                    .create ( )
                    .decorate ( );
            super.onChildDraw ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );

        }
    };

}