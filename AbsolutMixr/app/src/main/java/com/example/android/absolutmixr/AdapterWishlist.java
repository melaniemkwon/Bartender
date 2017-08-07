package com.example.android.absolutmixr;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.WishlistContract;
import com.squareup.picasso.Picasso;

import static com.example.android.absolutmixr.AdapterDrink.Picture_url;

/**
 * Created by melaniekwon on 8/6/17.
 */

public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.WishlistViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    private static final String Picture_url = "http://assets.absolutdrinks.com/drinks/solid-background-white/soft-shadow/floor-reflection/100x100/";
    private static final String type= ".png";

    public AdapterWishlist(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        public final TextView mDrinkName;
        public final TextView mDrinkRating;
        public final TextView mDrinkColor;
        public final ImageView mDrinkpic;

        public WishlistViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.name);
            mDrinkRating = (TextView) view.findViewById(R.id.rating);
            mDrinkColor = (TextView) view.findViewById(R.id.color);
            mDrinkpic = (ImageView) view.findViewById(R.id.drinkImage);
        }
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.display_list_view, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        int id = mCursor.getInt(mCursor.getColumnIndex(WishlistContract.WishlistEntry._ID));
        String name = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_NAME));
        String rating = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_RATING));
        String color = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_COLOR));
        String pic_URL = Picture_url + mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_PICTURE_URL)) + type;

        holder.mDrinkName.setText(name);
        holder.mDrinkRating.setText(rating);
        holder.mDrinkColor.setText(color);
        if(pic_URL != null){
            Picasso.with(mContext)
                    .load(pic_URL)
                    .into(holder.mDrinkpic);
        }

        // TODO: on swipe of item, remove drink item from db

        // TODO: BROWNIE POINTS-- undo delete item
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
