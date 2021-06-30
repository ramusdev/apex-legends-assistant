package com.rb.apexassistant;

import java.util.ArrayList;

public class Tweet {

    public String text;
    public String created_at;
    public String id;
    public ArrayList<String> imageKeys = new ArrayList<String>();
    public ArrayList<String> images = new ArrayList<String>();

    public String toString() {
        return "text: " + text + "\n" +
                "created_at: " + created_at + "\n" +
                "id: " + id + "\n" +
                "image: " + images + "\n\n";
    }

    public void setImageKeys(String imageKey) {
        this.imageKeys.add(imageKey);
    }

    public void setImages(String url) {
        this.images.add(url);
    }

    public ArrayList<String> getImageKeys() {
        return imageKeys;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getDate() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
