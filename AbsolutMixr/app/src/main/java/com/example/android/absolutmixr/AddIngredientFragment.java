package com.example.android.absolutmixr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AddIngredientFragment extends DialogFragment {

    Button button;

    public AddIngredientFragment() {
        // Required empty public constructor\
    }

   public interface OnDialogCloseListener{
       void closeDialog(String ingredientId);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ingredient, container, true);

        button = (Button) view.findViewById(R.id.add_by_upc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), BarcodeScanner.class);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }
}
