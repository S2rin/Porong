package com.goldhands.porong.activity.recommend;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.goldhands.porong.R;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Leehyebin on 2017. 10. 24..
 */

public class SpringActivity extends Activity {

    RecyclerView lecyclerView;
    TextView recotv;

    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);

        // connect to server
        ApplicationController connectController = ApplicationController.getInstance();
        connectController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        recotv = (TextView)findViewById(R.id.recotv);

        initLayout();
        initData();
    }
    /**
     * layout 초기화
     */
    private void initLayout(){
        lecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }

    /**
     * Data 초기화
     */

    private void initData(){

        recotv.setText("\"봄\"");

        final List<Recommend> recommendList = new ArrayList<Recommend>();

        try{

            Call<List<Recommend>> data = apiService.get_spring();
            data.enqueue(new Callback<List<Recommend>>() {


                @Override
                public void onResponse(Call<List<Recommend>> call, final Response<List<Recommend>> response) {
                    if(response.isSuccessful() && response.body() != null){

                        for(int i=0; i < response.body().size(); i++){

                            final Recommend recommend = new Recommend();

                            recommend.setTitle(response.body().get(i).getItem().getTitle());
                            recommend.setAddr1(response.body().get(i).getItem().getAddr1());
                            recommend.setFirstimage(response.body().get(i).getItem().getFirstimage());
                            recommend.setOverview(response.body().get(i).getItem().getOverview());

                            recommendList.add(recommend);
                        }
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

                        lecyclerView.setLayoutManager(staggeredGridLayoutManager);
                        lecyclerView.setAdapter(new RecommendAdapter(recommendList,R.layout.recommendrow,getApplicationContext()));
                        lecyclerView.setItemAnimator(new DefaultItemAnimator());
                    }else{
                        Toast.makeText(getBaseContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Recommend>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "[서버에러]데이터 로드 실패", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(getBaseContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
        }

    }

}
