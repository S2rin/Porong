package com.goldhands.porong.realm;

import io.realm.RealmObject;

/**
 * Created by surin on 2017. 11. 1..
 */

public class SearchItem extends RealmObject {
    private String item;

    public SearchItem() {
    }

    public SearchItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
