package com.surin.company.porongui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.surin.company.porongui.R;
import com.surin.company.porongui.adapter.SoundAdapter;
import com.surin.company.porongui.adapter.TagAdapter;
import com.surin.company.porongui.model.SoundListItem;
import com.surin.company.porongui.model.TagListItem;
import com.surin.company.porongui.UI.BottomNavigation;
import com.surin.company.porongui.UI.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    //Tag Navigation
    private DrawerLayout mDrawerLayout;

    //RecycleView
    private RecyclerView tagRecyclerView;
    private RecyclerView soundRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<TagListItem> tagListItemList;
    private List<SoundListItem> soundListItemList;

    //Image Slider
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    //PlayBar
    private SlidingUpPanelLayout mLayout;
    private TextView title, artist;
    private Button play, playlist;
    private static final String TAG = "PORONG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  header Navigation (Toolbar)
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
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
                    case R.id.navigation_item_attachment1:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_item_attachment2:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_item_attachment3:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_item_attachment4:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_item_attachment5:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                }
                return true;
            }
        });

        /**
         * Hot Tag & Hot Sound Chart (recyclerView)
         */
        tagRecyclerView = (RecyclerView) findViewById(R.id.tagRecylerView);
        tagRecyclerView.setHasFixedSize(true);

        soundRecyclerView = (RecyclerView) findViewById(R.id.soundRecylerView);
        soundRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        tagRecyclerView.setLayoutManager(layoutManager1);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false);
        soundRecyclerView.setLayoutManager(layoutManager2);

        //recylerView Default Value
        tagListItemList = new ArrayList<>();
        soundListItemList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            TagListItem listItem = new TagListItem("" + (i + 1), "Tag Desc");
            tagListItemList.add(listItem);
        }

        for (int i = 0; i < 12; i++) {
            SoundListItem listItem = new SoundListItem("" + (i + 1), "Sound Description");
            soundListItemList.add(listItem);
        }

        adapter = new TagAdapter(tagListItemList, this);
        tagRecyclerView.setAdapter(adapter);

        adapter = new SoundAdapter(soundListItemList, this);
        soundRecyclerView.setAdapter(adapter);

        /**
         * Image Slider - Hot Place()
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer  = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        /**
         * Play Bar (Sliding Up Panel)
         */
        title = (TextView) findViewById(R.id.title);
        artist = (TextView) findViewById(R.id.artist);
        play = (Button) findViewById(R.id.btn_play);
        playlist = (Button) findViewById(R.id.btn_playlist);

        //NOTE: Play시, 제목 및 타이틀 변경
        title.setText("제목은20자이내로설정해야합니다.");
        artist.setText("포롱인");
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Play the Sound
                Log.d(TAG, "onClick: play");
            }
        });
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Show recyclerView up!
                Log.d(TAG, "onClick: playlist");
            }
        });

        final List<String> array_list = Arrays.asList(
                "This", "Is", "An", "Example", "ListView", "That", "You",
                "Can", "Scroll", ".", "It", "Child", "Of", "SlidingUpPanelLayout");

        ListView lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, array_list.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, array_list );

        lv.setAdapter(arrayAdapter);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        /**
         * Bottom Navigation
         */
        BottomNavigation bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);

        //NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        break;
                    case R.id.i_search:
                        intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_upload:
                        break;
                    case R.id.i_recommend:
                        break;
                    case R.id.i_mypage:
                        break;
                }

                return true;
            }
        });

    }

    //Sliding Up Panel...
    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
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
                Toast.makeText(getApplicationContext(),"설정",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
