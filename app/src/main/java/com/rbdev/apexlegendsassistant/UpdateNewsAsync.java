package com.rbdev.apexlegendsassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.rbdev.apexlegendsassistant.data.DataContract;
import com.rbdev.apexlegendsassistant.data.DataDbHelper;

public class UpdateNewsAsync extends AsyncTask<Void, Void, Void> {

    Context context;
    List<News> newsArray = new ArrayList<News>();
    // public static final String NEWS_LINK = "https://www.ea.com/ru-ru/games/apex-legends/news";
    public UpdateNewsAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (isNetworkAvailable()) {
            parseNews();
            insertNewsToDatabase();
        }

        return null;
    }

    public void parseNews() {
        try {
            NewsParser newsParser = new NewsParser(context.getResources().getString(R.string.news_link));
            newsArray = newsParser.parse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertNewsToDatabase() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        for (News news : newsArray) {
            if (! isNewsExists(news, sqLiteDatabase)) {
                NewsValuesAdapter newValuesAdapter = new NewsValuesAdapter(news);
                ContentValues contentValues = newValuesAdapter.convert();
                long newRowId = sqLiteDatabase.insert(DataContract.NewsEntry.TABLE_NAME, null, contentValues);
            }
        }
    }

    public boolean isNewsExists(News news, SQLiteDatabase sqLiteDatabase) {
        String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { news.getTitle() };

        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            // Log.e("CustomLogTag", "Entry exists in database");
            cursor.close();
            return true;
        } else {
            // Log.e("CustomLogTag", "Entry do not exists in database");
            cursor.close();
            return false;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            );
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
