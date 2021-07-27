package com.rb.apexassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rb.apexassistant.data.DataContract;
import com.rb.apexassistant.data.DataDbHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseEntity<T> {

    public T[] data;

    public DatabaseEntity() {

    }

    public void insert(T[] data) {
        this.data = data;

        DataDbHelper dataDbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dataDbHelper.getWritableDatabase();

        for (int i = 0; i < data.length; ++i) {
            Log.d("MyTag", "Inside class");
            ContentValues contentValues = adapter(data[i]);
            sqLiteDatabase.insert(DataContract.WallpaperEntry.TABLE_NAME, null, contentValues);
        }
    }

    public void clear() {
        DataDbHelper dbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String deleteQuery = "DELETE" +
                " FROM " + DataContract.WallpaperEntry.TABLE_NAME;

        sqLiteDatabase.execSQL(deleteQuery);
    }

    public List<Wallpaper> load() {
        DataDbHelper dbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String orderBy = DataContract.WallpaperEntry.COLUMN_IMAGE + " DESC";
        String limit = "20";
        Cursor cursor = sqLiteDatabase.query(DataContract.WallpaperEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                limit
        );

        List<Wallpaper> wallpapers = new ArrayList<Wallpaper>();

        while (cursor.moveToNext()) {
            Wallpaper wallpaper = new Wallpaper();
            wallpaper.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.WallpaperEntry.COLUMN_IMAGE)));

            wallpapers.add(wallpaper);
        }

        cursor.close();

        return wallpapers;
    }

    public ContentValues adapter(T someClass) {
        Field[] fields = someClass.getClass().getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        Log.d("MyTag", "Inside class 2");

        for (int k = 0; k < fields.length; ++k) {
            try {
                Field field = fields[k];
                String name = field.getName();
                Object value = field.get(someClass);

                Log.d("MyTag", value.toString());

                if (value instanceof String) {
                    contentValues.put(name, (String) value);
                } else if (value instanceof Integer) {
                    contentValues.put(name, (int) value);
                }

            } catch (IllegalAccessException e) {
                Log.d("MyTag", e.getMessage());
            }
        }

        return contentValues;
    }
}
