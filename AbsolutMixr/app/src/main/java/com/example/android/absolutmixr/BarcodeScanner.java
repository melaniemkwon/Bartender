package com.example.android.absolutmixr;




        import android.os.Bundle;
        import android.support.v4.app.LoaderManager;
        import android.support.v4.content.AsyncTaskLoader;
        import android.support.v4.content.Loader;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.util.SparseArray;
        import android.view.View;


        import com.example.android.absolutmixr.Model.Ingredient;
        import com.example.android.absolutmixr.Model.UPCIngredient;
        import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
        import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
        import com.google.android.gms.vision.barcode.Barcode;

        import org.json.JSONException;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

        import static com.example.android.absolutmixr.R.id.barcode;



public class BarcodeScanner extends AppCompatActivity implements BarcodeRetriever, LoaderManager.LoaderCallbacks{

    /*
            NOTE: CODE FOR THIS SEGMENT IS FROM https://github.com/KingsMentor/MobileVisionBarcodeScanner
            ALL THAT HAS BEEN CHANGED IS THE ONRETRIEVED FUNCTION TO UTILIZE THE UPC SCANNED BY THIS LIBRARY,
            AS WELL AS THE ASYNC CALL TO OUR API
     */
    private static final int INGREDIENT_LOADER = 1;
    ArrayList<UPCIngredient> upcingredient;
    ArrayList<Ingredient> ingredients;
    Barcode bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(barcode);
        barcodeCapture.setRetrieval(this);

    }

    @Override
    public void onRetrieved(final Barcode barcode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar = barcode;
                load();


            }
        });


    }

    // for multiple callback
    @Override
    public void onRetrievedMultiple(final Barcode closetToClick, final List<BarcodeGraphic> barcodeGraphics) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = "Code selected : " + closetToClick.displayValue + "\n\nother " +
                        "codes in frame include : \n";
                for (int index = 0; index < barcodeGraphics.size(); index++) {
                    Barcode barcode = barcodeGraphics.get(index).getBarcode();
                    message += (index + 1) + ". " + barcode.displayValue + "\n";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeScanner.this)
                        .setTitle("code retrieved")
                        .setMessage(message);
                builder.show();
            }
        });

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // when image is scanned and processed
    }

    @Override
    public void onRetrievedFailed(String reason) {
        // in case of failure
    }


        @Override
        public Loader<Void> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Void>(getApplicationContext()) {

                //set progress spinner to visible on load start
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    Log.d("Async","start async");

                }

                //trigger refresh articles from refreshtasks in background
                @Override
                public Void loadInBackground() {
                    try {
                        Log.d("Async","inasync");
                        ingredients = IngredientSelectionTasks.getAllIngredients();
                        upcingredient = IngredientSelectionTasks.getUPCIngredients(bar.displayValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

            };
        }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(upcingredient.size()>0){
                    Ingredient a = IngredientSelectionTasks.getIngredientFromUPC(upcingredient.get(0).getName(),ingredients);
                    if(a!=null){
                        FragCabinet.setIngredient(a);
                    }
                AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeScanner.this)
                        .setTitle("code retrieved, added to cabinet if in database")
                        .setMessage(upcingredient.get(0).getName());
                builder.show();}
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeScanner.this)
                            .setTitle("Sorry")
                            .setMessage("Our database does not contain that UPC. Try something else.");
                    builder.show();}

            }});
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public void load() {
        Log.d("Async","called async");
        LoaderManager loaderManager = this.getSupportLoaderManager();
        loaderManager.restartLoader(INGREDIENT_LOADER, null, this).forceLoad();

    }


}
