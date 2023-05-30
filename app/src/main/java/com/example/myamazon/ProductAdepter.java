package com.example.myamazon;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.CustomProdectBinding;

import java.util.List;

public class ProductAdepter extends RecyclerView.Adapter<ProductAdepter.ProductViewHolder> {
    public static List<Product> products;
    private OnItemClickListener listener;

    public ProductAdepter(List<Product> products, OnItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomProdectBinding binding = CustomProdectBinding.inflate ( LayoutInflater.from ( parent.getContext ( ) ),parent,false );
        return new ProductViewHolder ( binding );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int index =position;
        Product p = products.get ( index );
        holder.title.setText ( p.getName ( )
        );
        holder.description.setText ( p.getDescription ( ) );
        holder.price.setText ( "$" + p.getPrice ( ) );
        try {
            if (p.getImage()!=null||!p.getImage ().equals ( "" )){
                holder.image.setImageURI ( Uri.parse ( p.getImage () ) );
            }else {
                holder.image.setImageResource ( R.drawable.burger_two );
            }
        }catch (Exception e ){
            holder.image.setImageResource ( R.drawable.pizza_2 );

        }

        holder.itemView.setOnClickListener ( v -> {
            if (listener != null) {
                listener.onItemClick ( v,index , p.getId ());
            }
        } );
    }
    @Override
    public int getItemCount() {
        return products.size ( );
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, description;
        ImageView image;
        public ProductViewHolder(@NonNull CustomProdectBinding binding) {
            super ( binding.getRoot ( ) );
            title = binding.ProductTitle;
            price = binding.ProductPrice;
            image = binding.imageView;
            description = binding.ProductDescription;

        }
    }


}