package com.example.androidcarmanager.Expences;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidcarmanager.Database.Expence_DB;
import com.example.androidcarmanager.R;
import com.example.androidcarmanager.user_info.Login_Screen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Expense_Detail_Screen<expense_detail, expense_detail_screen, AddExpenses> extends AppCompatActivity {
    String selectedExpenses;
    String[] expences={"Maintenance","Fuel","Purchase spare patrs","cleaning","engine tuning"};
    //  data to put
    String title;
    Long date, time;
    Double meterreading, price;

    EditText ettitle, etdate,ettime, etmeterReading,etprice;
    Button buttonsave;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private DatabaseReference databaseReference1, databaseReference2;
    private FirebaseAuth Auth;

    String key;
    int expensesIndex;
    ArrayList<String> keys = new ArrayList<String>();

    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense__detail__screen);
        setTitle(Html.fromHtml("<font color='#3477e3'>Add Expense</font>"));

        Auth = FirebaseAuth.getInstance();
        if (Auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Expense_Detail_Screen.this, Login_Screen.class));
        }
        final FirebaseUser user=Auth.getCurrentUser();
        key= getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("key","-1");
        databaseReference1= FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/vehicles/");
        databaseReference2=FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/expenses/"+key);
        ettitle=(EditText) findViewById(R.id.expencetitle);
        etdate=(EditText) findViewById(R.id.expenseDate);
        ettime=(EditText) findViewById(R.id.expenseTime);
        etmeterReading=(EditText) findViewById(R.id.meterreading);
        etprice=(EditText) findViewById(R.id.expenceprice);
        buttonsave=(Button) findViewById(R.id.expencesave);

        Bundle bundle=getIntent().getExtras();
        selectedExpenses=bundle.getString("type");


//      seting date and time picker
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

//                setting date picker
                datePickerDialog = new DatePickerDialog(Expense_Detail_Screen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        calendar.set(year, month, dayOfMonth);
                        date = calendar.getTimeInMillis();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int mHour=calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute=calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Expense_Detail_Screen.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ettime.setText(hourOfDay+" : "+minute);
                        time=calendar.getTimeInMillis();
                    }
                },mHour, mMinute, true);
                timePickerDialog.show();
            }
        });



        databaseReference1.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    keys.add(ds.getKey());
                }
            }
        });

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEt();
                if(key.equals("-1")) {
                    Toast.makeText(Expense_Detail_Screen.this, "Please Select a Car Before Adding Expense!", Toast.LENGTH_LONG).show();
                }else if(title==null){
                    Toast.makeText(Expense_Detail_Screen.this,"Enter Expense Title!",Toast.LENGTH_SHORT).show();
                }else if(date==null){
                    Toast.makeText(Expense_Detail_Screen.this,"Enter Date!",Toast.LENGTH_SHORT).show();
                }else if(time==null){
                    Toast.makeText(Expense_Detail_Screen.this,"Enter Time!",Toast.LENGTH_SHORT).show();
                }else if(meterreading==null){
                    Toast.makeText(Expense_Detail_Screen.this,"Enter Meter Reading!",Toast.LENGTH_SHORT).show();
                }else if(price==null){
                    Toast.makeText(Expense_Detail_Screen.this,"Enter Price!",Toast.LENGTH_SHORT).show();
                }else{
                    int position=keys.indexOf(key);
                    if(position==-1){
                        Toast.makeText(Expense_Detail_Screen.this, "Please Select an Existing Car!", Toast.LENGTH_LONG).show();
                    }else {
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(selectedExpenses).exists()){
                                    for (DataSnapshot ds : dataSnapshot.child(selectedExpenses).getChildren()) {
                                        expensesIndex = Integer.parseInt(ds.getKey());
                                        Log.d("expensesIndex1", String.valueOf(expensesIndex));
                                    }
                                    expensesIndex++;
                                    Log.d("expensesIndex2", String.valueOf(expensesIndex));
                                    addIntoDb(expensesIndex);
//                                    expensesIndex = Integer.parseInt(dataSnapshot.child(selectedExpenses).getKey());
                                }else{
                                    Log.d("expensesIndex3", String.valueOf(expensesIndex));
                                    expensesIndex=0;
                                    addIntoDb(expensesIndex);
                                }
                            }
                        });

                    }
                }

            }
        });

    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedExpenses=expences[position];
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addIntoDb(int index){
        Expence_DB expensesDB = new Expence_DB(title, date, time, meterreading, price);
        databaseReference2.child(selectedExpenses).child(String.valueOf(index)).setValue(expensesDB);
        clearEtValue();
        Toast.makeText(Expense_Detail_Screen.this, "Expense Added!", Toast.LENGTH_SHORT).show();

    }

    public void clearEtValue(){
        ettitle.setText("");
        etdate.setText("");
        ettime.setText("");
        etmeterReading.setText("");
        etprice.setText("");
    }

    public void getValuesFromEt(){
        title= ettitle.getText().toString().trim();
        meterreading= Double.valueOf(etmeterReading.getText().toString());
        price= Double.valueOf(etprice.getText().toString().trim());
    }
}
