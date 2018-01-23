package com.goldhands.porong.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.UI.PlayBottomNavigation;
import com.goldhands.porong.activity.upload.UploadActivity_1;
import com.goldhands.porong.adapter.SoundAdapter;
import com.goldhands.porong.adapter.TagAdapter;
import com.goldhands.porong.adapter.ViewPagerAdapter;
import com.goldhands.porong.model.SoundListItem;
import com.goldhands.porong.model.TagListItem;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Tag Navigation
    private DrawerLayout mDrawerLayout;
    private MenuItem mPreviousMenuItem;

    //RecycleView
    private RecyclerView tagRecyclerView;
    private RecyclerView soundRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<TagListItem> tagListItemList;
    private List<SoundListItem> soundListItemList;
    private int oldPos = -1;

    //Button (View more)
    private Button tagMore, chartMore;

    //Image Slider
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    //MusicPlayer
    private MediaPlayer mp;
    private int pos;
    private int clickSoundPosition;
    private boolean restart = false;

    //PlayBar
    private SlidingUpPanelLayout mLayout;
    private TextView title, artist;
    private String strTitle;
    private Button play, stop, playlist;
    private int toggle = 0;
    public enum Type {PLAY, PAUSE, STOP, REPLAY}
    private Type isPlaying = Type.STOP;
    private static final String TAG = "TEST";

    //Retrofit
    private ApiService apiService;
    private String userid = "QaSg14";

    //BottomNavigation
    private BottomNavigation bn;
    private PlayBottomNavigation playbn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        /**
         * Retrofit Setting
         */
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        /**
         * Image Slider - Hot Place
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         *  header Navigation (Toolbar)
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setCheckable(true);
                item.setChecked(true);
                if(mPreviousMenuItem != null){
                    mPreviousMenuItem.setChecked(false);
                }
                mPreviousMenuItem = item;
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
        String[] tagArray = {"악기","비","바다","동물","바람","봄"};
        final String[] soundArray = {"비오는 소리","낙원 피아노 연주","해운대 파도 소리","카페에서 나오는 캐롤","유럽 아침 시장","캠프 파이어","바람","종소리",
                "아침 새 소리", "가로수 길 걷는 길", "라이브 바 재즈 연주", "밤에 들리는 부엉이 소리"};
        tagListItemList = new ArrayList<>();
        soundListItemList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            TagListItem listItem = new TagListItem("" + (i + 1), tagArray[i]);
            tagListItemList.add(listItem);
        }

        for (int i = 0; i < 12; i++) {
            SoundListItem listItem = new SoundListItem("" + (i + 1), soundArray[i]);
            soundListItemList.add(listItem);
        }

        adapter = new TagAdapter(tagListItemList, this);
        tagRecyclerView.setAdapter(adapter);

        adapter = new SoundAdapter(soundListItemList, this);
        soundRecyclerView.setAdapter(adapter);

        final GestureDetector gestureDetector = new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        soundRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child!=null&&gestureDetector.onTouchEvent(e)) {
                    int position = rv.findViewHolderForAdapterPosition(rv.getChildLayoutPosition(child)).getAdapterPosition();
                    if(position == oldPos){
                        bnVisible();
                        oldPos = -2;
                    }else{
                        playbnVisible();
                        oldPos = position;
                    }

                    strTitle = soundListItemList.get(rv.getChildLayoutPosition(child)).getSound_title();
                    clickSoundPosition = Integer.parseInt(soundListItemList.get(rv.getChildLayoutPosition(child)).getNickname());

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        /**
         * Button - View more
         */
        tagMore = (Button) findViewById(R.id.view_more1);
        chartMore = (Button) findViewById(R.id.view_more2);
        tagMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        chartMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 4000, 4000);

        /**
         * Play Bar (Sliding Up Panel)
         */
        title = (TextView) findViewById(R.id.title);
        artist = (TextView) findViewById(R.id.artist);
        play = (Button) findViewById(R.id.btn_play);
        stop = (Button) findViewById(R.id.btn_stop);
        playlist = (Button) findViewById(R.id.btn_playlist);

        //NOTE: Play시, 제목 및 타이틀 변경
        title.setText("사운드를 선택해주세요");
        artist.setText("포롱인");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Play the Sound
                Boolean b = checkSound();
                if(b){
                    if(restart){
                        isPlaying = Type.REPLAY;
                        toggle = 0;
                        restart = false;
                        checkState();
                    }else{
                        if(toggle == 0){
                            isPlaying = Type.PAUSE;
                            toggle = 1;
                            restart = true;
                            checkState();
                        }else{
                            isPlaying = Type.PLAY;
                            toggle = 0;
                            checkState();
                        }
                    }
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean b = checkSound();
                if(b){
                    isPlaying = Type.STOP;
                    toggle = 1;
                    restart = false;
                    checkState();
                }
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
                this, android.R.layout.simple_list_item_1, array_list);

        lv.setAdapter(arrayAdapter);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setTouchEnabled(false);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        /**
         * Bottom Navigation
         */
        bn = new BottomNavigation();
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
                        showDialog();
                        bn.bnve.setCurrentItem(0);
                        break;
                    case R.id.i_upload:
                        intent = new Intent(MainActivity.this,UploadActivity_1.class);
                        startActivity(intent);
                        break;
                    case R.id.i_recommend:
                        intent  = new Intent(MainActivity.this,RecommendActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_mypage:
                        intent = new Intent(MainActivity.this,MyPageActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return true;
            }
        });

        /**
         * Play Bottom Navigation
         */
        playbn = new PlayBottomNavigation();
        playbn.play_bnve = (BottomNavigationViewEx) findViewById(R.id.play_navigation);
        playbn.navigationInit(playbn.play_bnve);

        playbn.play_bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.i_play:
                        isPlaying = Type.PLAY;
                        restart = false;
                        toggle = 0;
                        checkState();

                        bnVisible();
                        title.setText(strTitle);
                        break;

                    case R.id.i_addList:
                        showDialog();
                        bnVisible();
                        break;
                }
                return true;
            }
        });

    }

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

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp!=null) {
            mp.release(); // 자원해제
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp!=null) {
            mp.release(); // 자원해제
        }
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    }else if(viewPager.getCurrentItem() ==3){
                        viewPager.setCurrentItem(4);
                    }else if(viewPager.getCurrentItem() == 4){
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
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

    public void playbnVisible(){
        bn.getBnve().setVisibility(View.INVISIBLE);
        playbn.getPlay_bnve().setVisibility(View.VISIBLE);
    }

    public void bnVisible(){
        bn.getBnve().setVisibility(View.VISIBLE);
        playbn.getPlay_bnve().setVisibility(View.INVISIBLE);
    }

    public boolean checkSound(){
        String str = title.getText().toString();
        if(str.equals("사운드를 선택해주세요")){
            Toast.makeText(getApplicationContext(),"사운드를 선택 후 듣기를 눌러주세요!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void playSound(){
        //PLAY
        switch (clickSoundPosition){
            case 1 :
                mp = MediaPlayer.create(
                        getApplicationContext(), // 현재 화면의 제어권자
                        R.raw.sound_rain);
                break;
            case 2 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_piano);
                break;
            case 3 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_sea);
                break;
            case 4 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_carol);
                break;
            case 5 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_market);
                break;
            case 6 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_camp);
                break;
            case 7 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_wind);
                break;
            case 8 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_bell);
                break;
            case 9 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_morning);
                break;
            case 10 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_walk);
                break;
            case 11 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_jazz);
                break;
            case 12 :
                mp = MediaPlayer.create(
                        getApplicationContext(), R.raw.sound_night);
                break;
            default:
                break;
        }
        mp.setLooping(false); // true:무한반복
        mp.start(); // 노래 재생 시작
    }

    public void pauseSound(){
        pos = mp.getCurrentPosition();
        mp.pause(); // 일시중지
    }

    public void stopSound(){
        mp.stop(); // 멈춤
        mp.release(); // 자원 해제
    }

    public void restartSound(){
        mp.seekTo(pos); // 일시정지 시점으로 이동
        mp.start(); // 시작
    }

    public void checkState(){
        switch (isPlaying){
            case PLAY:
                play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                playSound();

                //End of Sound
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        isPlaying = Type.STOP;
                        restart = false;
                        toggle = 1;
                        checkState();
                    }
                });
                break;
            case PAUSE:
                play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                pauseSound();
                break;
            case STOP:
                play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                stopSound();
                break;
            case REPLAY:
                play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                restartSound();
                break;
        }
    }
}
