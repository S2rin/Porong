package com.surin.company.porongui.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by surin on 2017. 10. 4..
 */

public class SearchItem extends RealmObject{

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
