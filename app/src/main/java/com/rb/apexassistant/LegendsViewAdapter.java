package com.rb.apexassistant;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class LegendsViewAdapter extends RecyclerView.Adapter<LegendsViewAdapter.ViewHolder> {

    private final List<Legend> mValues;
    private Context context;

    public LegendsViewAdapter(Context context, List<Legend> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.legend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mTaglineView.setText(mValues.get(position).getTagline());

        String imageName = mValues.get(position).getImage();
        int imageId = MyApplicationContext.getAppContext().getResources().getIdentifier(imageName, "drawable", MyApplicationContext.getAppContext().getPackageName());
        Drawable imageDrawable = MyApplicationContext.getAppContext().getResources().getDrawable(imageId);

        Glide.with(context)
                .load(imageDrawable)
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
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final TextView mTaglineView;
        public final ImageView mImageView;
        public Legend mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.legend_title);
            mTaglineView = (TextView) view.findViewById((R.id.legend_tagline));
            mDescriptionView = (TextView) view.findViewById((R.id.legend_text));
            mImageView = (ImageView) view.findViewById((R.id.legend_image));

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, LegendsWebActivity.class);
            intent.putExtra("BUNDLE_LINK", mItem.getLink());
            context.startActivity(intent);
        }
    }
}