package com.example.android.absolutmixr;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import static com.example.android.absolutmixr.Model.WishlistContract.WishlistEntry.TABLE_NAME;

/**
 * Created by Leonard on 7/17/2017.
 */

public class AdapterDrink extends RecyclerView.Adapter<AdapterDrink.AdapterDrinkViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DrinkItem> data;
    private static final String TAG = AdapterDrink.class.getSimpleName();
    public static final String Picture_url = "http://assets.absolutdrinks.com/drinks/solid-background-white/soft-shadow/floor-reflection/100x100/";
    private static final String type= ".png";
    public static final String thumbsRatingUp = "THUMBSUP", thumbsRatingDown = "THUMBSDOWN", thumbsRatingNone = "NONE";
    private SQLiteDatabase mDb;


    //Constructor is default because we will change when we implement search function
    public AdapterDrink(){
        Log.v(TAG, "Made it to the constructor okay");
    }

    public class AdapterDrinkViewHolder extends RecyclerView.ViewHolder{
        public final TextView mDrinkName;
        public final TextView mDrinkRating;
        //public final TextView mDrinkColor;
        public final TextView mDrinkTastes;
        public final ImageView mDrinkpic;
        public final CheckBox mCheck;

        //instantiating
        public final CardView linearLayout;

        //creating the TextView to make it easier to bind in the onBindViewHolder
        public AdapterDrinkViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.name);
            mDrinkRating = (TextView) view.findViewById(R.id.rating);
            //mDrinkColor = (TextView) view.findViewById(R.id.color);
            mDrinkTastes = (TextView) view.findViewById(R.id.tastes);
            mDrinkpic = (ImageView) view.findViewById(R.id.drinkImage);
            mCheck = (CheckBox) view.findViewById(R.id.starCheck);

            //creating the layout and getting it
            linearLayout = (CardView) view.findViewById(R.id.clickable);
        }
    }

    @Override
    public AdapterDrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        // Database for writing to Wishlist
        WishlistDbHelper dbHelper = new WishlistDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        int layoutIdForListItem = R.layout.display_list_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        return new AdapterDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDrinkViewHolder holder, int position) {
        final DrinkItem drinkcount = data.get(position);

        //Logs to see if it can make it here used for debugging.
        Log.v(TAG, "Made it to onBind name");
        //Log.v(TAG, drinkcount.getName());
        final String url = Picture_url + (drinkcount.getId()+type);
        Log.v(TAG, "Built URI " + url +")(*&^%$#@!)(*&^%$#@*&^%$#@*&^%$#@)(*&^%$#@(*&^%$#@)(*&^%$#@");

        //Binding the info from the arraylist to the Textview.
        holder.mDrinkName.setText(drinkcount.getName());
        holder.mDrinkRating.setText("Rating: "+(drinkcount.getRating()));
        //holder.mDrinkColor.setText("Drink Color: "+drinkcount.getColor());
        String tastes = android.text.TextUtils.join(",", drinkcount.getTastes());
        holder.mDrinkTastes.setText(tastes);
        if(url != null){
            Picasso.with(context)
                    .load(url)
                    .into(holder.mDrinkpic);
        }

        // If drinkid exists in wishlist db, mark the star checkbox as checked
        holder.mCheck.setChecked(existsInWishlist( drinkcount.getId() ));

        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                boolean checked = ((CheckBox)v).isChecked();
                String check = drinkcount.getName();

                if(checked == true){
                    // Group ingredients, tastes, occassions into comma separated string.
                    String ingredients = android.text.TextUtils.join(",", drinkcount.getIngredients());
                    String occassions = android.text.TextUtils.join(",", drinkcount.getOccassions());
                    String tastes = android.text.TextUtils.join(",", drinkcount.getTastes());

                    // Add drink item to wishlist db
                    Log.d(TAG, "ADDING DRINK");
                    addToWishlist(drinkcount.getId(), drinkcount.getName(), drinkcount.getDescription(), drinkcount.getColor(), drinkcount.getSkill(), drinkcount.getRating(), url, ingredients, occassions, tastes);

                    check = check + " added to wishlist";
                    Toast toast = Toast.makeText(context,check,duration);
                    toast.show();
                }
                else{
                    // Delete drink item to wishlist db
                    removeWishlistItem(drinkcount.getId());
                    check =  check+ " removed from wishlist";
                    Toast toast = Toast.makeText(context,check,duration);
                    toast.show();
                }
            }
        });

        //making the layout section clickable
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "Clicked on " + drinkcount.getName() + " info");
                Intent intent = new Intent(context,DetailDrink.class);
                intent.putExtra("Drink Object",drinkcount);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null == data)return 0;
        return data.size();
    }

    public void setDrinkData(ArrayList<DrinkItem> dat){
        data = dat;
        Log.d(TAG, "setDrinkData called");
        //Log.d(TAG, dat.get(0).toString());
        notifyDataSetChanged();
    }

    // ########## WISHLIST DB METHODS ##########

    // Helper method to determine if drinkid exists in wishlist db
    // @returns true if star checkbox should be checked
    private boolean existsInWishlist(String id) {
        Cursor cursor = getAllWishlist();
        List drinkIds = new ArrayList<>();

        while (cursor.moveToNext()) {
            String drinkId = cursor.getString(cursor.getColumnIndexOrThrow(WishlistContract.WishlistEntry._ID));
            drinkIds.add(drinkId);
        }

        cursor.close();

        if (drinkIds.contains( id )) { return true; }
        return false;
    }

    private Cursor getAllWishlist() {
        return mDb.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WishlistContract.WishlistEntry._ID
        );
    }

    private long addToWishlist(String id, String name, String desc, String color, String skill, String rating, String pic, String ingreds, String occs, String tastes) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(WishlistContract.WishlistEntry._ID, id);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_NAME, name);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_DESCRIPTION, desc);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_COLOR, color);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_SKILL, skill);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_RATING, rating);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_PICTURE_URL, pic);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_INGREDIENTS, ingreds);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_OCCASSIONS, occs);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_TASTES, tastes);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_THUMBSUP, thumbsRatingNone);

        return mDb.insert(WishlistContract.WishlistEntry.TABLE_NAME, null, contentValues);
    }

    private boolean removeWishlistItem(String id) {
        return mDb.delete(WishlistContract.WishlistEntry.TABLE_NAME, WishlistContract.WishlistEntry._ID + "='" + id + "'", null) > 0;
    }

    // ########## END WISHLIST DB METHODS ##########
}
