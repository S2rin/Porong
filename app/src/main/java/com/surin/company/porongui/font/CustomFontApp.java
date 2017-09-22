package com.surin.company.porongui.font;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by surin on 2017. 9. 17..
 */

public class CustomFontApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        //Typekit
        Typekit.getInstance()
                .addCustom1(Typekit.createFromAsset(this, "fonts/BMJUA_ttf.ttf"));
    }
}
