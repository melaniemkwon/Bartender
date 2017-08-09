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

import static android.provider.BaseColumns._ID;
import static com.example.android.absolutmixr.Model.WishlistContract.WishlistEntry.TABLE_NAME;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragWishlist extends Fragment {

    private static final String TAG = FragWishlist.class.getSimpleName();
    private RecyclerView mDrink;
    private AdapterWishlist mAdapter;
    private SQLiteDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wishlist,container,false);

        // Get reference to RecyclerView
        mDrink = (RecyclerView) view.findViewById(R.id.recycler_wishlist);

        // Set layout manager
        mDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrink.setHasFixedSize(true);

        // Create WishlistDbHelper instance
        WishlistDbHelper dbHelper = new WishlistDbHelper(this.getContext());
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllWishlist();

        // DONE: MAKE NEW ADAPTER FOR WISHLIST
        mAdapter = new AdapterWishlist(this.getContext(), cursor);
        mDrink.setAdapter(mAdapter);

        return view;
    }

    private Cursor getAllWishlist() {
        return mDb.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WishlistContract.WishlistEntry.COLUMN_NAME
        );
    }

    private boolean removeWishlistItem(long id) {
        return mDb.delete(TABLE_NAME, _ID + "='" + id + "'", null) > 0;
    }
}
