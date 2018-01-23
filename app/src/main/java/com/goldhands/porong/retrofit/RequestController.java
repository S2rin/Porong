package com.goldhands.porong.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.goldhands.porong.activity.InternetActivity;
import com.goldhands.porong.activity.LoginActivity;

import java.lang.reflect.Member;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by surin on 2017. 11. 1..
 */

public class RequestController {
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    public String userid;
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
        sharedPreferences = context.getSharedPreferences("LoginAccount", MODE_PRIVATE);
        userid = sharedPreferences.getString("email", null);

        Call<Member> thumbnailCall = apiService.deleteMember(userid);
        thumbnailCall.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.isSuccessful()){
                    // SharePreference 삭제
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }else {
                    Toast.makeText(context, "탈퇴 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Intent intent = new Intent(context, InternetActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
