package com.goldhands.porong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goldhands.porong.R;

public class GridViewHolder  extends RecyclerView.ViewHolder{
    public TextView textViewNum;
    public TextView textViewTitle;

    public GridViewHolder(View itemView) {
        super(itemView);

        textViewNum = itemView.findViewById(R.id.textViewNum);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
    }
}
