package com.goldhands.porong.activity.recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldhands.porong.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private List<Recommend> recommendList;
    private int itemLayout;
    private Context context;

    /**
     * 생성자
     * @param items
     * @param itemLayout
     */

    public RecommendAdapter(List<Recommend> items , int itemLayout, Context context){

        this.recommendList = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }


    /**
     * 레이아웃을 만들어서 Holer에 저장
     * @param viewGroup
     * @param viewType
     * @return
     */


    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new RecommendAdapter.ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */


    @Override
    public void onBindViewHolder(RecommendAdapter.ViewHolder viewHolder, int position) {

        Recommend item = recommendList.get(position);

        viewHolder.textTitle.setText(item.getTitle());
        viewHolder.textAddr.setText(item.getAddr1());
        viewHolder.textOverview.setText(item.getOverview());

        //viewHolder.img.setImageBitmap(item.getBitmap());

        Picasso.with(context)
                .load(item.getFirstimage())
                .into(viewHolder.img);

        viewHolder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }


    /**
     * 뷰 재활용을 위한 viewHolder
     */


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView textTitle, textAddr, textOverview;

        public ViewHolder(View itemView){
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textAddr = (TextView) itemView.findViewById(R.id.textAddr);
            textOverview = (TextView) itemView.findViewById(R.id.textOverview);
        }

    }

}
