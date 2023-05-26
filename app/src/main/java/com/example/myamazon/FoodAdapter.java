package com.example.myamazon;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    List<FoodItem> data;
    int selectedItem = 0;

    public FoodAdapter(List<FoodItem> data){
        this.data = data;
    }
    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.food_holder, parent, false);

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.price.setText(String.format("$%d",data.get(position).getPrice()));
        holder.image.setImageResource(data.get(position).getImage());
        holder.title.setText(data.get(position).getName());
        holder.ratingBar.setRating(data.get(position).getRating());

        if (selectedItem == position){
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.1f);
            holder.title.setTextColor(Color.WHITE);
            holder.price.setTextColor(Color.WHITE);
            holder.llBackground.setBackgroundResource(R.drawable.splash);
        }else {
            holder.cardView.animate().scaleX(1f);
            holder.cardView.animate().scaleY(1f);
            holder.title.setTextColor(Color.BLACK);
            holder.price.setTextColor(Color.BLACK);
            holder.llBackground.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        ImageView image;
        TextView title;
        TextView price;
        LinearLayout llBackground;
        CardView cardView;
        public FoodHolder(View holder) {
            super(holder);
            ratingBar = holder.findViewById(R.id.rating);
            title = holder.findViewById(R.id.food_title);
            image = holder.findViewById(R.id.food_img);
            price = holder.findViewById(R.id.txt_price);
            cardView = holder.findViewById(R.id.food_background);
            llBackground = holder.findViewById(R.id.ll_background);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
