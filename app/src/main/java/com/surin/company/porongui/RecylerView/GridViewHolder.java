package com.surin.company.porongui.RecylerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.surin.company.porongui.R;

/**
 * Created by surin on 2017. 9. 18..
 */

public class GridViewHolder extends RecyclerView.ViewHolder{

    public TextView textViewNum;
    public TextView textViewTitle;

    public GridViewHolder(View itemView) {
        super(itemView);

        textViewNum = itemView.findViewById(R.id.textViewNum);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
    }




}
