package com.example.android.absolutmixr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.DrinkItem;

import java.util.ArrayList;

/**
 * Created by Leonard on 7/17/2017.
 */

public class AdapterDrink extends RecyclerView.Adapter<AdapterDrink.AdapterDrinkViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DrinkItem> data;
    private static final String TAG = AdapterDrink.class.getSimpleName();


    //Constructor is default because we will change when we implement search function
    public AdapterDrink(){
        Log.v(TAG, "Made it to the constructor okay");
    }

    public class AdapterDrinkViewHolder extends RecyclerView.ViewHolder{
        public final TextView mDrinkName;
        public final TextView mDrinkRating;
        public final TextView mDrinkColor;

        public AdapterDrinkViewHolder(View view){
            super(view);
            mDrinkName = (TextView) view.findViewById(R.id.name);
            mDrinkRating = (TextView) view.findViewById(R.id.rating);
            mDrinkColor = (TextView) view.findViewById(R.id.color);
        }


    }

    @Override
    public AdapterDrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.display_list_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmedietly = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmedietly);
        return new AdapterDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDrinkViewHolder holder, int position) {
        DrinkItem drinkcount = data.get(position);

        Log.v(TAG, "Made it to onBind name");

        Log.v(TAG, drinkcount.getName());
        //Log.v(TAG, );

        holder.mDrinkName.setText(drinkcount.getName());
        holder.mDrinkRating.setText((drinkcount.getDescription()));
        holder.mDrinkColor.setText(drinkcount.getColor());
    }

    @Override
    public int getItemCount() {
        if(null == data)return 0;
        return data.size();
    }

    public void setDrinkData(ArrayList<DrinkItem> dat){
        data = dat;
        notifyDataSetChanged();
    }
}
