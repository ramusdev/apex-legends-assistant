package com.rb.apexassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rb.apexassistant.data.DataContract;
import com.rb.apexassistant.data.DataDbHelper;

import java.util.concurrent.Callable;

public class TweetsClearCallable implements Callable<Integer> {

    private Context context;

    public TweetsClearCallable() {
        this.context = MyApplicationContext.getAppContext();
    }

    @Override
    public Integer call() {
        clear();

        return 1;
    }

    public void clear() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String deleteQuery = "DELETE" +
                " FROM " + DataContract.TweetsEntry.TABLE_NAME +
                " WHERE " + DataContract.TweetsEntry._ID +
                " NOT IN (SELECT " + DataContract.TweetsEntry._ID +
                " FROM " + DataContract.TweetsEntry.TABLE_NAME +
                " ORDER BY " + DataContract.TweetsEntry._ID +
                " DESC " +
                " LIMIT 20)";

        sqLiteDatabase.execSQL(deleteQuery);
    }
}
