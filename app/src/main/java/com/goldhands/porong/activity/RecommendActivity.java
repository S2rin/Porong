package com.goldhands.porong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.activity.recommend.CityActivity;
import com.goldhands.porong.activity.recommend.FallActivity;
import com.goldhands.porong.activity.recommend.ForestActivity;
import com.goldhands.porong.activity.recommend.MountainActivity;
import com.goldhands.porong.activity.recommend.SeaActivity;
import com.goldhands.porong.activity.recommend.SpringActivity;
import com.goldhands.porong.activity.recommend.SummerActivity;
import com.goldhands.porong.activity.recommend.TradiActivity;
import com.goldhands.porong.activity.recommend.ValleyActivity;
import com.goldhands.porong.activity.recommend.WinterActivity;
import com.goldhands.porong.activity.upload.UploadActivity_1;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class RecommendActivity extends AppCompatActivity{
    RecyclerView.LayoutManager layoutManager;
    RecyclerView lecyclerView;

    //Header Navigation
    private DrawerLayout mDrawerLayout;

    //BottomNavigation
    private BottomNavigation bn;

    //buttons
    Button btnspring, btnsummer, btnfall, btnwinter, btnmountain, btnvalley, btnsea, btnforest, btncity, btntra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        btnspring = (Button)findViewById(R.id.btnspring);
        btnsummer = (Button)findViewById(R.id.btnsummer);
        btnfall = (Button)findViewById(R.id.btnfall);
        btnwinter = (Button)findViewById(R.id.btnwinter);
        btnmountain = (Button)findViewById(R.id.btnmountain);
        btnvalley = (Button)findViewById(R.id.btnvalley);
        btnsea = (Button)findViewById(R.id.btnsea);
        btnforest = (Button)findViewById(R.id.btnforest);
        btncity = (Button)findViewById(R.id.btncity);
        btntra = (Button)findViewById(R.id.btntra);

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

        /**
         * Bottom Navigation
         */
        bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(3);

        //NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        intent  = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_search:
                        showDialog();
                        bn.bnve.setCurrentItem(3);
                        break;
                    case R.id.i_upload:
                        intent  = new Intent(getApplicationContext(),UploadActivity_1.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_recommend:
                        break;
                    case R.id.i_mypage:
                        intent  = new Intent(getApplicationContext(),MyPageActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return true;
            }
        });

        btnspring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), SpringActivity.class);
                startActivity(recommend);
            }
        });
        btnsummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), SummerActivity.class);
                startActivity(recommend);
            }
        });
        btnfall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), FallActivity.class);
                startActivity(recommend);
            }
        });
        btnwinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), WinterActivity.class);
                startActivity(recommend);
            }
        });
        btnmountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), MountainActivity.class);
                startActivity(recommend);
            }
        });
        btnvalley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), ValleyActivity.class);
                startActivity(recommend);
            }
        });
        btnsea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), SeaActivity.class);
                startActivity(recommend);
            }
        });
        btnforest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), ForestActivity.class);
                startActivity(recommend);
            }
        });
        btncity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), CityActivity.class);
                startActivity(recommend);
            }
        });
        btntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(getApplicationContext(), TradiActivity.class);
                startActivity(recommend);
            }
        });

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
