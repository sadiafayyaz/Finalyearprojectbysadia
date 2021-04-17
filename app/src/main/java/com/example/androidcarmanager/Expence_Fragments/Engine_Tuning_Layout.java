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
public class Engine_Tuning_Layout extends Fragment {



    public Engine_Tuning_Layout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_engine__tuning__layout, container, false);

        return view;

    }

}
