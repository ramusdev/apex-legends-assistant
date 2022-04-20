package com.rb.apexassistant;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.rb.apexassistant.model.WeaponEntity;

import java.util.List;

public class WeaponsViewAdapter extends RecyclerView.Adapter<WeaponsViewAdapter.ViewHolder> {

    private final List<WeaponEntity> mValues;
    private Context context;

    public WeaponsViewAdapter(Context context, List<WeaponEntity> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weapons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getName());
        holder.damage.setText(mValues.get(position).getDamage());
        holder.dps.setText(mValues.get(position).getDps());
        holder.rpm.setText(mValues.get(position).getRpm());
        holder.magazine.setText(mValues.get(position).getMagazine());
        holder.reload.setText(mValues.get(position).getReload());

        String imageName = mValues.get(position).getImage();
        int imageId = MyApplicationContext.getAppContext().getResources().getIdentifier(imageName, "drawable", MyApplicationContext.getAppContext().getPackageName());
        Drawable imageDrawable = MyApplicationContext.getAppContext().getResources().getDrawable(imageId);

        Glide.with(context)
                .load(imageDrawable)
                .dontTransform()
                .transition(withCrossFade())
                .transform(new MultiTransformation(new GranularRoundedCorners(20, 20, 0, 0)))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView title;
        public final TextView damage;
        public final TextView dps;
        public final TextView rpm;
        public final TextView magazine;
        public final TextView reload;
        public final ImageView image;
        public WeaponEntity mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            title = (TextView) view.findViewById(R.id.weapon_title);
            damage = (TextView) view.findViewById(R.id.data_damage);
            dps = (TextView) view.findViewById(R.id.data_dps);
            rpm = (TextView) view.findViewById(R.id.data_rpm);
            magazine = (TextView) view.findViewById(R.id.data_magazine);
            reload = (TextView) view.findViewById(R.id.data_reload);
            image = (ImageView) view.findViewById(R.id.weapon_image);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            // Intent intent = new Intent(context, WebActivity.class);
            // intent.putExtra("BUNDLE_TEXT", mItem.getText());
            // intent.putExtra("BUNDLE_TITLE", mItem.getTitle());
            // context.startActivity(intent);
        }
    }
}