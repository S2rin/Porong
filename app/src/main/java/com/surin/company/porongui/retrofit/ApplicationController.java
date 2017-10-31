package com.surin.company.porongui.retrofit;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by surin on 2017. 10. 23..
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;
    private ApiService apiService;
    private String url;

    public static ApplicationController getInstance(){
        if(instance == null){
            instance = new ApplicationController();
            return instance;
        }
        else{
            return instance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;  // 어플 최초 실행 시 인스턴스 생성
    }

    public ApiService getApiService() {return apiService;}

    public void buildApiService(){
        synchronized (ApplicationController.class){   // 스레드 동기화
            if(apiService == null){
                 IPAdrdess ipAdrdess = new IPAdrdess();
                url = String.format("http://%s:%d", ipAdrdess.ip, ipAdrdess.port);
                System.out.println("연결: http://"+ipAdrdess.ip+":"+ipAdrdess.port);

                Gson gson = new GsonBuilder().setLenient().create();

                GsonConverterFactory factory = GsonConverterFactory.create(gson);

                //Retrofit 설정
                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(factory).build();
                apiService = retrofit.create(ApiService.class);
            }
        }
    }

    public static class IPAdrdess{
        static String ip = "13.124.32.165";
        static int port = 3000;
    }
}
