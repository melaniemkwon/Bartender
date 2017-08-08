package com.example.android.absolutmixr;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Random;


/**
 * Created by maxru on 8/7/17.
 */

public class DrunkTest extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private RelativeLayout drunkayout;
    private TextView sensorTextView;
    private TextView rangeTextView;
    private TextView timerTextView;
    private Button exitButton;
    private Button resetButton;
    private static final String TAG = "drunktest";
    private boolean isDrunk = true;
    private int max = 0;
    private int min = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.drunk_test);

        drunkayout = (RelativeLayout) findViewById(R.id.drunk_layout);
        timerTextView = (TextView) findViewById(R.id.timer);
        rangeTextView = (TextView) findViewById(R.id.range);
        sensorTextView = (TextView) findViewById(R.id.sensorValue);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        resetButton = (Button) findViewById(R.id.resetButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        Dialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        randomRange();
                        new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timerTextView.setText("" + millisUntilFinished / 1000);
                            }

                            @Override
                            public void onFinish() {
                                timerTextView.setText("0");
                            }
                        }.start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displayResults(min, max);
                            }
                        }, 5000);
                    }
                })
                .setTitle("Instructions")
                .setMessage("By the 5th second, the number on the screen must be in the given range to pass otherwise you'll fail.\n\nTilt the phone forward/backward to increase/decrease the number on the screen.")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            sensorTextView.setText((String.valueOf((int)(values[0] * 100 / 9.81))));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void displayResults(int min, int max) {
        if(Integer.valueOf(sensorTextView.getText().toString()) <= max && Float.valueOf(sensorTextView.getText().toString()) >= min) {
            rangeTextView.setText("You Passed!");
            drunkayout.setBackgroundColor(Color.GREEN);
        }
        else {
            rangeTextView.setText("You Failed!");
            drunkayout.setBackgroundColor(Color.RED);
        }
    }

    public void randomRange() {
        Random rand = new Random();
        int random = rand.nextInt((4));

        if(random == 0) {
            rangeTextView.setText("0 - 25");
            min = 0;
            max = 25;
        }
        else if(random == 1) {
            rangeTextView.setText("25 - 50");
            min = 25;
            max = 50;
        }
        else if(random == 2) {
            rangeTextView.setText("50 - 75");
            min = 50;
            max = 75;
        }
        else {
            rangeTextView.setText("75 - 100");
            min = 75;
            max = 100;
        }
    }
}
