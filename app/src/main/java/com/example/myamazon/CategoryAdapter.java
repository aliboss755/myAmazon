package com.example.myamazon;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.CustomCardBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    List<Category> data;
    Context context;
    int selectedItem=0;

    public CategoryAdapter(List<Category> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.category_home,parent,false);
        return new CategoryHolder(view);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        holder.image.setImageResource(data.get(position).getImage());
        holder.title.setText(data.get(position).getName());
        if (position==selectedItem)
        {
            //Make car Selector
            holder.cardView.setOutlineSpotShadowColor (context.getColor ( R.color.red ));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.red));
            holder.title.setTextColor(context.getColor(R.color.red));
            holder.image.setColorFilter(R.color.red);
            holder.cardView.setStrokeWidth(5);
            holder.image.setColorFilter( ContextCompat.getColor(context,R.color.red), PorterDuff.Mode.SRC_IN);

        }else
        {
            //Make Card inactive
            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.gray_black));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.gray_black));
            holder.title.setTextColor(context.getColor(R.color.gray_black));
            holder.image.setColorFilter(ContextCompat.getColor(context,R.color.gray_black), PorterDuff.Mode.SRC_IN);
            holder.cardView.setStrokeWidth(0);

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        MaterialCardView cardView;
        public CategoryHolder(View holder){
            super(holder);
            title=holder.findViewById(R.id.txt_title);
            image=holder.findViewById(R.id.img);
            cardView=holder.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem=getAdapterPosition();
                    //rest items, so that color changes when click on card
                    notifyDataSetChanged();

                }
            });
        }

    }

    static class ProductAdepter2 extends RecyclerView.Adapter<ProductAdepter2.ProductViewHolder> {
        public static List<Product> products;
        private OnItemClickListener listener;

        public ProductAdepter2(List<Product> products, OnItemClickListener listener) {
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
            CustomCardBinding binding = CustomCardBinding.inflate ( LayoutInflater.from ( parent.getContext ( ) ),parent,false );
            return new ProductViewHolder ( binding );
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            int index =position;
            Product p = products.get ( index );
            holder.title.setText ( p.getName ( )
            );
            holder.price.setText ( "$" + p.getPrice ( ) );
            try {
                if (p.getImage()!=null&&!p.getImage ().equals ( "" )){
//                    holder.image.setImageURI ( Uri.parse ( p.getImage () ) );
                }else {
                    holder.image.setImageResource ( R.drawable.burger_two );
                }
            }catch (Exception e ){
                holder.image.setImageResource ( R.drawable.pizza_2 );

            }

            holder.image.setOnClickListener ( v -> {
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
            TextView title, price;
            ImageView image;
            public ProductViewHolder(@NonNull CustomCardBinding binding) {
                super ( binding.getRoot ( ) );
                title = binding.productName;
                price = binding.productPrice;
                image = binding.productImage;

            }
        }


    }
}
