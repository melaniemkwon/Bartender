package com.example.android.absolutmixr;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.android.absolutmixr.Model.DrinkItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mDrink;
    private AdapterDrink mAdapter;

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

        Log.v(TAG, "Made it here okay");
        loadDrinkData();
    }

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
            URL url = NetworkUtils.makeURL(query);
            try{
                String json =NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            }catch (IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute (ArrayList<DrinkItem> result){
            super.onPostExecute(result);
            Log.v(TAG, "Made it to onPost");
            if(result !=null){
                mAdapter.setDrinkData(result);
                displayDrinkData();
            }else{

            }
        }
    }
}
