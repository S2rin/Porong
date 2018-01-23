package com.goldhands.porong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.activity.upload.UploadActivity_1;
import com.goldhands.porong.adapter.SoundsAdapter;
import com.goldhands.porong.model.SoundListItem;
import com.goldhands.porong.retrofit.ApiService;
import com.goldhands.porong.retrofit.ApplicationController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class UploadListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private SoundsAdapter adapter;
    private List<SoundListItem> soundsList;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private String nickname;
    private static final String TAG = "TEST";
    private List<String> title;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_list);

        sharedPreferences = getSharedPreferences("LoginAccount", MODE_PRIVATE);
        nickname = sharedPreferences.getString("nickname", null);

        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService();
        apiService = ApplicationController.getInstance().getApiService();

        pbar = (ProgressBar) findViewById(R.id.progressBar);

        CountTask count = new CountTask();
        count.execute();
    }

    public class CountTask extends AsyncTask<Integer, Integer, List<SoundListItem>> {
        @Override
        protected List<SoundListItem> doInBackground(Integer... integers) {
            Call<List<SoundListItem>> upload_info = apiService.getUserUpload(nickname);
            try{
                return upload_info.execute().body();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<SoundListItem> item) {
            super.onPostExecute(item);

            if(item.size() == 0){
                showDialog();
            }else {
                title = new ArrayList<String>();
                for(SoundListItem soundlist : item){
                    title.add(soundlist.getSound_title());
                }

                recyclerView = (RecyclerView) findViewById(R.id.myUploadList);
                recyclerView.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);

                soundsList = new ArrayList<>();

                for (int i = 0; i < item.size(); i++) {
                    SoundListItem sound = new SoundListItem(nickname,title.get(i));
                    soundsList.add(sound);
                }

                adapter = new SoundsAdapter(soundsList,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

        }
    }

    public void showDialog() {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.information)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setDescription("아직 업로드를 하지 않으셨군요! 업로드를 하러 가시겠습니까?")
                .setCancelable(true)
                .setPositiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getApplicationContext(), UploadActivity_1.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .withIconAnimation(false)
                .show();
    }
}
