package com.goldhands.porong.UI;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by surin on 2017. 11. 1..
 */

public class PlayBottomNavigation {
    public BottomNavigationViewEx play_bnve;

    public PlayBottomNavigation() {
    }

    public BottomNavigationViewEx getPlay_bnve() {
        return play_bnve;
    }

    public void setPlay_bnve(BottomNavigationViewEx play_bnve) {
        this.play_bnve = play_bnve;
    }

    public void navigationInit(BottomNavigationViewEx bnve){
        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(true);

        bnve.setItemHeight(BottomNavigationViewEx.dp2px(bnve.getContext(), 56));
        bnve.setIconsMarginTop(BottomNavigationViewEx.dp2px(bnve.getContext(), 16));
    }
}
