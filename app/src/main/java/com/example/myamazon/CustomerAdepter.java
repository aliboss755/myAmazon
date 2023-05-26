package com.example.myamazon;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myamazon.databinding.CustomcustomerBinding;

import java.util.ArrayList;

public class CustomerAdepter extends RecyclerView.Adapter <CustomerAdepter.CustomerViewHelper>{
    ArrayList<Customer> customers ;
    OnItemClickListener  listener;
    int position1;

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public CustomerAdepter(ArrayList<Customer> customers, OnItemClickListener listener) {
        this.customers = customers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomcustomerBinding binding =CustomcustomerBinding.inflate ( LayoutInflater.from ( parent.getContext () ));
        return new CustomerViewHelper ( binding );
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHelper holder, @SuppressLint("RecyclerView") int position) {
        position1 =position;

        Customer c =customers.get ( position );
        holder.password.setText ( c.getPassword () );
        holder.userName.setText ( c.getUserName () );
        holder.emile.setText ( c.getEmail () );
        holder.userImage.setImageResource ( R.drawable.img_2 );
        if (null != c.getImage ( ))
        holder.userImage.setImageURI ( Uri.parse ( c.getImage () ) );
        holder.itemView.setOnClickListener ( v -> {
            if (listener != null) {
//                int position1 =  holder.getAdapterPosition ();
                listener.onItemClick ( v, position1,position);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return customers.size ();
    }

    static class CustomerViewHelper extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName , emile,password;

        public CustomerViewHelper(@NonNull CustomcustomerBinding binding ) {
            super ( binding.getRoot () );
            userImage=binding.userImage;
            userName=binding.userNameTv;
            emile=binding.emileTv;
            password=binding.PasswordTv;




        }
    }
}
