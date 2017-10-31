package com.surin.company.porongui.retrofit;

import com.surin.company.porongui.model.Member;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;

/**
 * Created by surin on 2017. 10. 24..
 */

public interface ApiService {
    //계정 탈퇴하기
    @DELETE("/setting//user-account/{userid}")
    Call<Member> deleteMember(@Path("userid") String userid);
}
