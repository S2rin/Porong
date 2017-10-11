package com.surin.company.porongui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.surin.company.porongui.R;
import com.surin.company.porongui.model.TagListItem;

import java.util.List;

/**
 * Created by surin on 2017. 9. 18..
 */

public class TagAdapter extends RecyclerView.Adapter<GridViewHolder>{

    private List<TagListItem> listItems;
    private Context context;

    public TagAdapter(List<TagListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item,parent,false);

        return new GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        TagListItem tagListItem = listItems.get(position);
        holder.textViewNum.setText(tagListItem.getNum());
        holder.textViewTitle.setText(tagListItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
