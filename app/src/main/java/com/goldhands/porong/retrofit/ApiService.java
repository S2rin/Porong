package com.goldhands.porong.retrofit;

import com.goldhands.porong.activity.recommend.Recommend;
import com.goldhands.porong.model.SoundListItem;
import com.goldhands.porong.model.User;

import java.lang.reflect.Member;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("/sound/info")
    Call<List<SoundListItem>> getUserUpload(@Field("nickname") String nickname);

    //계정 탈퇴하기
    @DELETE("/setting/user-account/{userid}")
    Call<Member> deleteMember(@Path("userid") String userid);

    @FormUrlEncoded
    @POST("/porong/login")
    Call<List<User>> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/porong/check/nickname")
    Call<List<User>> checkNickname(@Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("/porong/signup")
    Call<Void> signup(@Field("email") String email, @Field("password") String password, @Field("birth") String birthyear, @Field("nickname") String nickname, @Field("tags") String[] tags);

    @Multipart
    @POST("/sound/upload")
    Call<Void> upload(@Part MultipartBody.Part imageFile, @Part MultipartBody.Part audioFile, @Part("sound_title") String sound_title, @Part("sound_album") String sound_album, @Part("nickname") String nickname, @Part("tags") String[] tags);

    @FormUrlEncoded
    @POST("/porong/check/email")
    Call<List<User>> checkEmail(@Field("email") String email);


    // 봄 태그 데이터 가져오기
    @GET("/recommend/spring")
    Call<List<Recommend>> get_spring();

    // 여름 태그 데이터 가져오기
    @GET("/recommend/summer")
    Call<List<Recommend>> get_summer();

    //가을 태그 가져오기
    @GET("/recommend/fall")
    Call<List<Recommend>> get_fall();

    //겨울 태그 가져오기
    @GET("/recommend/winter")
    Call<List<Recommend>> get_winter();

    //산 태그 가져오기
    @GET("/recommend/mountain")
    Call<List<Recommend>> get_mountain();

    //계곡 태그 가져오기
    @GET("/recommend/valley")
    Call<List<Recommend>> get_valley();

    //바다 태그 가져오기
    @GET("/recommend/sea")
    Call<List<Recommend>> get_sea();

    //숲 태그 가져오기
    @GET("/recommend/forest")
    Call<List<Recommend>> get_forest();

    //도시 태그 가져오기
    @GET("/recommend/city")
    Call<List<Recommend>> get_city();

    //전통적인 태그 가져오기
    @GET("/recommend/traditional")
    Call<List<Recommend>> get_traditional();

    //캠핑 태그 가져오기
    @GET("/recommend/camping")
    Call<List<Recommend>> get_camping();

}
