package com.goldhands.porong.realm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldhands.porong.R;
import com.goldhands.porong.UI.SwipeListener;

import io.realm.Realm;
import io.realm.RealmResults;

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
