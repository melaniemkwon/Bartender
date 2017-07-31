package com.example.android.absolutmixr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.DrinkItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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


    //Constructor is default because we will change when we implement search function
    public AdapterDrink(){
        Log.v(TAG, "Made it to the constructor okay");
    }

    public class AdapterDrinkViewHolder extends RecyclerView.ViewHolder{
        public final TextView mDrinkName;
        public final TextView mDrinkRating;
        public final TextView mDrinkColor;
        public final ImageView mDrinkpic;

        //creating the TextView to make it easier to bind in the onBindViewHolder
        public AdapterDrinkViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.name);
            mDrinkRating = (TextView) view.findViewById(R.id.rating);
            mDrinkColor = (TextView) view.findViewById(R.id.color);
            mDrinkpic = (ImageView) view.findViewById(R.id.drinkImage);
        }


    }

    @Override
    public AdapterDrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.display_list_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        return new AdapterDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDrinkViewHolder holder, int position) {
        DrinkItem drinkcount = data.get(position);

        //Logs to see if it can make it here used for debugging.
        Log.v(TAG, "Made it to onBind name");
        Log.v(TAG, drinkcount.getName());
        String url = Picture_url + (drinkcount.getId()+type);
        Log.v(TAG, "Built URI " + url +")(*&^%$#@!)(*&^%$#@*&^%$#@*&^%$#@)(*&^%$#@(*&^%$#@)(*&^%$#@");

        //Binding the info from the arraylist to the Textview.
        holder.mDrinkName.setText(drinkcount.getName());
        holder.mDrinkRating.setText((drinkcount.getRating()));
        holder.mDrinkColor.setText(drinkcount.getColor());
        if(url != null){
            Picasso.with(context)
                    .load(url)
                    .into(holder.mDrinkpic);
        }
    }

    @Override
    public int getItemCount() {
        if(null == data)return 0;
        return data.size();
    }

    public void setDrinkData(ArrayList<DrinkItem> dat){
        data = dat;
        Log.d(TAG, "setDrinkData called");
        Log.d(TAG, dat.get(0).toString());
        notifyDataSetChanged();
    }
}
