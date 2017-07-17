package com.example.android.absolutmixr;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.DrinkItem;

import java.util.ArrayList;

public class DisplayList extends RecyclerView.Adapter<DisplayList.ItemHolder> {


    private ArrayList<DrinkItem> data;

    public DisplayList(ArrayList<DrinkItem> data){
        this.data = data;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImediately = false;

        View view = inflater.inflate(R.layout.display_list_view,parent, shouldAttachToParentImediately);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView rating;
        TextView color;

        ItemHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            rating = (TextView)view.findViewById(R.id.rating);
            color = (TextView)view.findViewById(R.id.color);
        }

        public void bind (int pos){
            DrinkItem drink = data.get(pos);
            name.setText(drink.getName());
            rating.setText(drink.getRating());
            color.setText(drink.getColor());
        }
    }
}
