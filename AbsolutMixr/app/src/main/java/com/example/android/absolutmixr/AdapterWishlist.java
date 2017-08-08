package com.example.android.absolutmixr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.example.android.absolutmixr.Model.DrinkItem;
import com.example.android.absolutmixr.Model.WishlistContract;
import com.example.android.absolutmixr.Model.WishlistDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.android.absolutmixr.AdapterDrink.Picture_url;

/**
 * Created by melaniekwon on 8/6/17.
 */

public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.WishlistViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private SQLiteDatabase mDb;

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
        public final CardView mCardview;

        public WishlistViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.wishlist_name);
            mDrinkRating = (TextView) view.findViewById(R.id.wishlist_rating);
            mDrinkColor = (TextView) view.findViewById(R.id.wishlist_color);
            mDrinkpic = (ImageView) view.findViewById(R.id.wishlist_drinkImage);
            mDotButton = (Button) view.findViewById(R.id.wishlist_action);
            mThumbsupButton = (ImageButton) view.findViewById(R.id.wishlist_thumbsup);
            mThumbsdownButton = (ImageButton) view.findViewById(R.id.wishlist_thumbsdown);
            mCardview = (CardView) view.findViewById(R.id.wishlist_card_view);
        }
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Database for writing to Wishlist
        WishlistDbHelper dbHelper = new WishlistDbHelper(mContext);
        mDb = dbHelper.getWritableDatabase();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.display_wishlist_view, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WishlistViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) { return; }

        final String id = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry._ID));
        final String name = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_NAME));
        final String description = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_DESCRIPTION));
        final String color = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_COLOR));
        final String skill = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_SKILL));
        final String rating = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_RATING));
        final String pic_URL = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_PICTURE_URL));

        // Strings are parsed into ArrayList<String>
        String ingredients = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_INGREDIENTS));
        String tastes = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_TASTES));
        String occassions = mCursor.getString(mCursor.getColumnIndex(WishlistContract.WishlistEntry.COLUMN_OCCASSIONS));
        final List<String> ingredientsList = Arrays.asList(ingredients.split("\\s*,\\s*"));
        final List<String> tastesList = Arrays.asList(tastes.split("\\s*,\\s*"));
        final List<String> occassionsList = Arrays.asList(occassions.split("\\s*,\\s*"));

        holder.mDrinkName.setText(name);
        holder.mDrinkRating.setText(rating);
        holder.mDrinkColor.setText(color);
        if(pic_URL != null){
            Picasso.with(mContext)
                    .load(pic_URL)
                    .into(holder.mDrinkpic);
        }

        // DONE: popupmenu, delete
        // TODO: implement - shop, share, refresh recyclerview upon delete
        holder.mDotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mDotButton);
                popupMenu.getMenuInflater().inflate(R.menu.wishlist_popup_action, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int menuId = item.getItemId();
                        switch(menuId) {
                            case R.id.wishlist_action_shop:
                                return true;
                            case R.id.wishlist_action_share:
                                return true;
                            case R.id.wishlist_action_delete:
                                Toast.makeText(mContext, "" + item.getGroupId(), Toast.LENGTH_SHORT).show();
                                removeWishlistItem(id);
                                //refresh recyclerview

                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

        // TODO: RATING DRINKS SHOULD BE PERSISTENT. SAVE TO DB.
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

        //making the cardview section clickable to see drink details
        holder.mCardview.setOnClickListener(new View.OnClickListener() {
            // NOTE: id of the DrinkItem is used to call the picture
            String test = "test";
            ArrayList<String> testArray = new ArrayList<String>(Arrays.asList(test, test, test));
            DrinkItem drinkItem = new DrinkItem(id, name, description, color, skill, rating, ingredientsList, occassionsList, tastesList);

            @Override
            public void onClick(View v) {
//                Log.d(TAG, "Clicked on " + drinkcount.getName() + " info");
                Intent intent = new Intent(mContext,DetailDrink.class);
                intent.putExtra("Drink Object",drinkItem);
                mContext.startActivity(intent);
            }
        });

        // TODO: BROWNIE POINTS-- undo delete item
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    private boolean removeWishlistItem(String id) {
        return mDb.delete(WishlistContract.WishlistEntry.TABLE_NAME, WishlistContract.WishlistEntry._ID + "='" + id + "'", null) > 0;
    }
}
