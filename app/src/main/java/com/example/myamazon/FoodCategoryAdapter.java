//package com.example.myamazon;
//
//import android.content.Context;
//import android.graphics.PorterDuff;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.card.MaterialCardView;
//
//import java.util.List;
//
//public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.CategoryHolder> {
//
//    List<FoodCategory> data;
//    Context context;
//    int selectedItem = 0;
//
//    OnCategoryClick onCategoryClick;
//
//    public interface OnCategoryClick {
//        void onClick(int pos);
//    }
//    public FoodCategoryAdapter(List<FoodCategory> data, Context context, OnCategoryClick onCategoryClick) {
//        this.data = data;
//        this.context = context;
//        this.onCategoryClick = onCategoryClick;
//    }
//
//    @NonNull
//    @Override
//    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.category_holder,parent,false);
//        return new CategoryHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
//        holder.image.setImageResource(data.get(position).getImage());
//        holder.title.setText(data.get(position).getName());
//        if (position == selectedItem){
//            // Make card selected
//            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.red));
//            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.red));
//            holder.cardView.setStrokeWidth(2);
//            holder.title.setTextColor(context.getColor(R.color.red));
//            holder.image.setColorFilter(ContextCompat.getColor(context,R.color.red), PorterDuff.Mode.SRC_IN);
//        }else {
//            // Make card inactive
//            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.gray1));
//            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.gray));
//            holder.title.setTextColor(context.getColor(R.color.gray));
//            holder.image.setColorFilter(ContextCompat.getColor(context,R.color.gray1), PorterDuff.Mode.SRC_IN);
//            holder.cardView.setStrokeWidth(0);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    class CategoryHolder extends RecyclerView.ViewHolder{
//        TextView title;
//        ImageView image;
//        MaterialCardView cardView;
//        public CategoryHolder(View holder){
//            super(holder);
//            title = holder.findViewById(R.id.txt_title);
//            image = holder.findViewById(R.id.img);
//            cardView = holder.findViewById(R.id.card_view);
//
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    selectedItem = getAdapterPosition();
//                    //reset items, so that color changes when we click on card
//                    if (onCategoryClick != null){
//                        onCategoryClick.onClick(getAdapterPosition());
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//        }
//    }
//}
