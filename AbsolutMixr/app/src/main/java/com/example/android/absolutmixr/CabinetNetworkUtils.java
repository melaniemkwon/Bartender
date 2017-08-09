package com.example.android.absolutmixr;

import android.net.Uri;
import android.util.Log;

import com.example.android.absolutmixr.Model.Ingredient;
import com.example.android.absolutmixr.Model.UPCIngredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Danny on 7/31/2017.
 */

public class CabinetNetworkUtils {
    public static final String TAG = "NetworkUtilities";
    public static final String INGREDIENTS_BASE_URL = "http://addb.absolutdrinks.com/ingredients/";
    public static final String PARAM_START = "start";
    public static final String PARAM_PAGESIZE = "pageSize";
    public static final String PARAM_APIKEY = "apiKey";
    //TODO:replace APIKEY with working key
    public static final String APIKEY = "bb66369811204fb395a943c7008414df";
    public static final String START = "0";
    public static final String PAGESIZE = "484";
    public static final String UPC_BASE_URL = "https://api.upcitemdb.com/prod/trial/lookup";
    public static final String PARAM_UPC = "upc";

    public static URL makeIngredientListURL(){
        Uri uri = Uri.parse(INGREDIENTS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_APIKEY,APIKEY)
                .appendQueryParameter(PARAM_START,START)
                .appendQueryParameter(PARAM_PAGESIZE,PAGESIZE).build();
        URL url =null;
        try{
            String urlString = uri.toString();
            Log.d(TAG, "Url:" + urlString);
            url = new URL(uri.toString());

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static URL makeUPCURL(String upc){
        Uri uri = Uri.parse(UPC_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_UPC,upc).build();
        URL url =null;
        try{
            String urlString = uri.toString();
            Log.d(TAG, "Url:" + urlString);
            url = new URL(uri.toString());

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection2 = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection2.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection2.disconnect();
        }
    }

    public static ArrayList<Ingredient> parseJSON(String json) throws JSONException {
        ArrayList<Ingredient> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray ingredients = main.getJSONArray("result");

        for(int i = 0; i < ingredients.length(); i++){
            JSONObject item = ingredients.getJSONObject(i);
            String type = item.getString("type");
            String description = item.getString("description");
            String name = item.getString("name");
            String id = item.getString("id");
            Ingredient repo = new Ingredient(name,id, type, description);
            result.add(repo);
        }
        return result;
    }
    public static ArrayList<UPCIngredient> parseUPCJSON(String json) throws JSONException {
        ArrayList<UPCIngredient> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("items");

        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            String title = item.getString("title");
            String description = item.getString("description");
            UPCIngredient repo = new UPCIngredient(title, description);
            result.add(repo);
        }
        return result;
    }
}
