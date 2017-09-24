package com.surin.company.porongui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.surin.company.porongui.R;

/**
 * Created by HyunJu on 2017-09-20.
 * Upload View(2) - Record
 */

public class UploadActivity_2 extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button start, stop, reset;
    private RelativeLayout buttonLayout1, buttonLayout2;
    private int time = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);


        buttonLayout1 = (RelativeLayout) findViewById(R.id.buttonLayout1);
        buttonLayout2 = (RelativeLayout) findViewById(R.id.buttonLayout2);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        reset = (Button) findViewById(R.id.reset);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(60);
        progressBar.setProgress(time);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLayout1.setVisibility(View.GONE);
                buttonLayout2.setVisibility(View.VISIBLE);
            }
        });
    }
}
