package com.goldhands.porong.model;

/**
 * Created by surin on 2017. 11. 1..
 */

public class Sounds extends Sound{
    private String[] tags;

    public String getSound_title() {
        return sound_title;
    }

    public void setSound_title(String sound_title) {
        this.sound_title = sound_title;
    }

    public String getSound_album() {
        return sound_album;
    }

    public void setSound_album(String sound_album) {
        this.sound_album = sound_album;
    }

    public String getSound_file(){
        return sound_file;
    }

    public void setSound_file(String sound_file){this.sound_file = sound_file;}

    public String getSound_image(){
        return sound_image;
    }

    public void setSound_image(String sound_image){this.sound_image = sound_image;}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
