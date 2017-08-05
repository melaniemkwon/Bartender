package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class FragSearch extends DialogFragment {

    private final String TAG = "searchfragment";

    private EditText editTextContent;
    private Spinner spinnerTaste;
    private Spinner spinnerColor;
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
    protected static HashSet<String> allColors = new HashSet<String>();

    //using a map to store key value pairs for contents of strings in array
    //necessary for search cases such as in occasion spinner,
    // where "text" : "After-Dinner Drinks" has search parameter "id": "after-dinner"
    protected static HashMap<String, String> tasteMap = new HashMap<String, String>();
    protected static HashMap<String, String> glassMap = new HashMap<String, String>();
    protected static HashMap<String, String> timeMap = new HashMap<String, String>();

    View view;

    public FragSearch() {
    }

    public interface OnDialogCloseListener {
        // TODO: add other terms..
        void closeDialog(String query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.advanced_search, container, false);


        editTextContent = (EditText) view.findViewById(R.id.drink_content_edit_text);
        spinnerTaste = (Spinner) view.findViewById(R.id.drink_taste_spinner);
        spinnerColor = (Spinner) view.findViewById(R.id.drink_color_spinner);
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
        allTastes.add("-Show All-");
        allColors.add("-Show All-");
        allSkills.add("-Show All-");
        allGlasses.add("-Show All-");
        allTimes.add("-Show All-");
    }

    private Object[] sortSet(HashSet<String> set){
        List<String> sortedList = new ArrayList<String>(set);
        Collections.sort(sortedList);
        return sortedList.toArray();
    }

    private void setSpinners(){
        spinnerGlass.setAdapter(getArrayAdapter(allGlasses));
        spinnerColor.setAdapter(getArrayAdapter(allColors));
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

        final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        advSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String urlString;
                String json;
                String drinkContains = editTextContent.getText().toString();
                URL url = NetworkUtils.makeAdvancedSearchUrl(drinkContains,
                        spinnerSkill.getSelectedItem().toString(), spinnerTaste.getSelectedItem().toString(),
                        spinnerGlass.getSelectedItem().toString(), spinnerTime.getSelectedItem().toString(),
                        spinnerColor.getSelectedItem().toString());

                FragCocktails fg = (FragCocktails) getActivity().getSupportFragmentManager().getFragments().get(0);
                getActivity().getSupportFragmentManager().beginTransaction().detach(fg).attach(fg).commit();

                Log.d(TAG, "search button clicked");

                viewPager.setCurrentItem(0);

                FragSearch.this.dismiss();

            }
        });

    }


}
