package com.example.android.absolutmixr;


        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.SwitchCompat;
        import android.util.Log;
        import android.util.SparseArray;
        import android.view.View;
        import android.widget.CheckBox;

        import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
        import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
        import com.google.android.gms.vision.barcode.Barcode;

        import java.util.List;

        import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

        import static com.example.android.absolutmixr.R.id.barcode;



public class BarcodeScanner extends AppCompatActivity implements BarcodeRetriever {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeScanner.this)
                        .setTitle("code retrieved")
                        .setMessage(barcode.displayValue);
                builder.show();
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

}
