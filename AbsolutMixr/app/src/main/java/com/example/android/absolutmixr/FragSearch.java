package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragSearch extends DialogFragment {

    private final String TAG = "searchfragment";

    public FragSearch() {
    }

    public interface OnDialogCloseListener {
        void closeDialog(String query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);

        return view;
    }
}
