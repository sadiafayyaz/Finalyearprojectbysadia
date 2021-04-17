package com.example.androidcarmanager.Expence_Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidcarmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fuel_layout extends Fragment {


    public Fuel_layout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fuel_layout, container, false);
    }

}
