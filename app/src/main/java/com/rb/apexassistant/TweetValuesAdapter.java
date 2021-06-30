package com.rb.apexassistant;

import android.content.ContentValues;

import com.rb.apexassistant.data.DataContract;

public class TweetValuesAdapter implements ValuesAdapterInterface {

    private Tweet tweet;

    public TweetValuesAdapter(Tweet tweet) {
        this.tweet = tweet;
    }

    public static ContentValues convert() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.TweetsEntry.COLUMN_TWEETID, tweet.getId());
        contentValues.put(DataContract.TweetsEntry.COLUMN_DATE, tweet.getDate());
        // contentValues.put(DataContract.TweetsEntry.COLUMN_IMAGE, tweet.getImages());
        contentValues.put(DataContract.TweetsEntry.COLUMN_TEXT, tweet.getText());
        contentValues.put(DataContract.TweetsEntry.COLUMN_NOTIFIED, 0);

        return contentValues;
    }
}
