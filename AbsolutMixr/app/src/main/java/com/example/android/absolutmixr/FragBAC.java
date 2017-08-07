package com.example.android.absolutmixr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragBAC extends Fragment {
    private Spinner drinkSpinner;
    private Spinner weightSpinner;
    private Spinner hourSpinner;
    private Button estimate;
    private TextView bac;
    private TextView toSober;
    private TextView drinkMeter;
    private RadioGroup genderGroup;
    private ImageView imageView;
    private ImageView statusView;
    private String TAG = "fragbac";
    private double bacNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bac,container,false);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setPositiveButton("Accept", null)
                .setTitle("Disclaimer")
                .setMessage("BAC readings may not be accurate. We are not liable for any choices you make because of the readings. Do you accept these terms?")
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                        viewPager.setCurrentItem(0);
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(false);

        drinkSpinner = (Spinner) view.findViewById(R.id.drinkSpinner);
        weightSpinner = (Spinner) view.findViewById(R.id.weightSpinner);
        hourSpinner = (Spinner) view.findViewById(R.id.hourSpinner);
        estimate = (Button) view.findViewById(R.id.estimate);
        bac = (TextView) view.findViewById(R.id.bac);
        genderGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        toSober = (TextView) view.findViewById(R.id.toSober);
        statusView = (ImageView) view.findViewById(R.id.statusView);
        drinkMeter = (TextView) view.findViewById(R.id.drinkMeterTextView);

        String[] weightList = new String[100];

        for(int i = 0; i < weightList.length; i++) {
            weightList[i] = String.valueOf(i * 2 + 90);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, weightList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
        bac.setVisibility(view.GONE);
        toSober.setVisibility(view.GONE);
        statusView.setVisibility(view.GONE);
        drinkMeter.setVisibility(view.GONE);

        estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton genderButton = (RadioButton) getActivity().findViewById(genderGroup.getCheckedRadioButtonId());
                String gender = genderButton.getText().toString();

                displayBAC(calculateBAC(gender,
                        Integer.valueOf(drinkSpinner.getSelectedItem().toString()),
                        Integer.valueOf(weightSpinner.getSelectedItem().toString()),
                        Integer.valueOf(hourSpinner.getSelectedItem().toString())), bac);
            }
        });
        return view;
    }


    public void displayBAC(double bac, TextView textView) {
        DecimalFormat df = new DecimalFormat("#,###0.000");
        df.setRoundingMode(RoundingMode.DOWN);

        if(bac == 0)
            textView.setText("BAC: 0.00" + " You're completely sober!");
        else if(bac <= 0.04)
            textView.setText("BAC: " + String.valueOf(df.format(bac)) + " You're a buzzing aren't you?");
        else if(bac <= 0.08)
            textView.setText("BAC: " + String.valueOf(df.format(bac)) + " You're tipsy. Slow down.");
        else if(bac <= 0.17)
            textView.setText("BAC: " + String.valueOf(df.format(bac)) + " You're drunk. Please don't drive.");
        else
            textView.setText("BAC: " + String.valueOf(df.format(bac)) + " You're wasted now. Good luck.");
    }

    public double calculateBAC(String gender, int numDrinks, int weight, int hours) {
        double genderConstant = 0;

        if(gender.equals("Male"))
            genderConstant = 0.73;
        else
            genderConstant = 0.66;

        bacNumber = (numDrinks * .6 * 5.14) / (weight * genderConstant) - .015 * hours;
        Log.d(TAG, String.valueOf(bacNumber));
        imageView.setVisibility(View.GONE);
        bac.setVisibility(View.VISIBLE);
        toSober(bacNumber);
        status(bacNumber);

        if(bacNumber < 0)
            return 0;
        return bacNumber;
    }

    public void toSober(double bac) {
        double timeToSober = bac / .015;

        if(timeToSober > 0) {
            Log.d(TAG, String.valueOf(timeToSober));
            String hours = String.valueOf((int) Math.floor(timeToSober)) + " hour";
            String minutes = String.valueOf((int) ((timeToSober % 1 * 100) * .60)) + " minute";

            if((int) Math.floor(timeToSober) != 1)
                hours += "s";
            if((int) ((timeToSober % 1 * 100) * .60) != 1)
                minutes += "s";

            toSober.setText(hours + " & " + minutes + " until BAC is 0.00");
            toSober.setVisibility(View.VISIBLE);
        }
    }

    public void status(double bac) {
        AnimationDrawable statusAnimation;

        if(bac <= 0)
            statusView.setBackgroundResource(R.drawable.green);
        else if(bac <= 0.04) {
            statusView.setBackgroundResource(R.drawable.greenanimation);
            statusAnimation = (AnimationDrawable) statusView.getBackground();
            statusAnimation.start();
        }
        else if(bac <= 0.07) {
            statusView.setBackgroundResource(R.drawable.yellowanimation);
            statusAnimation = (AnimationDrawable) statusView.getBackground();
            statusAnimation.start();
        }
        else if(bac < 0.08)
            statusView.setBackgroundResource(R.drawable.yellow);
        else if(bac <= 0.17) {
            statusView.setBackgroundResource(R.drawable.redanimation);
            statusAnimation = (AnimationDrawable) statusView.getBackground();
            statusAnimation.start();
        }
        else
            statusView.setBackgroundResource(R.drawable.red);

        drinkMeter.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.VISIBLE);
    }
}
