package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class AdvSearch extends Fragment {

    private static final String TAG = AdvSearch.class.getSimpleName();

    private EditText editTextContent;
    private Spinner spinnerTaste;
    private Spinner spinnerSkill;
    private Spinner spinnerGlass;
    private Spinner spinnerTime;
    private Button advSearchButton;
    private TextView urlTestingTV;

    //sets are used to auto-populate arrays in spinners without repeats
    protected static HashSet<String> allTastes = new HashSet<String>();
    protected static HashSet<String> allSkills = new HashSet<String>();
    protected static HashSet<String> allGlasses = new HashSet<String>();
    protected static HashSet<String> allTimes = new HashSet<String>();

    //using a map to store key value pairs for contents of strings in array
    //necessary for search cases such as in occasion spinner,
    // where "text" : "After-Dinner Drinks" has search parameter "id": "after-dinner"
    protected static HashMap<String, String> tasteMap = new HashMap<String, String>();
    protected static HashMap<String, String> glassMap = new HashMap<String, String>();
    protected static HashMap<String, String> timeMap = new HashMap<String, String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advanced_search,container,false);

        editTextContent = (EditText) view.findViewById(R.id.drink_content_edit_text);
        spinnerTaste = (Spinner) view.findViewById(R.id.drink_taste_spinner);
        spinnerSkill = (Spinner) view.findViewById(R.id.drink_skill_spinner);
        spinnerGlass = (Spinner) view.findViewById(R.id.drink_glass_spinner);
        spinnerTime = (Spinner) view.findViewById(R.id.drink_time_spinner);
        advSearchButton = (Button) view.findViewById(R.id.bt_adv_search);
        urlTestingTV = (TextView) view.findViewById(R.id.url_test_tv);

        addAllOptionToSets();
        setSpinners();

        return view;
    }

    private void addAllOptionToSets(){
        allTastes.add("-All Drinks-");
        allSkills.add("-All Drinks-");
        allGlasses.add("-All Drinks-");
        allTimes.add("-All Drinks-");
    }

    private Object[] sortSet(HashSet<String> set){
        List<String> sortedList = new ArrayList<String>(set);
        Collections.sort(sortedList);
        return sortedList.toArray();
    }

    private void setSpinners(){
        spinnerGlass.setAdapter(getArrayAdapter(allGlasses));
        spinnerTaste.setAdapter(getArrayAdapter(allTastes));
        spinnerSkill.setAdapter((getArrayAdapter(allSkills)));
        spinnerTime.setAdapter(getArrayAdapter(allTimes));

    }

    private ArrayAdapter getArrayAdapter(HashSet<String> set){
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sortSet(set));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        advSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String drinkContains = editTextContent.getText().toString();
                URL url = NetworkUtils.makeAdvancedSearchUrl(drinkContains,
                        spinnerSkill.getSelectedItem().toString(), spinnerTaste.getSelectedItem().toString(),
                        spinnerGlass.getSelectedItem().toString(), spinnerTime.getSelectedItem().toString());

                try {
                    String urlString = URLDecoder.decode(url.toString(), "utf-8");
                    urlTestingTV.setText(urlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}



