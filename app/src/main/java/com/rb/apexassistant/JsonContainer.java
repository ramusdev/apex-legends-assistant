package com.rb.apexassistant;

public class JsonContainer<T> {
    public int version;
    public T[] objects;

    public int getVersion() {
        return version;
    }

    public T[] getObjects() {
        return objects;
    }
}
