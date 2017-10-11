package com.surin.company.porongui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.surin.company.porongui.R;
import com.surin.company.porongui.model.SoundListItem;

import java.util.List;

/**
 * Created by surin on 2017. 9. 18..
 */

public class SoundAdapter extends RecyclerView.Adapter<GridViewHolder>{

    private List<SoundListItem> listItems;
    private Context context;

    public SoundAdapter(List<SoundListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);

        return new GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {

        SoundListItem soundListItem = listItems.get(position);

        holder.textViewNum.setText(soundListItem.getNum());
        holder.textViewTitle.setText(soundListItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
