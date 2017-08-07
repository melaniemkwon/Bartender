package com.example.android.absolutmixr;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.IngredientsContract;

/**
 * Created by Danny on 8/6/2017.
 */

public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.ItemHolder>{


        private Cursor cursor;
        private ItemClickListener listener;
        private String TAG = "ingredientlistadapter";

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("oncreateviewholder","reached");
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.ingredient_item, parent, false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {

            holder.bind(holder, position);
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        public interface ItemClickListener {
            void onItemClick(int pos, String id);
        }


        public AdapterIngredients(Cursor cursor, ItemClickListener listener) {
            this.cursor = cursor;
            this.listener = listener;
        }

        public void swapCursor(Cursor newCursor){
            if (cursor != null) cursor.close();
            cursor = newCursor;
            if (newCursor != null) {
                // Force the RecyclerView to refresh
                this.notifyDataSetChanged();
            }
        }



        class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;
            TextView desc;
            String ingredientTitle;
            String ingredientDesc;
            String ingredientId;

            long id;


            ItemHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);
                desc = (TextView) view.findViewById(R.id.desc);
                view.setOnClickListener(this);
            }




            public void bind(ItemHolder holder, int pos) {
                //Log.d("AdapterIngredients","reached bind"+pos);
                cursor.moveToPosition(pos);
                id = cursor.getLong(cursor.getColumnIndex(IngredientsContract.TABLE_INGREDIENTS._ID));
                ingredientDesc = cursor.getString(cursor.getColumnIndex(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_DESCRIPTION));
                ingredientTitle = cursor.getString(cursor.getColumnIndex(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_NAME));
                ingredientId = cursor.getString(cursor.getColumnIndex(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_ID));
                desc.setText(ingredientDesc);
                title.setText(ingredientTitle);
                holder.itemView.setTag(id);
            }



            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos, ingredientId);
            }


        }


    }


