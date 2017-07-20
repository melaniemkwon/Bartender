package com.example.mixr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
/*
* When you add a new activity when you change the button for what your doing remember to add it to
* the manifest if you dont the app will close out.
*
* This is a simple button use for the app to just get started, I will make more changes later and
* adding new features.
* */
public class MainActivity extends AppCompatActivity {
    private Button mB1LaunchActiviy;
    private Button mB2LaunchActiviy;
    private Button mB3LaunchActiviy;
    private Button mB4LaunchActiviy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets the layout for how it will look
        setContentView(R.layout.activity_main);

        //get the button info from the layout.
        mB1LaunchActiviy = (Button) findViewById(R.id.button1);
        mB2LaunchActiviy = (Button) findViewById(R.id.button2);
        mB3LaunchActiviy = (Button) findViewById(R.id.button3);
        mB4LaunchActiviy = (Button) findViewById(R.id.button4);

        //what should happen when any button is pressed
        //There might be a more efficient way than this and I will look into it.
        //This should help in the mean time so you guys can start on your parts.
        mB1LaunchActiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(1);
            }
        });
        mB2LaunchActiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(2);
            }
        });
        mB3LaunchActiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(3);
            }
        });
        mB4LaunchActiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(4);
            }
        });
    }

    private void launchActivity(int button){
        Intent intent;
        if(button ==1) {
            intent = new Intent(this, ButtonActivity.class);
            startActivity(intent);
        }
        else if(button ==2) {
            intent = new Intent(this, Button2Activity.class);
            startActivity(intent);
        }
        else if(button ==3) {
            intent = new Intent(this, Button3Activity.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(this, Button4Activity.class);
            startActivity(intent);
        }
        //this switches to the activity.

    }


}
