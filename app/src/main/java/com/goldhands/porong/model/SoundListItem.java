package com.goldhands.porong.model;

/**
 * Created by surin on 2017. 11. 1..
 */

public class SoundListItem {
    private String nickname;
    private String sound_title;

    public SoundListItem(String nickname, String sound_title) {
        this.nickname = nickname;
        this.sound_title = sound_title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSound_title() {
        return sound_title;
    }

    public void setSound_title(String sound_title) {
        this.sound_title = sound_title;
    }
}
