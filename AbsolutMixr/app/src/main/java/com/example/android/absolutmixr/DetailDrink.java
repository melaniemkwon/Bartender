package com.example.android.absolutmixr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
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
    private TextView color;
    private TextView rating;
    private TextView skill;

    private LinearLayout dynamic;
    private LinearLayout dynamicTaste;
    private LinearLayout dynamicOcc;

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
        dynamicTaste = (LinearLayout)findViewById(R.id.dynamicTaste);
        dynamicOcc = (LinearLayout)findViewById(R.id.dynamicOccasion);
        color = (TextView)findViewById(R.id.deatailColor);
        rating = (TextView)findViewById(R.id.detailkRating);
        skill = (TextView)findViewById(R.id.detailSkill);

        //get the intent that was called
        Intent intentThatStartedThisActivity = getIntent();
        //insert the serializable data (our drinkItem object) to the drinkInfo of object type DrinkItem.
        DrinkItem drinkInfo = (DrinkItem) intentThatStartedThisActivity.getSerializableExtra("Drink Object");

        //setting the drinkInfo's arrays to thier respective list
        List<String> ingredients = drinkInfo.getIngredients();
        List<String> occasions = drinkInfo.getOccassions();
        List<String> tastes = drinkInfo.getTastes();

        //Log.v(TAG,occasions.get(0));

        //setting the textview to the drink name
        mDrinkName.setText(drinkInfo.getName());

        //setting the image
        String url = Picture_url + (drinkInfo.getId()+type);
        Picasso.with(context)
                .load(url)
                .into(image);

        //setting the description
        Spanned desc = Html.fromHtml("<b>Instructions:</b><br/>" +drinkInfo.getDescription());
        //description.setText("Description:\n"+drinkInfo.getDescription());
        description.setText(desc);

        //dynamically adding the list of ingredients to the linear view named dynamic
        for (int i=0;i<ingredients.size();i++){
            TextView m =new TextView(this);
            m.setText(ingredients.get(i));
            m.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            m.setId(i+3);
            dynamic.addView(m);
        }

        //dynamically adding the list of ingredients to the linear view named dynamicT
        if(tastes.size() == 0){
            TextView o =new TextView(this);
            o.setText("Has no specific taste");
            o.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            dynamicOcc.addView(o);
        }
        else {
            for (int l = 0; l < tastes.size(); l++) {
                TextView t = new TextView(this);
                t.setText(tastes.get(l));
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                t.setId(l + 8);
                Log.v(TAG, "setting into tastes: " + tastes.get(l));
                dynamicTaste.addView(t);
            }
        }

        //dynamically adding the list of ingredients to the linear view named dynamicO
        if(occasions.size() == 0){
            TextView o =new TextView(this);
            o.setText("For all occasions");
            o.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            dynamicOcc.addView(o);
        }

        else {
            for (int j = 0; j < occasions.size(); j++) {
                TextView o = new TextView(this);
                o.setText(occasions.get(j));
                o.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                o.setId(j + 4);
                dynamicOcc.addView(o);
            }
        }

        //Using spanned and html formating to only format the beginning of the text
        Spanned c = Html.fromHtml("<b>Color: </b>"+drinkInfo.getColor());
        color.setText(c);
        Spanned r = Html.fromHtml("<b>Rating: </b>"+drinkInfo.getRating());
        rating.setText(r);
        Spanned s = Html.fromHtml("<b>Skill: </b>"+drinkInfo.getSkill());
        skill.setText(s);

    }
}
