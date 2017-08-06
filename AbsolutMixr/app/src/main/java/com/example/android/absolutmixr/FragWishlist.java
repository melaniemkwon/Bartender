package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.absolutmixr.Model.DrinkItem;

import java.util.ArrayList;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragWishlist extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<DrinkItem>> {

    private static final String TAG = FragWishlist.class.getSimpleName();
    private RecyclerView mDrink;
    private AdapterDrink mAdapter;
    private static final int ADDB_LOADER2 = 333;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wishlist,container,false);

        // Get reference to RecyclerView
        mDrink = (RecyclerView) view.findViewById(R.id.recycler_wishlist);

        // Set layout manager
        mDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrink.setHasFixedSize(true);

        // Create and set adapter
        mAdapter= new AdapterDrink();
        mDrink.setAdapter(mAdapter);

        //loadWishlist();

        getActivity().getSupportLoaderManager().initLoader(ADDB_LOADER2, null, this);

        return view;
    }

    @Override
    public Loader<ArrayList<DrinkItem>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DrinkItem>> loader, ArrayList<DrinkItem> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DrinkItem>> loader) {

    }
}
