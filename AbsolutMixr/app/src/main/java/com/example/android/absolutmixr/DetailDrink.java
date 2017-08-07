package com.example.android.absolutmixr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.DrinkItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leonard on 8/3/2017.
 */

public class DetailDrink extends AppCompatActivity{

    private static final String DRINK_HASHTAG = " #Drink Detail";
    private static final String TAG = DetailDrink.class.getSimpleName();
    public static final String Picture_url = "http://assets.absolutdrinks.com/drinks/solid-background-white/soft-shadow/floor-reflection/100x100/";
    private static final String type= ".png";
    private Context context;

    //private String display;
    private TextView mDrinkName;
    private ImageView image;
    private TextView description;
    private LinearLayout dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_drink);

        Log.d(TAG,"Made it to Detail Drink nicely.");

        //loading the views
        mDrinkName= (TextView)findViewById(R.id.dName);
        image = (ImageView)findViewById(R.id.imgdrink);
        description = (TextView)findViewById(R.id.description);
        dynamic = (LinearLayout)findViewById(R.id.dynamicText);

        //get the intent that was called
        Intent intentThatStartedThisActivity = getIntent();
        //insert the serializable data (our drinkItem object) to the drinkInfo of object type DrinkItem.
        DrinkItem drinkInfo = (DrinkItem) intentThatStartedThisActivity.getSerializableExtra("Drink Object");

        //setting the drinkInfo's ingredients to a list
        List<String> ingredients = drinkInfo.getIngredients();

        //setting the textview to the drink name
        mDrinkName.setText(drinkInfo.getName());
        //setting the image
        String url = Picture_url + (drinkInfo.getId()+type);
        Picasso.with(context)
                .load(url)
                .into(image);

        //setting the description
        description.setText("Description:\n"+drinkInfo.getDescription());


        for (int i=0;i<ingredients.size();i++){

            TextView m =new TextView(this);
            m.setText(ingredients.get(i));
            m.setId(i+3);
            dynamic.addView(m);
        }

    }
}
