package com.goldhands.porong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.goldhands.porong.R;
import com.goldhands.porong.model.User;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity_2 extends AppCompatActivity {
    private ArrayList<String> tags_ArrayList = new ArrayList<String>();
    private String[] tags = new String[5];
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9,
            checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18, checkBox19,
            checkBox20, checkBox21, checkBox22, checkBox23, checkBox24, checkBox25, checkBox26, checkBox27, checkBox28;
    private int numberofCheckBox = 0;
    private boolean[] ischecked = new boolean[28];

    Button register;

    private ApiService apiService;

    // Interface for preference data
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        // connect to server
        ApplicationController connectController = ApplicationController.getInstance();
        connectController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkbox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkbox8);
        checkBox9 = (CheckBox) findViewById(R.id.checkbox9);
        checkBox10 = (CheckBox) findViewById(R.id.checkbox10);
        checkBox11 = (CheckBox) findViewById(R.id.checkbox11);
        checkBox12 = (CheckBox) findViewById(R.id.checkbox12);
        checkBox13 = (CheckBox) findViewById(R.id.checkbox13);
        checkBox14 = (CheckBox) findViewById(R.id.checkbox14);
        checkBox15 = (CheckBox) findViewById(R.id.checkbox15);
        checkBox16 = (CheckBox) findViewById(R.id.checkbox16);
        checkBox17 = (CheckBox) findViewById(R.id.checkbox17);
        checkBox18 = (CheckBox) findViewById(R.id.checkbox18);
        checkBox19 = (CheckBox) findViewById(R.id.checkbox19);
        checkBox20 = (CheckBox) findViewById(R.id.checkbox20);
        checkBox21 = (CheckBox) findViewById(R.id.checkbox21);
        checkBox22 = (CheckBox) findViewById(R.id.checkbox22);
        checkBox23 = (CheckBox) findViewById(R.id.checkbox23);
        checkBox24 = (CheckBox) findViewById(R.id.checkbox24);
        checkBox25 = (CheckBox) findViewById(R.id.checkbox25);
        checkBox26 = (CheckBox) findViewById(R.id.checkbox26);
        checkBox27 = (CheckBox) findViewById(R.id.checkbox27);
        checkBox28 = (CheckBox) findViewById(R.id.checkbox28);

        for (int i = 0; i < 28; i++) {
            ischecked[i] = false;
        }

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < tags_ArrayList.size(); i++) {
                    tags[i] = tags_ArrayList.get(i);
                }
                sharedPreferences = getSharedPreferences("register", MODE_PRIVATE);
                User user = new User();
                user.setEmail(sharedPreferences.getString("email", null));
                user.setPassword(sharedPreferences.getString("password", null));
                user.setNickname(sharedPreferences.getString("nickname", null));
                user.setBirthyear(sharedPreferences.getString("birth", null));
                user.setTags(tags);

                Call<Void> signupCall = apiService.signup(user.getEmail(), user.getPassword(), user.getBirthyear(), user.getNickname(), user.getTags());

                signupCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "로그인 해주세요", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "회원 가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 에러 발생", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onCheckBoxClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox1:
                checkEvent(0, checkBox1);
                break;
            case R.id.checkbox2:
                checkEvent(1, checkBox2);
                break;
            case R.id.checkbox3:
                checkEvent(2, checkBox3);
                break;
            case R.id.checkbox4:
                checkEvent(3, checkBox4);
                break;
            case R.id.checkbox5:
                checkEvent(4, checkBox5);
                break;
            case R.id.checkbox6:
                checkEvent(5, checkBox6);
                break;
            case R.id.checkbox7:
                checkEvent(6, checkBox7);
                break;
            case R.id.checkbox8:
                checkEvent(7, checkBox8);
                break;
            case R.id.checkbox9:
                checkEvent(8, checkBox9);
                break;
            case R.id.checkbox10:
                checkEvent(9, checkBox10);
                break;
            case R.id.checkbox11:
                checkEvent(10, checkBox11);
                break;
            case R.id.checkbox12:
                checkEvent(11, checkBox12);
                break;
            case R.id.checkbox13:
                checkEvent(12, checkBox13);
                break;
            case R.id.checkbox14:
                checkEvent(13, checkBox14);
                break;
            case R.id.checkbox15:
                checkEvent(14, checkBox15);
                break;
            case R.id.checkbox16:
                checkEvent(15, checkBox16);
                break;
            case R.id.checkbox17:
                checkEvent(16, checkBox17);
                break;
            case R.id.checkbox18:
                checkEvent(17, checkBox18);
                break;
            case R.id.checkbox19:
                checkEvent(18, checkBox19);
                break;
            case R.id.checkbox20:
                checkEvent(19, checkBox20);
                break;
            case R.id.checkbox21:
                checkEvent(20, checkBox21);
                break;
            case R.id.checkbox22:
                checkEvent(21, checkBox22);
                break;
            case R.id.checkbox23:
                checkEvent(22, checkBox23);
                break;
            case R.id.checkbox24:
                checkEvent(23, checkBox24);
                break;
            case R.id.checkbox25:
                checkEvent(24, checkBox25);
                break;
            case R.id.checkbox26:
                checkEvent(25, checkBox26);
                break;
            case R.id.checkbox27:
                checkEvent(26, checkBox27);
                break;
            case R.id.checkbox28:
                checkEvent(27, checkBox28);
                break;

        }
    }

    public void checkEvent(int number, CheckBox checkBox) {

        //checkBox.isChecked()
        if (ischecked[number]) {
            checkBox.setChecked(false);
            ischecked[number] = false;
            --numberofCheckBox;
            for (int i = 0; i < tags_ArrayList.size(); i++) {
                if (tags_ArrayList.get(i) == checkBox.getText().toString()) {
                    tags_ArrayList.remove(i);
                }
            }
        } else {
            if (numberofCheckBox > 4) {
                Toast.makeText(getApplicationContext(), "최대 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                checkBox.setChecked(false);
                ischecked[number] = false;
            } else {
                checkBox.setChecked(true);
                ischecked[number] = true;
                tags_ArrayList.add(checkBox.getText().toString());
                numberofCheckBox++;
            }
        }
    }
}
