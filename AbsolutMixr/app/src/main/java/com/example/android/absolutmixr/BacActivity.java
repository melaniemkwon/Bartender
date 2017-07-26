package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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

        estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double genderConstant = 0;
                double bacPer = 0;
                RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
                String gender = genderButton.getText().toString();

                if(gender.equals("Male"))
                    genderConstant = 0.73;
                else
                    genderConstant = 0.66;

                bacPer = (Integer.valueOf(drinkSpinner.getSelectedItem().toString()) * .6 * 5.14) /
                        (Integer.valueOf(weightSpinner.getSelectedItem().toString()) * genderConstant) -
                        .015 * Integer.valueOf(hourSpinner.getSelectedItem().toString());
                bac.setText(String.valueOf(bacPer));
            }
        });
    }
}
