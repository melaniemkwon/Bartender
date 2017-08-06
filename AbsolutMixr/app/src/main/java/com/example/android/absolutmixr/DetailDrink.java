package com.example.android.absolutmixr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.DrinkItem;

/**
 * Created by Leonard on 8/3/2017.
 */

public class DetailDrink extends AppCompatActivity{

    private static final String DRINK_HASHTAG = " #Drink Detail";
    private static final String TAG = DetailDrink.class.getSimpleName();

    //private String display;
    private TextView mDrinkDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_drink);

        Log.d(TAG,"Made it to Detail Drink nicely.");

        mDrinkDisplay= (TextView)findViewById(R.id.deatail);

        //get the intent that was called
        Intent intentThatStartedThisActivity = getIntent();
        //insert the serializable data (our drinkItem object) to the drinkInfo of object type DrinkItem.
        DrinkItem drinkInfo = (DrinkItem) intentThatStartedThisActivity.getSerializableExtra("Drink Object");

        //setting the textview to the drink name
        mDrinkDisplay.setText(drinkInfo.getName());
    }
}
