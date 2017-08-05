package com.example.android.absolutmixr;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.FragmentManager;
        import android.support.v7.app.AppCompatActivity;
        import android.util.SparseArray;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.android.gms.vision.CameraSource;
        import com.google.android.gms.vision.Detector;
        import com.google.android.gms.vision.barcode.Barcode;
        import com.google.android.gms.vision.barcode.BarcodeDetector;

        import java.io.IOException;

        import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class MyCabinetActivity extends AppCompatActivity {

    private Button button;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_my_cabinet);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddIngredientFragment frag = new AddIngredientFragment();
                frag.show(fm, "addingredientfragment");
            }
        });
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
                Intent myIntent = new Intent(MyCabinetActivity.this, MainActivity.class);
                MyCabinetActivity.this.startActivity(myIntent);
                return true;
            case R.id.wishlist:


                return true;
            case R.id.cabinet:

            case R.id.bac:

                return true;

        }return true;

    }


}