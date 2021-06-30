package com.rb.apexassistant.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apexlegends.db";
    private static final int DATABASE_VERSION = 3;

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + DataContract.NewsEntry.TABLE_NAME + " ("
                + DataContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.NewsEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_PREVIEWTEXT + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_TEXT + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_LINK + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_IMAGE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_NOTIFIED + " INTEGER NOT NULL DEFAULT 0);";

        String SQL_CREATE_TWEETS_TABLE = "CREATE TABLE " + DataContract.TweetsEntry.TABLE_NAME + " ("
                + DataContract.TweetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.TweetsEntry.COLUMN_TWEETID + " TEXT NOT NULL, "
                + DataContract.TweetsEntry.COLUMN_TEXT + " TEXT NOT NULL, "
                + DataContract.TweetsEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + DataContract.TweetsEntry.COLUMN_IMAGE + " TEXT, "
                + DataContract.TweetsEntry.COLUMN_NOTIFIED + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TWEETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("MyTag", "Update database");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.NewsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.TweetsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
