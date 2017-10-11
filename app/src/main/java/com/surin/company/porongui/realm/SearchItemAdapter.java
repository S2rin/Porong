package com.surin.company.porongui.realm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.surin.company.porongui.R;
import com.surin.company.porongui.UI.SwipeListener;
import com.surin.company.porongui.model.SoundListItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by surin on 2017. 10. 4..
 */

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.myViewHolder> implements SwipeListener {


    private LayoutInflater inflater;
    private Realm realm;
    private RealmResults<SearchItem> results;
    private static final String TAG = "PORONG";

    public SearchItemAdapter(Context context, Realm realm, RealmResults<SearchItem> results) {
        inflater = LayoutInflater.from(context);
        this.realm = realm;
        update(results);
    }


    public void update(RealmResults<SearchItem> results){
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_search_list, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        SearchItem searchItem = results.get(position);
        holder.searchstr.setText(searchItem.getItem());
        Log.d(TAG, "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onSwipe(int position) {
        if(position < results.size()){
            realm.beginTransaction();
            results.get(position).deleteFromRealm();
            realm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView searchstr;
        public myViewHolder(View itemView) {
            super(itemView);
            searchstr = (TextView) itemView.findViewById(R.id.search_item);
        }
    }
}
