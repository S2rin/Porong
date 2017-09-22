package com.surin.company.porongui.navigation;

import android.net.sip.SipAudioCall;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by surin on 2017. 9. 15..
 */

public class BottomNavigation {
    public BottomNavigationViewEx bnve;

    public void BottomNavigation(){

    }

    public BottomNavigationViewEx getBnve() {
        return bnve;
    }

    public void setBnve(BottomNavigationViewEx bnve) {
        this.bnve = bnve;
    }

    //NOTE: Bottom navigation 설정

    public void navigationInit(BottomNavigationViewEx bnve){
        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(false);

        bnve.setItemHeight(BottomNavigationViewEx.dp2px(bnve.getContext(), 56));
        bnve.setIconsMarginTop(BottomNavigationViewEx.dp2px(bnve.getContext(), 16));
    }

    //NOTE: Navigation Listener
}
