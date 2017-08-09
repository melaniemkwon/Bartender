package com.example.android.absolutmixr;

import com.example.android.absolutmixr.Model.Ingredient;
import com.example.android.absolutmixr.Model.UPCIngredient;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Danny on 8/6/2017.
 */

public class IngredientSelectionTasks {
    public static ArrayList<Ingredient> getIngredients(int selection) throws IOException, JSONException {
        URL url = CabinetNetworkUtils.makeIngredientListURL();
        String response = CabinetNetworkUtils.getResponseFromHttpUrl(url);
        ArrayList<Ingredient> ingredients = CabinetNetworkUtils.parseJSON(response);

        String type = getType(selection);
        for (int i = ingredients.size()-1; i>-1;i--){
            Ingredient current = ingredients.get(i);
            if (!type.equals(current.getType())){
                ingredients.remove(i);
            }
        }

        return ingredients;
    }
    public static ArrayList<Ingredient> getAllIngredients() throws IOException, JSONException {
        URL url = CabinetNetworkUtils.makeIngredientListURL();
        String response = CabinetNetworkUtils.getResponseFromHttpUrl(url);
        ArrayList<Ingredient> ingredients = CabinetNetworkUtils.parseJSON(response);

        return ingredients;
    }
    public static ArrayList<UPCIngredient> getUPCIngredients(String upc) throws IOException, JSONException {
        URL url = CabinetNetworkUtils.makeUPCURL(upc);
        String response = CabinetNetworkUtils.getResponseFromHttpUrl(url);
        ArrayList<UPCIngredient> ingredients = CabinetNetworkUtils.parseUPCJSON(response);
        return ingredients;
    }
    public static Ingredient getIngredientFromUPC(String name, ArrayList<Ingredient> ing){
        for (int i = 0;i<ing.size();i++){
            if(name.contains(ing.get(i).getName())){
                return ing.get(i);
            }
        }return null;
    }
    public static String getType(int position){
        String result = "";
        switch(position){
            case 1:
                result = "mixers";
                break;
            case 2:
                result = "BaseSpirit";
                break;
            case 3:
                result = "brandy";
                break;
            case 4:
                result = "whisky";
                break;
            case 5:
                result = "gin";
                break;
            case 6:
                result = "vodka";
                break;
            case 7:
                result = "tequila";
                break;
            case 8:
                result = "rum";
                break;
            case 9:
                result = "spirits-other";
                break;
            case 10:
                result = "decoration";
                break;
            case 11:
                result = "fruits";
                break;
            case 12:
                result = "berries";
                break;
            case 13:
                result = "ice";
                break;
            case 14:
                result = "spices-herbs";
                break;
            case 15:
                result = "others";
                break;

        }

        return result;
    }
}
