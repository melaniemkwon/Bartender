package com.example.android.absolutmixr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by melaniekwon on 7/27/17.
 */

public class FragCocktails extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cocktails,container,false);

        // Get reference to RecyclerView
        RecyclerView mDrink = (RecyclerView) view.findViewById(R.id.recycler);

        // Set layout manager
        mDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrink.setHasFixedSize(true);

        // Create and set adapter
        AdapterDrink mAdapter= new AdapterDrink();
        mDrink.setAdapter(mAdapter);

//        loadDrinkData();
        mDrink.setVisibility(View.VISIBLE);
//        MainActivity.NetworkTask task = new MainActivity.NetworkTask("");
//        task.execute();

        return view;
    }
}
