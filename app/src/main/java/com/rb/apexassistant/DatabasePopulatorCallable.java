package com.rb.apexassistant;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;

public class DatabasePopulatorCallable implements Callable<Integer> {

    public DatabasePopulatorCallable() {

    }

    @Override
    public Integer call() {
        String stringJson = loadJsonFromFile();
        JsonContainer<Wallpaper> jsonContainer = parseJsonToObject(stringJson);

        updateDataToDatabase(jsonContainer);

        return 1;
    }

    public void updateDataToDatabase(JsonContainer<Wallpaper> jsonContainer) {
        DatabaseEntity<Wallpaper> databaseEntity = new DatabaseEntity<Wallpaper>(Wallpaper.class);
        databaseEntity.clear();
        databaseEntity.insert(jsonContainer.getWallpapers());
    }

    public JsonContainer<Wallpaper> parseJsonToObject(String stringJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<JsonContainer<Wallpaper>>(){}.getType();
        JsonContainer<Wallpaper> jsonContainer = gson.fromJson(stringJson, type);

        return jsonContainer;
    }

    public String loadJsonFromFile() {
        String json = null;
        try {
            InputStream inputStream = MyApplicationContext.getAppContext().getAssets().open("Wallpaper/wallpaper.json");
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
