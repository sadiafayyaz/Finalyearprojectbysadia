package com.example.androidcarmanager.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.example.androidcarmanager.R;

public class Reminder_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder__screen);
        setTitle(Html.fromHtml("<font color='#3477e3'>Reminders</font>"));
    }
    public void addNotes(View v){
        Intent i = new Intent(Reminder_Screen.this, Add_Reminder_Screen.class);
        startActivity(i);
    }
    public void moveToNextActivity(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.card1: {
                intent = new Intent(Reminder_Screen.this, Add_Reminder_Screen.class);
                startActivity(intent);
            }
            break;
            case R.id.card2: {
                intent = new Intent(Reminder_Screen.this, Add_Reminder_Screen.class);
                startActivity(intent);
            }
            break;
            case R.id.card3: {
                intent = new Intent(Reminder_Screen.this, Add_Reminder_Screen.class);
                startActivity(intent);
            }
            break;

            default: {
                Log.d("error: ", "Next Activity not Specified");
            }
        }
    }
}
