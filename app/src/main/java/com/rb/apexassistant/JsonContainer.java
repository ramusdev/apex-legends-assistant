package com.rb.apexassistant;

public class JsonContainer<T> {
    public int version;
    public T[] wallpapers;

    public int getVersion() {
        return version;
    }

    public T[] getWallpapers() {
        return wallpapers;
    }
}
