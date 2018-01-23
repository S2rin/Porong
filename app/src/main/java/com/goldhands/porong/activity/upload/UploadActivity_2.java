package com.goldhands.porong.activity.upload;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.activity.MainActivity;
import com.goldhands.porong.activity.MyPageActivity;
import com.goldhands.porong.activity.RecommendActivity;
import com.goldhands.porong.activity.SearchActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UploadActivity_2 extends AppCompatActivity {

    private static final int RequestPermissionCode = 1;
    String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    Toolbar toolbar;
    BottomNavigation bn;

    private ImageButton record, stop, reset;
    private ImageButton back, next;
    Chronometer chronometer;

    private MediaRecorder mediaRecorder = null;
    private String status = "";   // RECORDING / DONE / RESET
    private String state;
    private File audioFile;

    String color_Red = "#ff0000";
    String color_Black = "#000000";

    long now = 0;

    // Interface for preference data
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;   // Editor of preference data

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);

        back = (ImageButton) findViewById(R.id.back);
        next = (ImageButton) findViewById(R.id.next);
        record = (ImageButton) findViewById(R.id.start);
        stop = (ImageButton) findViewById(R.id.stop);
        reset = (ImageButton) findViewById(R.id.reset);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        // 1. Request Permission
        requestPermission();
        // 2. Check existence of External Storage
        checkExternalStorage();

        now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(date);
        audioFile = new File(Environment.getExternalStorageDirectory(), "porong" + today +".mp3");

        // Setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        // Bottom Navigation
        BottomNavigation bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkExternalStorage()) {
                    Toast.makeText(getApplicationContext(), "저장 불가합니다.", Toast.LENGTH_SHORT).show();

                    mediaRecorder.release();
                    mediaRecorder = null;
                    onDestroy();
                } else {
                    try {
                        mediaRecorder.release();

                        mediaRecorder = null;
                        onDestroy();

                        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("audio", audioFile.getPath());
                        editor.commit();


                        Intent intent = new Intent(getApplicationContext(), UploadActivity_3.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // ImageViews Event Listener
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.isEnabled()) {
                    record();
                    Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "이미 녹음 중입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stop.isEnabled()) {
                    stop();
                    Toast.makeText(getApplicationContext(), "녹음을 완료합니다.", Toast.LENGTH_SHORT).show();
                } else if (!stop.isEnabled()) {
                    if (reset.isEnabled()) {
                        Toast.makeText(getApplicationContext(), "이미 녹음을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "녹음을 먼저 시작해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset.isEnabled()) {
                    reset();
                    Toast.makeText(getApplicationContext(), "초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "이미 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void record() {
        if (checkPermission()) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                chronometer.start();
                chronometer.setTextColor(Color.parseColor(color_Red));
                status = "RECORDING";

                record.setEnabled(false);
                stop.setEnabled(true);
                reset.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestPermission();
        }
    }

    private void stop() {
        try {
            chronometer.stop();
            chronometer.setTextColor(Color.parseColor(color_Black));
            mediaRecorder.stop();
            status = "DONE";

            record.setEnabled(false);
            stop.setEnabled(false);
            reset.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        try {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setTextColor(Color.parseColor(color_Black));
            mediaRecorder.reset();
            status = "RESET";

            record.setEnabled(true);
            stop.setEnabled(false);
            reset.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        chronometer.stop();
    }

    //NOTE: Navigation Event Listener
    public void setBottomNavigation_EventListener() {
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.i_search:
                        showDialog();
                        bn.bnve.setCurrentItem(2);
                        break;
                    case R.id.i_upload:
                        break;
                    case R.id.i_recommend:
                        intent = new Intent(getApplicationContext(), RecommendActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.i_mypage:
                        intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
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


    // request Permission
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSIONS[1]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSIONS[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSIONS[0])) {

            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, RequestPermissionCode);
            }
        }
    }

    // After requesting permission, get the results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(), "업로드 기능을 위해서 권한을 설정해야 합니다.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkExternalStorage() {
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {  // Read & Write Available
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {   // Read only
            return false;
        } else {  // Read & Write Both unavailable
            return false;
        }
    }
}
