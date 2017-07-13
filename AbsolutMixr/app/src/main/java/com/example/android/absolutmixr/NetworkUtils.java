package com.example.android.absolutmixr;

import android.net.Uri;

import com.example.android.absolutmixr.Model.DrinkItem;

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
 * Created by Leonard on 7/12/2017.
 */

public class NetworkUtils {
    //The url for the ADDb -> lists in JSON
    public static final String Base_Url = "https://addb.absolutdrinks.com/drinks/?apiKey=bb66369811204fb395a943c7008414df";
    //Will be used to call the picture to display using the id from Drink Item and add .png at the end
    public static final String Picture_url = "http://assets.absolutdrinks.com/drinks/transparent-background-white/soft-shadow/floor-reflection/100x200/";
    public static final String Query_Param = "q";

    public static URL makeURL (String searchQuery){
        Uri uri = Uri.parse(Base_Url).buildUpon()
                .appendQueryParameter(Query_Param,searchQuery).build();
        URL url = null;
        try {
            String urlString = uri.toString();
            url = new URL(uri.toString());

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //Passes a url sting and returns a JSON string
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return  scanner.next();
            }else{
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<DrinkItem> parseJSON(String json)throws JSONException {
        ArrayList<DrinkItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("result");
        //JSONArray skills = main.getJSONArray("skill");

        for(int i=0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);

            String id =item.getString("id");
            String name =item.getString("name");
            String description =item.getString("descriptionPlain");
            String skill =item.getString("skill");
            String color =item.getString("color");
            int rating =item.getInt("rating");

            DrinkItem drink = new DrinkItem(id,name,description,color,skill,rating);
            result.add(drink);
        }
        return result;
    }

}
