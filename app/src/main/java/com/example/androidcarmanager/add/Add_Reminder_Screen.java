package com.example.androidcarmanager.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidcarmanager.R;

public class Add_Reminder_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reminder__screen);
        setTitle(Html.fromHtml("<font color='#3477e3'>Add Reminder</font>"));
    }
    public void saveAlarm(View v){ Log.i("Save Alarm","Alarm has been Saved Successfully.");
        Intent i= new Intent(Add_Reminder_Screen.this, Reminder_Screen.class);
        startActivity(i);
        Toast.makeText(Add_Reminder_Screen.this,"Alarm has been Saved Successfully.",Toast.LENGTH_SHORT).show();


    }
}
