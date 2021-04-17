package com.example.androidcarmanager.View_EXPENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidcarmanager.Adapter.Custom_Adapter;
import com.example.androidcarmanager.Adapter.Model_Adapter;
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

public class Search_Screen extends AppCompatActivity {
    EditText etSearchTitle, etStartDate,etEndDate;
    Button btnSecrchList;
    ListView listOfSerches;
    Custom_Adapter adapter;
    //  values from firebase
    ArrayList<String> maintenance = new ArrayList<String>();
    ArrayList<Long> maintenanceDate = new ArrayList<Long>();
    ArrayList<String> fuel = new ArrayList<String>();
    ArrayList<Long> fuelDate = new ArrayList<Long>();
    ArrayList<String> purchase = new ArrayList<String>();
    ArrayList<Long> purchaseDate = new ArrayList<Long>();
    ArrayList<String> cleaning = new ArrayList<String>();
    ArrayList<Long> cleacingDate = new ArrayList<Long>();
    ArrayList<String> enginetuning = new ArrayList<String>();
    ArrayList<Long> enginetuningDate = new ArrayList<Long>();


    ArrayList<String>titles=new ArrayList<String>();
    ArrayList<Long>dates=new ArrayList<Long>();
    ArrayList<Model_Adapter> listModel= new ArrayList<Model_Adapter>();

    Long startDate, endDate;
    String query="";

    private DatabaseReference databaseReference1, databaseReference2;
    private FirebaseAuth Auth;
    private FirebaseUser user;

    String vehicleId;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__screen);
        setTitle(Html.fromHtml("<font color='#3477e3'>Search Expences</font>"));
        Auth = FirebaseAuth.getInstance();
        user = Auth.getCurrentUser();
        if (Auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Search_Screen.this, Login_Screen.class));
        }
        vehicleId = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("key", "-1");

        setTitle("Search Expenses");
        etStartDate=(EditText) findViewById(R.id.startdate);
        etEndDate=(EditText) findViewById(R.id.enddate);
        etSearchTitle=(EditText) findViewById(R.id.expencetitle);
        listOfSerches =(ListView) findViewById(R.id.searchList);
        btnSecrchList=(Button) findViewById(R.id.btnsearch);

        progressDialog= ProgressDialog.show(Search_Screen.this, "","Please Wait, Loading...",true);
        getDataFromFirebase();
//        setListforAdapter();


        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(Search_Screen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etStartDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        calendar.set(year, month, dayOfMonth);
                        startDate=calendar.getTimeInMillis();
//                        startDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                }, mYear, mMonth, mDay).show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int mYear=calendar.get(Calendar.YEAR);
                int mMonth=calendar.get(Calendar.MONTH);
                int mDay=calendar.get(Calendar.DAY_OF_MONTH);


                new DatePickerDialog(Search_Screen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etEndDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        calendar.set(year, month, dayOfMonth);
                        endDate=calendar.getTimeInMillis();
                    }
                }, mYear, mMonth, mDay).show();
            }
        });

        btnSecrchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query=etSearchTitle.getText().toString().trim();

                if (startDate == null){
                    Toast.makeText(Search_Screen.this,"Select Starting Date!",Toast.LENGTH_SHORT).show();
                }else if(endDate == null){
                    Toast.makeText(Search_Screen.this,"Select Ending Date!",Toast.LENGTH_SHORT).show();
                }else if(query.isEmpty()){
                    Toast.makeText(Search_Screen.this,"Enter your Query!",Toast.LENGTH_SHORT).show();
                }else {
                    adapter.dateTitleFilter(startDate, endDate, query);
                }
            }
        });
    }

    public void getDataFromFirebase(){
        databaseReference1 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId);

        databaseReference1.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                  maintenance
                    if (dataSnapshot.child("Maintenance").exists()) {
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId + "/Maintenance");
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Expence_DB expensesDB = ds.getValue(Expence_DB.class);
                                    Log.d("expensesDB(maintena)", expensesDB.getExpenseTitle());
                                    maintenance.add(expensesDB.getExpenseTitle());
                                    maintenanceDate.add(expensesDB.getDate());
                                }

                            }
                        });
                        for (String ti:maintenance){
                            Log.d("maintenance", ti);
                        }
                    } else {
                        Log.d("expensesDB(maintenance)", "Empty");
                    }

