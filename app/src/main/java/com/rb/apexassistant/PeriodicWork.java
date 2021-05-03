package com.rb.apexassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rb.apexassistant.data.DataDbHelper;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodicWork extends Worker {

    private Context context;
    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_SERVER = "server";

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyTag", "periodic work class -->");

        TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
        Callable callable = new UpdateNewsCallable(5);
        taskRunner.executeAsync(callable);

        return Result.success();
    }
}
