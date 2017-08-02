package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


//public class AdvSearch extends AppCompatActivity {
//    private Button mBGoBack;
//    String[] drinkContentArray = {"Rum", "Vodka", "Tequila", "Whiskey"};
//    String[] tasteArray = {"Sweet", "Bitter", "Fruity", "Herb", "Spicy"};
//    String[] skillArray = {"Average", "Difficult"};
//    String[] glassArray = {"Cocktail Glass", "Rocks Glass"};
//    String[] timeArray = {"Afternoon drinks", "Evening drinks"};
//
//    protected static HashSet<String> allTastes = new HashSet<String>();
//    protected static HashSet<String> allSkills = new HashSet<String>();
//    protected static HashSet<String> allGlasses = new HashSet<String>();
//    protected static HashSet<String> allTimes = new HashSet<String>();
//
//    ArrayList<DrinkItem> drinks;
//            //IsAlcoholic, isCarbonated
//
//    @Override
//    protected void onCreate(Bundle savedInstance){
//        super.onCreate(savedInstance);
//
//        setContentView(R.layout.advanced_search);
//        Spinner spinneDrinkContent = (Spinner) findViewById(R.id.drink_content_spinner);
//        Spinner spinnerTaste = (Spinner) findViewById(R.id.drink_taste_spinner);
//        Spinner spinnerSkill = (Spinner) findViewById(R.id.drink_skill_spinner);
//        Spinner spinnerGlass = (Spinner) findViewById(R.id.drink_glass_spinner);
//        Spinner spinnerTime = (Spinner) findViewById(R.id.drink_time_spinner);
//
//
//
//        mBGoBack = (Button) findViewById(R.id.bt_go_back);
//
//        mBGoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, drinkContentArray);
//        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinneDrinkContent.setAdapter(arrayAdapter1);
//
//        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, allTastes.toArray());
//        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTaste.setAdapter(arrayAdapter2);
//
//        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, skillArray);
//        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSkill.setAdapter(arrayAdapter3);
//
//
//
//    }
//
//
//}



public class AdvSearch extends Fragment {

    private static final String TAG = AdvSearch.class.getSimpleName();

    private EditText editTextContent;
    private Spinner spinnerTaste;
    private Spinner spinnerSkill;
    private Spinner spinnerGlass;
    private Spinner spinnerTime;
    private Spinner spinnerIsAlcoholic;


    protected static HashSet<String> allTastes = new HashSet<String>();
    protected static HashSet<String> allSkills = new HashSet<String>();
    protected static HashSet<String> allGlasses = new HashSet<String>();
    protected static HashSet<String> allTimes = new HashSet<String>();
    protected static HashSet<String> allAlcoholic = new HashSet<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advanced_search,container,false);

        editTextContent = (EditText) view.findViewById(R.id.drink_content_edit_text);
        spinnerTaste = (Spinner) view.findViewById(R.id.drink_taste_spinner);
        spinnerSkill = (Spinner) view.findViewById(R.id.drink_skill_spinner);
        spinnerGlass = (Spinner) view.findViewById(R.id.drink_glass_spinner);
        spinnerTime = (Spinner) view.findViewById(R.id.drink_time_spinner);
        spinnerIsAlcoholic = (Spinner) view.findViewById(R.id.drink_is_alcoholic_spinner);

        addAllOptionToSets();
        setSpinners();

        return view;
    }

    private void addAllOptionToSets(){
        allTastes.add("-All Drinks-");
        allSkills.add("-All Drinks-");
        allGlasses.add("-All Drinks-");
        allTimes.add("-All Drinks-");
        allAlcoholic.add("-All Drinks-");
    }

    private Object[] sortSet(HashSet<String> set){
        List<String> sortedList = new ArrayList<String>(set);
        Collections.sort(sortedList);
        return sortedList.toArray();
    }

    private void setSpinners(){

        //ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, allGlasses.toArray());
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(allGlasses));
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGlass.setAdapter(arrayAdapter1);

        //ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, allTastes.toArray());
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(allTastes));
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaste.setAdapter(arrayAdapter2);

        //ArrayAdapter arrayAdapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, allSkills.toArray());
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(allSkills));
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSkill.setAdapter(arrayAdapter3);

        //ArrayAdapter arrayAdapter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, allTimes.toArray());
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(allTimes));
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(arrayAdapter4);

        //ArrayAdapter arrayAdapter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, allAlcoholic.toArray());
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(allAlcoholic));
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsAlcoholic.setAdapter(arrayAdapter5);

    }

}



