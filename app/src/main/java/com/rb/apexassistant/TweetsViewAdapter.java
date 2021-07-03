package com.rb.apexassistant;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV;
import static android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING;
import static android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TweetsViewAdapter extends RecyclerView.Adapter<TweetsViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    private Context context;

    public TweetsViewAdapter(Context context, List<Tweet> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText(), FROM_HTML_MODE_COMPACT));
        holder.mTitleView.setText("@PlayApex " + mValues.get(position).getDate());

        if (mValues.get(position).getImages().size() > 0) {
            Glide.with(context)
                    .load(mValues.get(position).getImages().get(0))
                    .dontTransform()
                    .transition(withCrossFade())
                    .transform(new MultiTransformation(new GranularRoundedCorners(0, 0, 20, 20)))
                    .into(holder.mImageView);
        } // else {
            // holder.mImageView.setVisibility(View.GONE);
        // }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mTextView;
        public final ImageView mImageView;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mTextView = (TextView) view.findViewById((R.id.news_text));
            mImageView = (ImageView) view.findViewById((R.id.news_image));

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("BUNDLE_TEXT", mItem.getText());
            // intent.putExtra("BUNDLE_TITLE", mItem.getTitle());
            context.startActivity(intent);
        }
    }
}