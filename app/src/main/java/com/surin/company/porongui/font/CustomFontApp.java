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
                .addNormal(Typekit.createFromAsset(this,"fonts/IropkeBatangM.ttf"));
                .addCustom1(Typekit.createFromAsset(this, "fonts/IropkeBatangM.ttf"));

    }
}
