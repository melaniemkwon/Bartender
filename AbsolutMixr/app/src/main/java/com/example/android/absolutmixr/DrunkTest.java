package com.example.android.absolutmixr;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by maxru on 8/7/17.
 */

public class DrunkTest extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView sensorTextView;
    private Button exitButton;
    private static final String TAG = "drunktest";
    private Timer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.drunk_test);

        sensorTextView = (TextView) findViewById(R.id.sensorValue);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Dialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setTitle("Instructions")
                .setMessage("Tilt the phone forward or backward until the big number is within the provided range.\nStay in this range for at least 5 seconds to pass.\nTake too long and you'll fail!")
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
            sensorTextView.setText((String.valueOf(values[0] * 100 / 9.81)));
            Log.d(TAG, String.valueOf(values[0] * 100 / 9.81));
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
}
