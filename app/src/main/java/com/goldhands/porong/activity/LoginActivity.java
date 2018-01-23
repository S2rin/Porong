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
import android.widget.TextView;
import android.widget.Toast;

import com.goldhands.porong.R;
import com.goldhands.porong.model.User;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    Button login, signup;
    EditText email, password;

    private ApiService apiService;

    // Interface for preference data
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;   // Editor of preference data

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect to server
        ApplicationController connectController = ApplicationController.getInstance();
        connectController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        final Intent goMain = new Intent(getApplicationContext(), MainActivity.class);

        sharedPreferences = getSharedPreferences("LoginAccount", MODE_PRIVATE);
        String loginCheck = sharedPreferences.getString("email", null);

        if (loginCheck != null) {
            finish();
            startActivity(goMain);
        }

        //회원 가입 버튼 설정
        signup = (Button) findViewById(R.id.button_signup);
        login = (Button) findViewById(R.id.button_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit1 = email.getText().toString();
                String edit2 = password.getText().toString();
                if (edit1.equals("") || edit2.equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일과 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    Call<List<User>> loginCall = apiService.login(user.getEmail(), user.getPassword());
                    loginCall.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                            if (response.isSuccessful()) {
                                try{
                                    if(response.body().get(0).getCnt() == 1){
                                        sharedPreferences = getSharedPreferences("LoginAccount", MODE_PRIVATE);
                                        editor = sharedPreferences.edit();
                                        editor.putString("email", email.getText().toString());
                                        editor.putString("nickname", response.body().get(0).getNickname());
                                        editor.commit();

                                        Toast.makeText(getApplicationContext(), "로그인 성공 ", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(goMain);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "계정이 없습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 실패. 회원가입 하셨나요?", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

}
