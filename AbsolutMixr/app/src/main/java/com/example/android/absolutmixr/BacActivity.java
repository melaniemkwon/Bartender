package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by maxru on 7/25/17.
 */

public class BacActivity extends AppCompatActivity {

    private Spinner drinkSpinner;
    private Spinner weightSpinner;
    private Spinner hourSpinner;
    private Button estimate;
    private TextView bac;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bac);

        drinkSpinner = (Spinner) findViewById(R.id.drinkSpinner);
        weightSpinner = (Spinner) findViewById(R.id.weightSpinner);
        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        estimate = (Button) findViewById(R.id.estimate);
        bac = (TextView) findViewById(R.id.bac);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup);

        String[] weightList = new String[54];

        for(int i = 0; i < weightList.length; i++) {
            weightList[i] = String.valueOf(i * 5 + 85);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weightList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);

        estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
                String gender = genderButton.getText().toString();

                displayBAC(calculateBAC(gender,
                        Integer.valueOf(drinkSpinner.getSelectedItem().toString()),
                        Integer.valueOf(weightSpinner.getSelectedItem().toString()),
                        Integer.valueOf(weightSpinner.getSelectedItem().toString())), bac);
            }
        });
    }

    public void displayBAC(double bac, TextView textView) {
        if(bac == 0)
            textView.setText("BAC:" + String.valueOf(bac));
        else if(bac <= 0.4)
            textView.setText("BAC:" + String.valueOf(bac));
        else
            textView.setText("BAC:" + String.valueOf(bac));
    }

    public double calculateBAC(String gender, int numDrinks, int weight, int hours) {
        double genderConstant = 0;
        double bacPer = 0;

        if(gender.equals("Male"))
            genderConstant = 0.73;
        else
            genderConstant = 0.66;

        bacPer = (numDrinks * .6 * 5.14) / (weight * genderConstant) - .015 * hours;

        if(bacPer < 0)
            return 0;
        return bacPer;
    }
}
