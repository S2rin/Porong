package com.surin.company.porongui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.surin.company.porongui.R;
import com.surin.company.porongui.retrofit.RequestController;

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

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            reqController = new RequestController(getActivity());
            reqController.buildRequestController();
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            String key = preference.getKey();

            if(key.equals("loginSet")){
                //TODO: 로그인
            }else if(key.equals("logoutSet")){
                //TODO: 로그아웃
            }else if(key.equals("deleteSet")){

                reqController.deleteMember();

            }else if(key.equals("porongInfo")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://13.124.32.165:3000/porongInfo")));
            }
            return true;
        }
    }
}
