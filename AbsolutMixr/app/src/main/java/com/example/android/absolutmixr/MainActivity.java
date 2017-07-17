package com.example.android.absolutmixr;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.android.absolutmixr.Model.DrinkItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private RecyclerView mDrink;
    private AdapterDrink mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
    }

    public class NetworkTask extends AsyncTask<URL, Void, ArrayList<DrinkItem>> {
        String query;

        NetworkTask (String s ){
            query = s;
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
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute (final ArrayList<DrinkItem> result){
            //super.onPostExecute(result);
            mDrink = (RecyclerView)findViewById(R.id.recycler);
            mAdapter = new AdapterDrink(MainActivity.this,result);
            mDrink.setAdapter(mAdapter);
            mDrink.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }


    }
}
