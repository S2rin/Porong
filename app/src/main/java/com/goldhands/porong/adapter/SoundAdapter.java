package com.goldhands.porong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldhands.porong.R;
import com.goldhands.porong.model.SoundListItem;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<GridViewHolder>{
    private List<SoundListItem> listItems;
    private Context context;
    private int selectedPosition = -1;
    private int selectedPosition2 = -2;
    private final static String TAG = "PORONG";

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
    public void onBindViewHolder(final GridViewHolder holder, int position) {

        SoundListItem soundListItem = listItems.get(position);

        holder.textViewNum.setText(soundListItem.getNickname());
        holder.textViewTitle.setText(soundListItem.getSound_title());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });


        if(selectedPosition == selectedPosition2){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            if (selectedPosition == holder.getAdapterPosition()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#D5D5D5"));
                selectedPosition2 = selectedPosition;
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