//                  fuel
                    if (dataSnapshot.child("Fuel").exists()) {
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId + "/Fuel");
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Expence_DB expensesDB = ds.getValue(Expence_DB.class);
                                    Log.d("expensesDB(fuel)", expensesDB.getExpenseTitle());
                                    fuel.add(expensesDB.getExpenseTitle());
                                    fuelDate.add(expensesDB.getDate());
                                }
                            }
                        });
                    } else {
                        Log.d("fuel", "Empty");
                    }

//                  purchase
                    if (dataSnapshot.child("Purchase").exists()) {
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId + "/Purchase");
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Expence_DB expensesDB = ds.getValue(Expence_DB.class);
                                    Log.d("expensesDB(purchase)", expensesDB.getExpenseTitle());
                                    purchase.add(expensesDB.getExpenseTitle());
                                    purchaseDate.add(expensesDB.getDate());
                                }
                            }
                        });
                    } else {
                        Log.d("purchase", "Empty");
                    }

//                  services
                    if (dataSnapshot.child("Cleaning").exists()) {
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId + "/Service");
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Expence_DB expensesDB = ds.getValue(Expence_DB.class);
                                    Log.d("expensesDB(Cleaning)", expensesDB.getExpenseTitle());
                                    cleaning.add(expensesDB.getExpenseTitle());
                                    cleacingDate.add(expensesDB.getDate());
                                }
                            }
                        });
                    } else {
                        Log.d("Cleaning", "Empty");
                    }

//                  fine
                    if (dataSnapshot.child("Engine Tuning").exists()) {
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/expenses/" + vehicleId + "/Fine");
                        databaseReference2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Expence_DB expensesDB = ds.getValue(Expence_DB.class);
                                    Log.d("expensesDB(tuning)", expensesDB.getExpenseTitle());
                                    enginetuning.add(expensesDB.getExpenseTitle());
                                    enginetuningDate.add(expensesDB.getDate());
                                }
                            }
                        });
                    } else {
                        Log.d("Enging tuning", "Empty");
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            setListforAdapter();
                        }
                    },5*1000);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(Search_Screen.this,"Failed to Fetch Data try again",Toast.LENGTH_SHORT).show();
                    Log.d("dataSnapshot.exists()", "Empty");
                }
            }
        });
    }

    public void setListforAdapter(){
        Calendar c=Calendar.getInstance();
//      maintenance
        if (!maintenance.isEmpty() && !maintenanceDate.isEmpty()){
            for (String title:maintenance){
                titles.add(title);
                Log.d("setAdapter", title);
            }
            for (Long date:maintenanceDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
                dates.add(date);
            }
        }
//      fuel
        if (!fuel.isEmpty() && !fuelDate.isEmpty()){
            for (String title:fuel){
                titles.add(title);
                Log.d("setAdapter", title);
            }
            for (Long date:fuelDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
                dates.add(date);
            }
        }
//      purchase
        if (!purchase.isEmpty() && !purchaseDate.isEmpty()){
            for (String title:purchase){
                titles.add(title);
                Log.d("setAdapter", title);
            }
            for (Long date:purchaseDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
                dates.add(date);
            }
        }
//      service
        if (!cleaning.isEmpty() && !cleacingDate.isEmpty()){
            for (String title:cleaning){
                titles.add(title);
                Log.d("setAdapter", title);
            }
            for (Long date:cleacingDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
                dates.add(date);
            }
        }
//      fine
        if (!enginetuning.isEmpty() && !enginetuningDate.isEmpty()){
            for (String title:enginetuning){
                titles.add(title);
                Log.d("setAdapter", title);
            }
            for (Long date:enginetuningDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
                dates.add(date);
            }
        }

//            for (String title:tax){
//                titles.add(title);
//                Log.d("setAdapter", title);
//            }
//            for (Long date:taxDate){
//                c.setTimeInMillis(date);
//                dates.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR));
//            }

        setAdapter();
    }
    public void setAdapter(){
        if(!titles.isEmpty() && !dates.isEmpty()){

//        Log.d("setAdapter", String.valueOf(titles.size()));
            for(int i=0;i<titles.size()-1;i++){
                Model_Adapter modelAdapter=new Model_Adapter(titles.get(i), dates.get(i));
                //bind all strings in an array
                listModel.add(modelAdapter);
            }
            adapter = new Custom_Adapter(Search_Screen.this, listModel);
            listOfSerches.setAdapter(adapter);

        }else{
            Toast.makeText(Search_Screen.this, "Failed  Retrieving Data",Toast.LENGTH_SHORT).show();
        }
    }
}
