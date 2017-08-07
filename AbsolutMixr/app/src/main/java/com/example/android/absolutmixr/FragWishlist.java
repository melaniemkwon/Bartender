package com.example.android.absolutmixr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.absolutmixr.Model.DrinkItem;
import com.example.android.absolutmixr.Model.WishlistContract;
import com.example.android.absolutmixr.Model.WishlistDbHelper;

import java.util.ArrayList;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragWishlist extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<DrinkItem>> {

    private static final String TAG = FragWishlist.class.getSimpleName();
    private RecyclerView mDrink;
    private AdapterWishlist mAdapter;
    private SQLiteDatabase mDb;

    private static final int ADDB_LOADER3 = 333;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wishlist,container,false);

        // Get reference to RecyclerView
        mDrink = (RecyclerView) view.findViewById(R.id.recycler_wishlist);

        // Set layout manager
        mDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrink.setHasFixedSize(true);

        // Create and set adapter
//        mAdapter= new AdapterDrink();
        mDrink.setAdapter(mAdapter);

        // Create WishlistDbHelper instance
        WishlistDbHelper dbHelper = new WishlistDbHelper(this.getContext());
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllWishlist();
        // TODO: MAKE NEW ADAPTER FOR WISHLIST
//        mAdapter = new AdapterDrink(this, cursor.getCount());

//        loadWishlist();

        getActivity().getSupportLoaderManager().initLoader(ADDB_LOADER3, null, this);

        return view;
    }

    @Override
    public Loader<ArrayList<DrinkItem>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<DrinkItem>>(this.getContext()) {

            @Override
            public ArrayList<DrinkItem> loadInBackground() {
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DrinkItem>> loader, ArrayList<DrinkItem> data) {
        if(data != null){
            mAdapter.setDrinkData(data);
        } else {
            Log.d(TAG, "No drinks in wishlist.");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DrinkItem>> loader) {}

    public void loadWishlist() {
        // TODO: load all saved wishlist drinks to adapter
    }

    private Cursor getAllWishlist() {
        return mDb.query(
                WishlistContract.WishlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WishlistContract.WishlistEntry.COLUMN_NAME
        );
    }
}
