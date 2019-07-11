package com.example.rvpagination.pagination_rv_2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rvpagination.R;

  public class MovieVH extends RecyclerView.ViewHolder {
    private TextView textView;

    public MovieVH(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.item_text);
    }
}
