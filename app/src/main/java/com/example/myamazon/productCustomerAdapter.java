package com.example.myamazon;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.CustomCardBinding;

import java.util.ArrayList;

public class productCustomerAdapter extends RecyclerView.Adapter<productCustomerAdapter.productViewHolder>{
    ArrayList<Product> products ;
    OnItemClickListener listener ;

    public productCustomerAdapter(ArrayList<Product> products, OnItemClickListener listener) {
        this.products = products;
        listener=listener;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomCardBinding binding = CustomCardBinding.inflate ( LayoutInflater.from ( parent.getContext () ),parent,false );
        return new productViewHolder ( binding );
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        int index =position;
        Product product =products.get ( index );
//        holder.productImage.setImageURI ( Uri.parse ( product.getImage () ) );
        holder.price.setText ( String.valueOf ( product.getPrice () ) );
        holder.title.setText ( product.getName () );
        holder.quantity.setText ( String.valueOf ( product.getPrice ()*product.getQuantity () ) );
        holder.deleteImage.setImageResource ( R.drawable.ic_baseline_delete_24 );
        holder.deleteImage.setOnClickListener ( v -> listener.onDelete ( index,product.getId () ) );

    }

    @Override
    public int getItemCount() {
        return products.size ();
    }

    class productViewHolder extends RecyclerView.ViewHolder {
        TextView title,quantity,price;
        ImageView productImage,deleteImage;
        public productViewHolder(CustomCardBinding binding) {
            super ( binding.getRoot () );
            title=binding.productName;
            quantity=binding.productQuantities;
            price=binding.productPrice;
            productImage=binding.productImage;

        }
    }

}
