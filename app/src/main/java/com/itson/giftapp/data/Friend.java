package com.itson.giftapp.data;

public class Friend {
    private String name;
    private int photoResId;

    public Friend(String name, int photoResId) {
        this.name = name;
        this.photoResId = photoResId;
    }

    public String getName() {
        return name;
    }

    public int getPhotoResId() {
        return photoResId;
    }
}
