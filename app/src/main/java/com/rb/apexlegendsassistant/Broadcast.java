package com.rb.apexlegendsassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        updateNewsAsync.execute();

        NewsClear newsClear = new NewsClear(context);
        newsClear.execute();

        NotificationShower notificationShower = new NotificationShower(context);
        notificationShower.execute();
    }
}
