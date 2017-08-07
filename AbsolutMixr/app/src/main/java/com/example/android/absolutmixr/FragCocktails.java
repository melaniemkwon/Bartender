package com.example.android.absolutmixr;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.android.absolutmixr.Model.DrinkItem;
import com.example.android.absolutmixr.Model.WishlistContract;
import com.example.android.absolutmixr.Model.WishlistDbHelper;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragCocktails extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<DrinkItem>>,View.OnClickListener {

    private static final String TAG = FragCocktails.class.getSimpleName();
    private RecyclerView mDrink;
    private AdapterDrink mAdapter;
    private static final int ADDB_LOADER2 = 222;
    private SQLiteDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cocktails,container,false);

        // Get reference to RecyclerView
        mDrink = (RecyclerView) view.findViewById(R.id.recycler2);

        // Set layout manager
        mDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrink.setHasFixedSize(true);

        // Create and set adapter
        mAdapter= new AdapterDrink();
        mDrink.setAdapter(mAdapter);

        loadDrinkData();

        getActivity().getSupportLoaderManager().initLoader(ADDB_LOADER2, null, this);

        // Database for writing to Wishlist
        WishlistDbHelper dbHelper = new WishlistDbHelper(this.getContext());
        mDb = dbHelper.getWritableDatabase();

        return view;
    }

    // ##### AsyncTaskLoader #####
    //       Implement methods onCreateLoader, onLoadFinished, and onLoaderReset
    //       for LoaderManager.LoaderCallbacks<Void>
    @Override
    public Loader<ArrayList<DrinkItem>> onCreateLoader(int id, final Bundle args) {
        Log.d(TAG, "Calling AsyncTaskLoader");
        return new AsyncTaskLoader<ArrayList<DrinkItem>>(this.getContext()) {

            ArrayList<DrinkItem> result;

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                if (result != null) {
                    deliverResult(result);
                } else {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<DrinkItem> loadInBackground() {

                URL url = NetworkUtils.makeURL();

                try {
                    String json = NetworkUtils.getResponseFromHttpUrl(url);
                    result = NetworkUtils.parseJSON(json);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch(JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(ArrayList<DrinkItem> data) {
                super.deliverResult(data);
                result = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DrinkItem>> loader, ArrayList<DrinkItem> result) {
        if(result != null){
            Log.d(TAG, "calling setDrinkData");

//            displayDrinkData();
            mAdapter.setDrinkData(result);

            //loadDrinkData();
        } else {
            Log.d(TAG, "nothing in result");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DrinkItem>> result) {}

    public void loadDrinkData(){
        // ##########################################################
        // Request that an AsyncTaskLoader performs the GET request
        URL addbSearchUrl = NetworkUtils.makeURL(); //TODO: MAKE THIS INCLUDE THE QUERY PARAMS

        Bundle queryBundle = new Bundle();
//        queryBundle.putString(,);

        // Give the LoaderManager an ID and it returns a loader (if one exists)
        // If it doesn't exist, tell the LoaderManager to create one
        // If it does exist, tell the LoaderManager to restart it
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> addbLoader = loaderManager.getLoader(ADDB_LOADER2);
        if (addbLoader == null) {
            loaderManager.initLoader(ADDB_LOADER2, queryBundle, this);
        } else {
            loaderManager.restartLoader(ADDB_LOADER2, queryBundle, this);
        }
        // ##########################################################
    }

    @Override
    public void onClick(View v) {
        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        boolean checked = ((CheckBox)v).isChecked();

        if(checked == true){
            Toast toast = Toast.makeText(context,"added to wishlist",duration);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(context,"added to wishlist",duration);
            toast.show();
        }
    }

    // note: may need to remove some extraneous parameters....
    private long addToWishlist(int id, String name, String desc, String color, String skill, String rating, String pic) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(WishlistContract.WishlistEntry._ID, id);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_NAME, name);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_DESCRIPTION, desc);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_COLOR, color);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_SKILL, skill);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_RATING, rating);
        contentValues.put(WishlistContract.WishlistEntry.COLUMN_PICTURE_URL, pic);

        return mDb.insert(WishlistContract.WishlistEntry.TABLE_NAME, null, contentValues);
    }
}
