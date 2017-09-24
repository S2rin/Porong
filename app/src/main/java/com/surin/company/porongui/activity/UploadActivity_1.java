package com.surin.company.porongui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.surin.company.porongui.R;
import com.surin.company.porongui.navigation.BottomNavigation;

import java.util.ArrayList;

/**
 * Created by HyunJu on 2017-09-20.
 * Upload View(1) - Choose one of Albums
 */

public class UploadActivity_1 extends AppCompatActivity {
    private ListView listView;
    Button button;
    TextView app_name, next;
    private ArrayAdapter<String> adapter;
    private DrawerLayout mDrawerLayout; //Tag Navigation

    private ArrayList<String> albums = new ArrayList<String>();   // 앨범 리스트뷰

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listView = (ListView) findViewById(R.id.listview);
        button = (Button) findViewById(R.id.make_new_album);

        albums.add("속초 여행");
        albums.add("베트남 여행");
        albums.add("부산 여행");
        albums.add("제주도 여행");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, albums);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Dialog();
            }
        });
    }

    private void show_Dialog() {

        LayoutInflater dialog_inflater = LayoutInflater.from(this);
        final View dialog_view = dialog_inflater.inflate(R.layout.dialog_make_album, null);


        // 새 앨범 추가를 위한 Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.make_new_album);
        builder.setView(dialog_view);
        EditText edit = (EditText) findViewById(R.id.album_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = (EditText) dialog_view.findViewById(R.id.album_title);

                try {
                    albums.add(0, edit.getText().toString());
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    showToast();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);   // Dialog 바깥 쪽을 터치 시에 없어지지 않도록 설정
        dialog.show();


        // Bottom Navigation(밑에 메뉴바)
        BottomNavigation bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);

        // NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_music:
                        break;
                    case R.id.i_backup:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.upload:
                        intent = new Intent(getApplicationContext(), UploadActivity_1.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_favor:
                        break;
                    case R.id.i_visibility:
                        break;
                }

                return true;
            }
        });
    }

    // 앨범명 입력 요청 토스트 메세지 띄우기
    public void showToast() {
        Toast.makeText(this, "앨범명을 입력해주세요", Toast.LENGTH_SHORT).show();
    }


    // 커스텀 액션바 적용하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }



    // Toolbar 메뉴 Event 메서드
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.next:
                Intent intent = new Intent(getApplicationContext(), UploadActivity_2.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }


}
