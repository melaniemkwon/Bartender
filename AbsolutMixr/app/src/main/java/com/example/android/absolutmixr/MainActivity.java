package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.absolutmixr.Model.DrinkItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<DrinkItem>>, FragSearch.OnDialogCloseListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mDrink;
    private AdapterDrink mAdapter;

    private static final int ADDB_LOADER = 111;
    /* A constant to save and restore the URL that is being displayed */
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private ViewPager viewPager;    // submenu for Material Design Tab Layout
    private TabLayout tabLayout;    // submenu for Material Design Tab Layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Made it here okay");
        setContentView(R.layout.activity_main);

        mDrink = (RecyclerView)findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mDrink.setLayoutManager(layoutManager);

        mDrink.setHasFixedSize(true);

        mAdapter= new AdapterDrink();

        mDrink.setAdapter(mAdapter);

        loadDrinkData(); //does nothing

        /*
         * Initialize the loader
         */
        getSupportLoaderManager().initLoader(ADDB_LOADER, null, this);

        Log.v(TAG, "Made it here okay");

        // Implement Material Design Tab Layout
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // ##### MENU #####
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        switch (itemNumber) {
            case R.id.search:
                // DONE: launch 'advanced search' fragment
                openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Open FragSearch fragment dialogue
    private void openSearch() {
        FragmentManager fm = getSupportFragmentManager();
        FragSearch fragSearch = new FragSearch();
        fragSearch.show(fm, "searchfragment");
    }
    // ##### END MENU #####

    // ##### MATERIAL DESIGN TAB #####
    public void setupViewPager(ViewPager viewPager){
        AdapterViewPager pagerAdapter = new AdapterViewPager(getSupportFragmentManager());
        pagerAdapter.addFragment(new FragCocktails(),"Cocktails");
        pagerAdapter.addFragment(new FragCabinet(),"Cabinet");
        pagerAdapter.addFragment(new FragWishlist(), "Wishlist");
        pagerAdapter.addFragment(new FragBAC(), "BAC Calc");
        viewPager.setAdapter(pagerAdapter);
    }
    // ##### END MATERIAL DESIGN TAB #####

    private void loadDrinkData(){
        // ##########################################################
        // Request that an AsyncTaskLoader performs the GET request
        URL addbSearchUrl = NetworkUtils.makeURL(); //TODO: MAKE THIS INCLUDE THE QUERY PARAMS

        Bundle queryBundle = new Bundle();

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> addbLoader = loaderManager.getLoader(ADDB_LOADER);
        if (addbLoader == null) {
            loaderManager.initLoader(ADDB_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(ADDB_LOADER, queryBundle, this);
        }
        // ##########################################################
    }

    private void displayDrinkData(){
        mDrink.setVisibility(View.VISIBLE);
        Log.v(TAG, "Made it to displayDrink");
    }

    // ##### AsyncTaskLoader #####
    //       Implement methods onCreateLoader, onLoadFinished, and onLoaderReset
    //       for LoaderManager.LoaderCallbacks<Void>
    @Override
    public Loader<ArrayList<DrinkItem>> onCreateLoader(int id, final Bundle args) {
        Log.d(TAG, "Calling AsyncTaskLoader");
        return new AsyncTaskLoader<ArrayList<DrinkItem>>(this) {

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

            displayDrinkData();
            mAdapter.setDrinkData(result);

            loadDrinkData();
        } else {
            Log.d(TAG, "nothing in result");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DrinkItem>> result) {}

    // ##### When FragSearch fragment closes, do api call and update recyclerview #####
    @Override
    public void closeDialog(String query) {
        // TODO: do api call and update recyclerview with asynctaskloader
        Log.d(TAG, "closeDialog call");
        loadDrinkData();
    }

    // DONE: replace AsyncTask with AsyncTaskLoader. To be deleted.
//    public class NetworkTask extends AsyncTask<URL, Void, ArrayList<DrinkItem>> {
//        String query;
//        NetworkTask (String s ){
//            query = s;
//        }
//
//        @Override
//        protected void onPreExecute(){
//            Log.v(TAG, "Made it here okay");
//            super.onPreExecute();
//        }
//
//        @Override
//        protected ArrayList<DrinkItem> doInBackground(URL... params) {
//            ArrayList<DrinkItem> result =null;
//            URL url = NetworkUtils.makeURL();
//            try{
//                String json =NetworkUtils.getResponseFromHttpUrl(url);
//                result = NetworkUtils.parseJSON(json);
//
//                return result;
//            }catch (IOException e){
//                e.printStackTrace();
//                return null;
//            }catch(JSONException e){
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute (ArrayList<DrinkItem> result){
//            super.onPostExecute(result);
//            Log.v(TAG, "Made it to onPost");
//            if(result !=null){
//                displayDrinkData();
//                mAdapter.setDrinkData(result);
//
//            }
//        }
//    }
}
