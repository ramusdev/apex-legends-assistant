package com.rb.apexassistant.tasks;

import android.util.Log;

import androidx.room.Database;

import com.google.gson.Gson;
import com.rb.apexassistant.MyApplicationContext;
import com.rb.apexassistant.R;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.WeaponDao;
import com.rb.apexassistant.model.WeaponEntity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class PopulatorWeaponsTask implements Callable<Void>{

    private String path;
    private Class<WeaponEntity> classTyped;
    private WeaponDao weaponDao;

    @Override
    public Void call() throws Exception {
        init();
        clear();
        populate();
        return null;
    }

    public void init() {
        this.path = MyApplicationContext.getAppContext().getResources().getString(R.string.weapons_json_dir);
        this.classTyped = WeaponEntity.class;

        AppDatabase database = AppDatabase.getInstance();
        this.weaponDao = database.WeaponDao();
    }

    public void populate() {
        String json = loadJsonFromFile(this.path);
        WeaponEntity[] weaponEntities = parseJsonToObject(json);

        weaponDao.insertAll(Arrays.asList(weaponEntities));
    }

    public void clear() {
        weaponDao.deleteAll();
    }

    public <T> T[] parseJsonToObject(String stringJson) {
        Gson gson = new Gson();

        Type arrayClassTyped = Array.newInstance(classTyped, 20).getClass();
        T[] arrayObject = gson.fromJson(stringJson, arrayClassTyped);

        return arrayObject;
    }

    public String loadJsonFromFile(String pathToObject) {
        String json = null;
        try {
            InputStream inputStream = MyApplicationContext.getAppContext().getAssets().open(pathToObject);
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
