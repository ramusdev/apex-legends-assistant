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

    private final List<Wallpaper> mValues;
    private Context context;

    public WallpaperViewAdapter(Context context, List<Wallpaper> items) {
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
        Glide.with(context)
                .load(mValues.get(position).getImage())
                .dontTransform()
                .transition(withCrossFade())
                .transform(new MultiTransformation(new GranularRoundedCorners(20, 20, 20, 20)))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView mImageView;
        public Button button;
        public Wallpaper mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
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

            button.setText(MyApplicationContext.getAppContext().getResources().getString(R.string.wallpaper_downloading));
            button.setBackgroundResource(R.drawable.button_wallpaper_download);

            TaskRunner.TaskRunnerCallback<String> taskRunnerCallback = new TaskRunner.TaskRunnerCallback<String>() {
                @Override
                public void execute(String fileSavePath) {
                    button.setText(MyApplicationContext.getAppContext().getResources().getString(R.string.wallpaper_loaded));
                    button.setBackgroundResource(R.drawable.button_wallpaper_set);
                }
            };

            String imageLink = mItem.getImage();
            TaskRunner<String> taskRunner = new TaskRunner<String>();
            Callable imageDownloadCallable = new ImageDownloadCallable(imageLink);
            taskRunner.executeAsync(imageDownloadCallable, taskRunnerCallback);
        }
    }
}