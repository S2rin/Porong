package com.goldhands.porong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.goldhands.porong.R;
import com.goldhands.porong.retrofit.RequestController;

/**
 * Created by surin on 2017. 11. 1..
 */

public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,
                        new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        RequestController reqController;
        SharedPreferences sharedPreferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            reqController = new RequestController(getActivity());
            reqController.buildRequestController();

            sharedPreferences = getActivity().getSharedPreferences("LoginAccount", MODE_PRIVATE);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            String key = preference.getKey();
            Intent intent;

            if(key.equals("logoutSet")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }else if(key.equals("deleteSet")){
                reqController.deleteMember();
            }else if(key.equals("porongInfo")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://13.124.32.165:3000/porongInfo")));
            }
            return true;
        }
    }
}
