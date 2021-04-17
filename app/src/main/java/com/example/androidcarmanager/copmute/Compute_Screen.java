package com.example.androidcarmanager.copmute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

import com.example.androidcarmanager.R;

public class Compute_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute__screen);
        setTitle(Html.fromHtml("<font color='#3477e3'>Compute</font>"));

    }
}
