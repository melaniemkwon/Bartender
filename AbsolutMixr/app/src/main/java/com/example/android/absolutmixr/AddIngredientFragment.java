package com.example.android.absolutmixr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.absolutmixr.Model.Ingredient;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.GONE;


public class AddIngredientFragment extends DialogFragment implements LoaderCallbacks<Void> {
    private static final int INGREDIENT_LOADER = 1;
    private Button button;
    private Button add;
    private TextView prompt;
    private Spinner type;
    private Spinner ingredients;
    private ProgressBar progress;
    private int ingredientChoice;
    private ArrayList<Ingredient> ingredientChoices;
    private DialogInterface.OnDismissListener onDismissListener;

    public AddIngredientFragment() {
        // Required empty public constructor\
    }
    public interface OnDismissListener {
        void onDismiss(AddIngredientFragment myDialogFragment);
    }
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(getActivity()) {

            //set progress spinner to visible on load start
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }


            @Override
            public Void loadInBackground() {
                try {
                    ingredientChoices = IngredientSelectionTasks.getIngredients(ingredientChoice);
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
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(GONE);
        String spinnerArray[] = new String[ingredientChoices.size()];
        for (int i =0; i<spinnerArray.length;i++){
            spinnerArray[i] = ingredientChoices.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, spinnerArray);
        prompt.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        ingredients.setVisibility(View.VISIBLE);
        ingredients.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    public void load() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(INGREDIENT_LOADER, null, this).forceLoad();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_add_ingredient, container, true);
        type = (Spinner) view.findViewById(R.id.type_spinner);
        ingredients = (Spinner) view.findViewById(R.id.ingredient_spinner);
        button = (Button) view.findViewById(R.id.add_by_upc);
        progress = (ProgressBar) view.findViewById(R.id .progressBarAddIngredient);
        prompt = (TextView) view.findViewById(R.id .ingredient_prompt);
        add = (Button) view.findViewById(R.id.add);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        add.setTextColor(Color.WHITE);
        add.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), BarcodeScanner.class);
                getActivity().startActivity(myIntent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("addingfrag","click registered");
                Ingredient choice = ingredientChoices.get(ingredients.getSelectedItemPosition());
                FragCabinet.setIngredient(choice);
                //OnDialogCloseListener activity = (OnDialogCloseListener) FragCabinet;
                //activity.closeDialog(choice.getDescription(), choice.getName(), choice.getId(), choice.getType());
                AddIngredientFragment.this.dismiss();
            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    if (arg2 ==0){
                        return;
                    }
                    else {
                        ingredientChoice = arg2;
                        Log.d("AddIngredientFrag", "Item Selected = " + ingredientChoice);
                        load();
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

}
