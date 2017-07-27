package com.example.android.absolutmixr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mDrink;
    private AdapterDrink mAdapter;
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

        loadDrinkData();
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
                // TODO: launch 'advanced search' fragment
                return true;
        }

        return super.onOptionsItemSelected(item);
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
        displayDrinkData();
        NetworkTask task = new NetworkTask("");
        task.execute();
    }

    private void displayDrinkData(){
        mDrink.setVisibility(View.VISIBLE);
        Log.v(TAG, "Made it to displayDrink");
    }

    public class NetworkTask extends AsyncTask<URL, Void, ArrayList<DrinkItem>> {
        String query;
        NetworkTask (String s ){
            query = s;
        }

        @Override
        protected void onPreExecute(){
            Log.v(TAG, "Made it here okay");
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DrinkItem> doInBackground(URL... params) {
            ArrayList<DrinkItem> result =null;
            URL url = NetworkUtils.makeURL();
            try{
                String json =NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);

                return result;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }catch(JSONException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute (ArrayList<DrinkItem> result){
            super.onPostExecute(result);
            Log.v(TAG, "Made it to onPost");
            if(result !=null){
                displayDrinkData();
                mAdapter.setDrinkData(result);

            }
        }
    }
}
