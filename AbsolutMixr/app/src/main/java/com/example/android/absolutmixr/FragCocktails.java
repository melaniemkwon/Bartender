package com.example.android.absolutmixr;

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

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragCocktails extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<DrinkItem>> {

    private static final String TAG = FragCocktails.class.getSimpleName();
    private RecyclerView mDrink;
    private AdapterDrink mAdapter;
    private static final int ADDB_LOADER2 = 222;

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

            //not sure if loadDrinkData(); should go in the else
            //since if there is no data then it should load new data to the result by getting a
            //new json and parsing.
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
}
