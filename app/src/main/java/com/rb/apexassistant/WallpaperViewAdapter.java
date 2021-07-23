package com.rb.apexassistant;

import androidx.recyclerview.widget.RecyclerView;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class WallpaperViewAdapter extends RecyclerView.Adapter<WallpaperViewAdapter.ViewHolder> {

    private final List<News> mValues;
    private Context context;

    public WallpaperViewAdapter(Context context, List<News> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // holder.mTextView.setText(mValues.get(position).getPreviewText());
        // holder.mTitleView.setText(mValues.get(position).getTitle());
        // holder.mDateView.setText(mValues.get(position).getDate());
        Glide.with(context)
                .load(mValues.get(position).getImage())
                .dontTransform()
                .transition(withCrossFade())
                .transform(new MultiTransformation(new GranularRoundedCorners(20, 20, 0, 0)))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        // public final TextView mTitleView;
        // public final TextView mTextView;
        // public final TextView mDateView;
        public final ImageView mImageView;
        public Button button;
        public News mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            // mTitleView = (TextView) view.findViewById(R.id.news_title);
            // mDateView = (TextView) view.findViewById((R.id.news_date));
            // mTextView = (TextView) view.findViewById((R.id.news_text));
            mImageView = (ImageView) view.findViewById(R.id.news_image);
            button = (Button) view.findViewById(R.id.button_download);

            button.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View v) {

            button.setText("Downloading");
            button.setBackgroundResource(R.drawable.button_wallpaper_download);


            // Intent intent = new Intent(context, WebActivity.class);
            // intent.putExtra("BUNDLE_TEXT", mItem.getText());
            // intent.putExtra("BUNDLE_TITLE", mItem.getTitle());
            // context.startActivity(intent);

            // Log.d("MyTag", "From click");

            // WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApplicationContext.getAppContext());
            // Bitmap bitmap = BitmapFactory.decodeFile(fileSavePath);
            // wallpaperManager.getCropAndSetWallpaperIntent(Uri.parse("/storage/emulated/0/Download/5781130.jpg"));


            // File wallpaper_file = new File(Uri.parse("/document/raw:/storage/emulated/0/Download/5781130.jpg").getPath());
            // Uri contentURI = getImageContentUri(MyApplicationContext.getAppContext(), wallpaper_file);

            // ContentResolver cr = this.getContentResolver();
            // Log.d("CONTENT TYPE: ", "IS: " + cr.getType(contentURI));

            // Intent intent = new Intent(wallpaperManager.getCropAndSetWallpaperIntent(contentURI));
            // MyApplicationContext.getAppContext().startActivity(intent);


            TaskRunner.TaskRunnerCallback<String> taskRunnerCallback = new TaskRunner.TaskRunnerCallback<String>() {
                @Override
                public void execute(String fileSavePath) {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApplicationContext.getAppContext());
                    Bitmap bitmapWallpaper = BitmapFactory.decodeFile(fileSavePath);

                    // Log.d("MyTag", fileSavePath);

                    // try {
                        // wallpaperManager.setBitmap(bitmap);

                    // wallpaperManager.getCropAndSetWallpaperIntent(Uri.parse(fileSavePath));

                    // } catch (IOException e) {
                        // Log.d("MyTag", e.getMessage());
                    // }

                    // 1. Get screen size.
                    // DisplayMetrics metrics = new DisplayMetrics();
                    // Display display =

                    // display.getMetrics(metrics);
                    // final int screenWidth  = metrics.widthPixels;
                    // final int screenHeight = metrics.heightPixels;

                    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
                    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

                    // int screenWidth = MyApplicationContext.getAppContext().getResources().getConfiguration().screenWidthDp;
                    // int screenHeight = MyApplicationContext.getAppContext().getResources().getConfiguration().screenHeightDp;

                    // int screenWidth = 100;
                    // int screenHeight = 100;

                    // 2. Make the wallpaperManager fit the screen size.
                    // final WallpaperManager wallpaperManager = WallpaperManager.getInstance(ViewWallpaperActivity.this);
                    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight);

                    // 3. Get the desired size.
                    final int width = wallpaperManager.getDesiredMinimumWidth();
                    final int height = wallpaperManager.getDesiredMinimumHeight();
                    // final int height = 3040;

                    // Log.d("MyTag", String.valueOf(width));
                    // Log.d("MyTag", String.valueOf(height));

                    // 4. Scale the wallpaper.
                    // Bitmap bitmap = getBitmap(); // getBitmap(): Get the image to be set as wallpaper.
                    /*
                    Bitmap wallpaper = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    Bitmap wallpaper2 = Bitmap.createBitmap(
                            bitmap,
                            0,
                            bitmap.getHeight()/2 - bitmap.getWidth()/2,
                            bitmap.getWidth(),
                            bitmap.getHeight()
                    );
                    */

                    // Bitmap wallpaper_sized = Bitmap.createBitmap(bitmap, 0, 0, 1440, 2701);
                    // Bitmap wallpaper_sized = Bitmap.createScaledBitmap(bitmap, 1440, 2701, true);

                    // Bitmap bitmapWallpaper = BitmapScaler.scaleToFitHeight(bitmap, screenHeight);
                    // Bitmap bitmapWallpaper = ThumbnailUtils.extractThumbnail(bitmap, screenWidth, screenHeight);
                    // Bitmap bitmapWallpaper = ThumbnailUtils.createImageThumbnail()



                    // int w = wallpaper.getWidth();
                    // int h = wallpaper.getHeight();

                    // Log.d("MyTag", String.valueOf(w));
                    // Log.d("MyTag", String.valueOf(h));

                    // Drawable st = wallpaperManager.getBuiltInDrawable();

                    // 5. Set the image as wallpaper.
                    /*
                    try {
                        wallpaperManager.setBitmap(bitmapWallpaper);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */

                    button.setText("Wallpaper is set");
                    button.setBackgroundResource(R.drawable.button_wallpaper_set);
                }
            };

            // String imageLink = mItem.getImage();
            String imageLink = "https://edgenews.ru/android/apexlegends/wallpapers/5781051.png";
            // String imageLink = "https://edgenews.ru/android/apexlegends/wallpapers/wallpaper_img.jpg";

            TaskRunner<String> taskRunner = new TaskRunner<String>();
            Callable imageDownloadCallable = new ImageDownloadCallable(imageLink);
            taskRunner.executeAsync(imageDownloadCallable, taskRunnerCallback);
            // taskRunner.executeAsync(imageDownloadCallable);
        }
    }
}