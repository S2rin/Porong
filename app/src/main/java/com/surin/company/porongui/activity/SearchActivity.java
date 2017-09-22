package com.surin.company.porongui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.surin.company.porongui.R;
import com.surin.company.porongui.navigation.BottomNavigation;

/**
 * Created by surin on 2017. 9. 15..
 */

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigation bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(1);

        //NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_music:
                        intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_backup:
                        break;
                    case R.id.i_friends:
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

}
