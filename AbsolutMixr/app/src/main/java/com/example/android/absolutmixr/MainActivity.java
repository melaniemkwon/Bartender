package com.example.android.absolutmixr;




import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;


public class MainActivity extends AppCompatActivity implements FragSearch.OnDialogCloseListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;    // submenu for Material Design Tab Layout
    private TabLayout tabLayout;    // submenu for Material Design Tab Layout

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Made it here okay");
        setContentView(R.layout.activity_main);
        Log.v(TAG, "Made it here okay");

        // Implement Material Design Tab Layout
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Twitter
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("IdjL7LOKwuss74mm8HBlARp5A", "XXrwAdFQwr61oqOY8tZ3be9XUTWNSz0IcH4CbNAIaFkAIRRfDQ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
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
    // TODO: melanie-- make these scrollable...
    // https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    public void setupViewPager(ViewPager viewPager){
        AdapterViewPager pagerAdapter = new AdapterViewPager(getSupportFragmentManager());
        pagerAdapter.addFragment(new FragCocktails(),"Cocktails");
        pagerAdapter.addFragment(new FragCabinet(),"Cabinet");
        pagerAdapter.addFragment(new FragWishlist(), "Wishlist");
        pagerAdapter.addFragment(new FragBAC(), "BAC Calc");
        viewPager.setAdapter(pagerAdapter);
    }
    // ##### END MATERIAL DESIGN TAB #####

    // ##### When FragSearch fragment closes, do api call and update recyclerview #####
    @Override
    public void closeDialog(String query) {
        // TODO: do api call with query params and update recyclerview with asynctaskloader
        Log.d(TAG, "closeDialog call");
//        loadDrinkData();
    }

    // DONE: replace AsyncTask with AsyncTaskLoader (which is implemented in FragCocktails)

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void dispatchChoosePictureIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQUEST_IMAGE_GALLERY);
    }
}