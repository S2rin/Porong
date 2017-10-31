package com.surin.company.porongui.retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.surin.company.porongui.activity.InternetActivity;
import com.surin.company.porongui.model.Member;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by surin on 2017. 10. 24..
 */

public class RequestController {

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    public String userid = "QaSg14";
    private final static String TAG = "PORONG";
    private Context context;

    public RequestController(Context context) {
        this.context = context;
    }

    public void buildRequestController(){
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();
    }

    public void deleteMember(){
        Call<Member> thumbnailCall = apiService.deleteMember(userid);
        thumbnailCall.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: 성공");
                    // SharePreference 삭제
                }else {
                    int statusCode = response.code();
                    Log.i(TAG, "onResponseFail: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                Intent intent = new Intent(context, InternetActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
