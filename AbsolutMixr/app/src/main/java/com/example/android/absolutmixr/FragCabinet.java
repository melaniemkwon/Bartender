package com.example.android.absolutmixr;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.absolutmixr.Model.Ingredient;
import com.example.android.absolutmixr.Model.IngredientsContract;
import com.example.android.absolutmixr.Model.IngredientsDBHelper;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragCabinet extends Fragment {
    private Button button;
    private Button refresh;
    private Button add;
    private RecyclerView rv;
    private IngredientsDBHelper helper;
    private Cursor cursor;
    private static SQLiteDatabase db;
    static Ingredient addIngredient;
    AdapterIngredients adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_cabinet,container,false);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddIngredientFragment frag = new AddIngredientFragment();
                frag.show(fm, "addingredientfragment");

            }
        });
        helper = new IngredientsDBHelper(getActivity());
        db = helper.getWritableDatabase();
        cursor = getAllItems(db);
        refresh = (Button) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


        rv = (RecyclerView) view.findViewById(R.id.ingredientRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);

        // Create and set adapter
        adapter= new AdapterIngredients(cursor, new AdapterIngredients.ItemClickListener() {

            @Override
            public void onItemClick(int id, String description, String name) {
                //launch fragment with item descriptions

            }
        });
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragcab","got to onstart");
        refresh();
        helper = new IngredientsDBHelper(getActivity());
        db = helper.getWritableDatabase();
        cursor = getAllItems(db);
        Log.d("Fragcab","got to onstart"+cursor.getCount());
        adapter = new AdapterIngredients(cursor, new AdapterIngredients.ItemClickListener() {

            @Override
            public void onItemClick(int id, String description, String name) {
                //launch fragment with item descriptions

            }
        });




        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeToDo(db, id);
                adapter.swapCursor(getAllItems(db));
            }
        }).attachToRecyclerView(rv);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }
    private Cursor getAllItems(SQLiteDatabase db) {
        return db.query(
                IngredientsContract.TABLE_INGREDIENTS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_NAME
        );
    }
    public static long addIngredient(String description, String name, String id, String type) {
        Log.d("Fragcab","reached addIngredient" + description);
        ContentValues cv = new ContentValues();
        cv.put(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_DESCRIPTION, description);
        cv.put(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_NAME, name);
        cv.put(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_ID, id);
        cv.put(IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_TYPE, type);
        return db.insert(IngredientsContract.TABLE_INGREDIENTS.TABLE_NAME, null, cv);
    }

    private boolean removeToDo(SQLiteDatabase db, long id) {
        return db.delete(IngredientsContract.TABLE_INGREDIENTS.TABLE_NAME, IngredientsContract.TABLE_INGREDIENTS._ID + "=" + id, null) > 0;
    }
    public static void setIngredient(Ingredient i){
        addIngredient(i.getDescription(), i.getName(), i.getId(), i.getType());
    }
    public void refresh(){
        adapter.swapCursor(getAllItems(db));
        adapter.notifyDataSetChanged();
    }

}
