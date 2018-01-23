package com.goldhands.porong.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adroitandroid.chipcloud.ChipCloud;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.activity.upload.UploadActivity_1;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by surin on 2017. 11. 1..
 */

public class MyPageActivity extends AppCompatActivity{
    //Header Navigation
    private DrawerLayout mDrawerLayout;

    //BottomNavigation
    private BottomNavigation bn;

    private Button edit_pro_btn;
    private TextView myal_tv, likes_tv;
    private ChipCloud chip_cloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /**
         *  header Navigation (Toolbar)
         */
        Toolbar hToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = item.getItemId();
                switch (id) {
                    default:
                        showDialog();
                        break;
                }
                return true;
            }
        });

        myal_tv = (TextView)findViewById(R.id.myal_tv);
        likes_tv = (TextView)findViewById(R.id.likes_tv);
        edit_pro_btn = (Button)findViewById(R.id.edit_pro_btn);
        chip_cloud = (ChipCloud)findViewById(R.id.chip_cloud);

        String[] stringArray = {"겨울", "백색소음", "바다"};
        new ChipCloud.Configure()
                .chipCloud(chip_cloud)
                .selectedColor(Color.parseColor("#ff00cc"))
                .selectedFontColor(Color.parseColor("#ffffff"))
                .deselectedColor(Color.parseColor("#e1e1e1"))
                .deselectedFontColor(Color.parseColor("#333333"))
                .selectTransitionMS(500)
                .deselectTransitionMS(250)
                .labels(stringArray)
                .mode(ChipCloud.Mode.MULTI)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.CENTER).build();


        myal_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyPageActivity.this, UploadListActivity.class);
                startActivity(intent2);
            }
        });

        likes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        edit_pro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        /**
         * Bottom Navigation
         */
        bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(4);

        //NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        intent  = new Intent(MyPageActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_search:
                        showDialog();
                        break;
                    case R.id.i_upload:
                        intent  = new Intent(MyPageActivity.this,UploadActivity_1.class);
                        startActivity(intent);
                        break;
                    case R.id.i_recommend:
                        intent  = new Intent(MyPageActivity.this,RecommendActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_mypage:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(MyPageActivity.this,SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.information)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setDescription(R.string.search_description)
                .setCancelable(true)
                .setPositiveText(R.string.ok)
                .withIconAnimation(false)
                .show();
    }
}
