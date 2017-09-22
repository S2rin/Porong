package com.surin.company.porongui.model;

/**
 * Created by surin on 2017. 9. 18..
 */

public class SoundListItem {

    private String num;
    private String title;

    public SoundListItem(String num, String title) {
        this.num = num;
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}