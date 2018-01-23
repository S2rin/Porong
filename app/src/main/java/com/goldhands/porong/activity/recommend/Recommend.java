package com.goldhands.porong.activity.recommend;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Recommend{

    private Object total;

    private Recommend item;

    @SerializedName("title")
    private String title;

    @SerializedName("addr1")
    private String addr1;

    @SerializedName("overview")
    private String overview;

    @SerializedName("firstimage")
    private String firstimage;


    private Bitmap bitmap;

    Recommend(){

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Recommend getItem() {
        return item;
    }

    public void setItem(Recommend item) {
        this.item = item;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }
}
