package com.example.mixr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Leonard on 7/20/2017.
 */

public class Button3Activity extends AppCompatActivity {
    private Button mBGoBack;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.b3_activity);
        mBGoBack = (Button) findViewById(R.id.bt_go_back);

        mBGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
