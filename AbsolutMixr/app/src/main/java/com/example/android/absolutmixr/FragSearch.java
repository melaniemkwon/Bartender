package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragSearch extends DialogFragment {

    private final String TAG = "searchfragment";

    private EditText searchEntry;
    private Button searchButton;

    public FragSearch() {
    }

    public interface OnDialogCloseListener {
        // TODO: add other terms..
        void closeDialog(String query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);

        searchEntry = (EditText) view.findViewById(R.id.search_drinks_entry);
        searchButton = (Button) view.findViewById(R.id.search_drinks_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                activity.closeDialog(searchEntry.getText().toString());
                FragSearch.this.dismiss();
            }
        });

        return view;
    }
}
