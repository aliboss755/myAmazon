package com.example.myamazon;

import android.view.View;

public interface OnItemClickListener {
    void onItemClick(View view, int position,int id );
    void onDelete(int position,int id );
}
