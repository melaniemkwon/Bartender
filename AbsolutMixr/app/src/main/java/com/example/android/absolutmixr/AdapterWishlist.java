package com.example.android.absolutmixr;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.absolutmixr.Model.WishlistContract;
import com.squareup.picasso.Picasso;

import static com.example.android.absolutmixr.AdapterDrink.Picture_url;

/**
 * Created by melaniekwon on 8/6/17.
 */

public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.WishlistViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public AdapterWishlist(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        public final TextView mDrinkName;
        public final TextView mDrinkRating;
        public final TextView mDrinkColor;
        public final ImageView mDrinkpic;
        public final Button mDotButton;
        public final ImageButton mThumbsupButton;
        public final ImageButton mThumbsdownButton;

        public WishlistViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.wishlist_name);
            mDrinkRating = (TextView) view.findViewById(R.id.wishlist_rating);
            mDrinkColor = (TextView) view.findViewById(R.id.wishlist_color);
            mDrinkpic = (ImageView) view.findViewById(R.id.wishlist_drinkImage);
            mDotButton = (Button) view.findViewById(R.id.wishlist_action);
            mThumbsupButton = (ImageButton) view.findViewById(R.id.wishlist_thumbsup);
            mThumbsdownButton = (ImageButton) view.findViewById(R.id.wishlist_thumbsdown);
        }
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.display_wishlist_view, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WishlistViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) { return; }

        int id = mCursor.getInt(mCursor.getColumnIndex(WishlistContract.WishlistEntry._ID));
        String name = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_NAME));
        String rating = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_RATING));
        String color = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_COLOR));
        String pic_URL = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_PICTURE_URL));

        holder.mDrinkName.setText(name);
        holder.mDrinkRating.setText(rating);
        holder.mDrinkColor.setText(color);
        if(pic_URL != null){
            Picasso.with(mContext)
                    .load(pic_URL)
                    .into(holder.mDrinkpic);
        }

        // DONE: popupmenu
        // TODO: implement - shop, share, delete
        holder.mDotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mDotButton);
                popupMenu.getMenuInflater().inflate(R.menu.wishlist_popup_action, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mContext, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        // DONE: RATE THIS DRINK
        holder.mThumbsupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable defaultThumbsup = mContext.getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp);
                Drawable selectedThumbsup = mContext.getResources().getDrawable(R.drawable.ic_thumb_up_selected);
                Drawable defaultThumbsdown = mContext.getResources().getDrawable(R.drawable.ic_thumb_down_black_24dp);

                if (holder.mThumbsupButton.getDrawable().getConstantState().equals(defaultThumbsup.getConstantState())) {
                    holder.mThumbsupButton.setImageDrawable(selectedThumbsup);
                    holder.mThumbsdownButton.setImageDrawable(defaultThumbsdown);
                } else if (holder.mThumbsupButton.getDrawable().getConstantState().equals(selectedThumbsup.getConstantState())) {
                    holder.mThumbsupButton.setImageDrawable(defaultThumbsup);
                }
            }
        });

        holder.mThumbsdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable defaultThumbsdown = mContext.getResources().getDrawable(R.drawable.ic_thumb_down_black_24dp);
                Drawable selectedThumbsdown = mContext.getResources().getDrawable(R.drawable.ic_thumb_down_selected);
                Drawable defaultThumbsup = mContext.getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp);

                if (holder.mThumbsdownButton.getDrawable().getConstantState().equals(defaultThumbsdown.getConstantState())) {
                    holder.mThumbsdownButton.setImageDrawable(selectedThumbsdown);
                    holder.mThumbsupButton.setImageDrawable(defaultThumbsup);
                } else if (holder.mThumbsdownButton.getDrawable().getConstantState().equals(selectedThumbsdown.getConstantState())) {
                    holder.mThumbsdownButton.setImageDrawable(defaultThumbsdown);
                }
            }
        });


        // TODO: BROWNIE POINTS-- undo delete item
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
