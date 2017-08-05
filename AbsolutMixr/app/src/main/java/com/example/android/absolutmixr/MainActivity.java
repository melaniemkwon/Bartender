package com.example.android.absolutmixr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //inflate menu so it is seen by user
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drinks:


                return true;
            case R.id.wishlist:


                return true;
            case R.id.cabinet:
                Intent myIntent = new Intent(MainActivity.this, MyCabinetActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            case R.id.bac:
                Intent myIntent2 = new Intent(MainActivity.this, BarcodeScanner.class);
                MainActivity.this.startActivity(myIntent2);
                return true;

        }return true;

    }


}
