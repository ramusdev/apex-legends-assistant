package com.rb.apexlegendsassistant;

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
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.rb.apexlegendsassistant.data.DataContract;
import com.rb.apexlegendsassistant.data.DataDbHelper;

public class UpdateNewsAsync extends AsyncTask<Void, Void, Void> {

    Context context;
    List<News> newsArray = new ArrayList<News>();
    public static final String NEWS_LINK = "https://www.ea.com/ru-ru/games/apex-legends/news";

    public UpdateNewsAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("CustomLogTag", "From background update news");

        if (isNetworkAvailable()) {
            parseNews();
            insertNewsToDatabase();
        }

        return null;
    }


    public void parseNews() {
        try {
            NewsParser newsParser = new NewsParser(NEWS_LINK);
            newsArray = newsParser.parse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String cleanText(String text, String imageLink) {
        StringBuilder stringBuilder = new StringBuilder(text);
        String html = "<html>";
        String style = "<link type=\"text/css\" rel=\"stylesheet\" media=\"all\" href=\"https://edgenews.ru/android/wardocwarface/news/style.css\">";
        String image = "<img src=\"" + imageLink + "\">";

        stringBuilder.insert(0, html);
        stringBuilder.insert(6, style);
        stringBuilder.insert(121, image);
        stringBuilder.append("</html>");

        String cleanTags = stringBuilder
                .toString()
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&nbsp;", " ")
                .replace("&quot;", "\"")
                .replace("&mdash;", "—")
        	    .replace("<!--break-->", "");

        Pattern pattern = Pattern.compile("<script(.*?)</script>(.*?)<style(.*?)</style>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(cleanTags);
        String cleanScript = matcher.replaceAll("");

        return cleanScript;
    }

    public String cleanPreview(String text) {
        String cleanText = text
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&nbsp;", " ")
                .replace("&quot;", "\"")
                .replace("&mdash;", "—")
                .replace("<!--break-->", "");

        return cleanText;
    }

    public void insertNewsToDatabase() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        for (News news : newsArray) {
            if (! isNewsExists(news, sqLiteDatabase)) {
                // String text = cleanText(news.getText(), news.getImage());
                // news.setText(text);

                // String previewText = cleanPreview(news.getPreviewText());
                // news.setPreviewText(previewText);

                // String date = News.formatFromParseToDatabase(news.getDate());
                // news.setDate(date);

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