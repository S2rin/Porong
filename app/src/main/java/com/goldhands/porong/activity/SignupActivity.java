package com.goldhands.porong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goldhands.porong.R;
import com.goldhands.porong.model.User;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private Button checkNickname, checkEmail, gotoNext;
    private ApiService apiService;

    // Interface for preference data
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;   // Editor of preference data

    EditText edit_email, edit_password, edit_birth;
    MaterialEditText edit_nickname;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_nickname = (MaterialEditText) findViewById(R.id.edit_nickname);
        edit_birth = (EditText) findViewById(R.id.edit_birth);
        checkNickname = (Button) findViewById(R.id.checkNickname);
        gotoNext = (Button) findViewById(R.id.gotoNext);
        checkEmail = (Button) findViewById(R.id.checkEmail);

        // connect to server
        ApplicationController connectController = ApplicationController.getInstance();
        connectController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        String nickname = edit_nickname.getText().toString();

        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = edit_email.getText().toString();
                    if (email.equals("")) {
                        Toast.makeText(getApplicationContext(), "이메일을 먼저 입력하세요", Toast.LENGTH_SHORT).show();
                    } else {
                        Call<List<User>> checkEmailCall = apiService.checkEmail(email);
                        checkEmailCall.enqueue(new Callback<List<User>>() {
                            @Override
                            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().get(0).getCnt() == 1) {
                                        Toast.makeText(getApplicationContext(), "이미 해당 이메일로 가입되어 있는 계정이 존재합니다. 다른 이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "해당 이메일로 가입이 가능합니다", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<List<User>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "예상치 못한 예외 발생", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                        String nickname = edit_nickname.getText().toString();
                        if (nickname.equals("")) {
                            Toast.makeText(getApplicationContext(), "닉네임을 먼저 입력하세요", Toast.LENGTH_SHORT).show();
                        } else {
                        Call<List<User>> checkNicknameCall = apiService.checkNickname(nickname);

                        checkNicknameCall.enqueue(new Callback<List<User>>() {
                            @Override
                            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                if (response.isSuccessful()) {
                                    try{
                                        if(response.body().get(0).getCnt() == 1){
                                            Toast.makeText(getApplicationContext(), "이미 사용 중인 닉네임 입니다 " + sharedPreferences.getString("email", null), Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "사용할 수 있는 닉네임 입니다", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<User>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "서버 에러 발생", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "예상치 못한 예외 발생", Toast.LENGTH_SHORT).show();
                }
            }
        });


        gotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                String birth = edit_birth.getText().toString();
                String nickname = edit_nickname.getText().toString();

                if(email.equals("") || password.equals("") || birth.equals("") || nickname.equals("")){
                    Toast.makeText(getApplicationContext(), "정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    sharedPreferences = getSharedPreferences("register", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putString("nickname", nickname);
                    editor.putString("birth", birth);
                    editor.commit();

                    Intent goNext = new Intent(getApplicationContext(), SignupActivity_2.class);
                    startActivity(goNext);
                }
            }
        });
    }
}
