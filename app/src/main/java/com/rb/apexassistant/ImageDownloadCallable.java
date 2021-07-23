package com.rb.apexassistant;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.FutureTarget;

import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static android.os.Environment.DIRECTORY_PICTURES;

public class ImageDownloadCallable implements Callable<String> {

    public String fileLink;

    public ImageDownloadCallable(String fileLink) {
        this.fileLink = fileLink;
    }

    @Override
    public String call() throws IOException {
        // String fileLink = "https://edgenews.ru/android/apexlegends/wallpapers/4265064.jpg";

        URL url = new URL(fileLink);
        Path path = Paths.get(url.getPath());
        String fileName = path.getFileName().toString();

        // String fileSavePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName;
        String fileSavePath = MyApplicationContext.getAppContext().getExternalFilesDir(DIRECTORY_PICTURES).toString() + "/" + fileName;
        Log.d("MyTag", fileSavePath);

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(fileLink).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileSavePath)){
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Log.d("MyTag", e.getMessage());
        };



        Bitmap bitmapWallpaper = null;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApplicationContext.getAppContext());

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        FutureTarget<Bitmap> futureTarget =
                Glide.with(MyApplicationContext.getAppContext())
                        .asBitmap()
                        .load(fileSavePath)
                        .submit(screenWidth, screenHeight);

        try {
            bitmapWallpaper = futureTarget.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.d("MyTag", e.getMessage());
        }

        wallpaperManager.setBitmap(bitmapWallpaper);



        return fileSavePath;
    }
}
