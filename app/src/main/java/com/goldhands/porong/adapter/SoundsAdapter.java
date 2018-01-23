package com.goldhands.porong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldhands.porong.R;
import com.goldhands.porong.model.SoundListItem;

import java.util.List;

public class SoundsAdapter extends RecyclerView.Adapter<SoundsAdapter.myViewHolder>{

    private List<SoundListItem> listItems;
    private Context context;

    public SoundsAdapter(List<SoundListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_single_layout,parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        SoundListItem sounds = listItems.get(position);

        holder.title.setText(sounds.getSound_title());
        holder.artist.setText(sounds.getNickname());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView artist;

        public myViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.sound_title);
            artist = (TextView) itemView.findViewById(R.id.sound_artist);
        }
    }
}
