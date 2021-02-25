package com.rb.apexlegendsassistant;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<News> mValues;
    private Context context;

    public MyItemRecyclerViewAdapter(Context context, List<News> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextView.setText(mValues.get(position).getPreviewText());
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDateView.setText(mValues.get(position).getDate());
        Glide.with(context)
                .load(mValues.get(position).getImage())
                .dontTransform()
                .transition(withCrossFade())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mTextView;
        public final TextView mDateView;
        public final ImageView mImageView;
        public News mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mDateView = (TextView) view.findViewById((R.id.news_date));
            mTextView = (TextView) view.findViewById((R.id.news_text));
            mImageView = (ImageView) view.findViewById((R.id.news_image));

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("BUNDLE_TEXT", mItem.getText());
            intent.putExtra("BUNDLE_TITLE", mItem.getTitle());
            context.startActivity(intent);
        }
    }
}