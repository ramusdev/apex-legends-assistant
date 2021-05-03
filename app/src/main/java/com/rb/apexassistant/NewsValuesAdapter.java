package com.rb.apexassistant;

import android.content.ContentValues;
import com.rb.apexassistant.data.DataContract;

public class NewsValuesAdapter {

    private News news;

    public NewsValuesAdapter(News news) {
        this.news = news;
    }

    public ContentValues convert() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.NewsEntry.COLUMN_TITLE, news.getTitle());
        contentValues.put(DataContract.NewsEntry.COLUMN_DATE, news.getDate());
        contentValues.put(DataContract.NewsEntry.COLUMN_IMAGE, news.getImage());
        contentValues.put(DataContract.NewsEntry.COLUMN_LINK, news.getLink());
        contentValues.put(DataContract.NewsEntry.COLUMN_PREVIEWTEXT, news.getPreviewText());
        contentValues.put(DataContract.NewsEntry.COLUMN_TEXT, news.getText());
        contentValues.put(DataContract.NewsEntry.COLUMN_NOTIFIED, 0);

        return contentValues;
    }
}
