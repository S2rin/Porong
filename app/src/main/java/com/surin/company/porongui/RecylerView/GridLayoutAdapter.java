package com.surin.company.porongui.RecylerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.surin.company.porongui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-09-23.
 */

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ViewHolder>{

    private ArrayList<Integer> images;
    private Context context;

    public GridLayoutAdapter (Context context, ArrayList<Integer> images){
        this.images = images;
        this.context = context;
    }

    @Override
    public GridLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridLayoutAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view){
            super(view);

            img = (ImageView) view.findViewById(R.id.basic_image);
        }
    }
}
