package com.goldhands.porong.model;

/**
 * Created by surin on 2017. 11. 1..
 */

public class TagListItem {
    private String num;
    private String title;

    public TagListItem(String num, String title) {
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
