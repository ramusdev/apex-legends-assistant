package com.rb.apexassistant;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import kotlin.jvm.internal.TypeParameterReference;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DatabasePopulatorCallable<T> implements Callable<Integer> {


    public String path;
    public Class<T> classTyped;

    public DatabasePopulatorCallable(String path, Class<T> classTyped) {
        this.path = path;
        this.classTyped = classTyped;
    }

    @Override
    public Integer call() {
        String stringJson = loadJsonFromFile();
        T[] arrayObject = parseJsonToObject(stringJson);
        // Log.d("MyTag", Arrays.toString(jsonContainer.getObjects()));

        updateDataToDatabase(arrayObject);

        return 1;
    }

    public void updateDataToDatabase(T[] arrayObject) {
        DatabaseEntity<T> databaseEntity = new DatabaseEntity<T>(classTyped);
        databaseEntity.clear();
        databaseEntity.insert(arrayObject);
    }

    public T[] parseJsonToObject(String stringJson) {
        Gson gson = new Gson();

        Type arrayClassTyped = Array.newInstance(classTyped, 20).getClass();
        T[] arrayObject = gson.fromJson(stringJson, arrayClassTyped);

        return arrayObject;
    }

    public String loadJsonFromFile() {
        String json = null;
        try {
            InputStream inputStream = MyApplicationContext.getAppContext().getAssets().open(path);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
        } catch(IOException e) {
            Log.d("MyTag", "Error: " + e.getMessage());
        }

        return json;
    }
}
