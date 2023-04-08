package com.nandhinis.fitnessapp;

import static com.nandhinis.fitnessapp.CalenderUtils.daysInWeekArray;
import static com.nandhinis.fitnessapp.CalenderUtils.monthYearFromDate;
import static com.nandhinis.fitnessapp.CalenderUtils.selectedDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sahana.horizontalcalendar.HorizontalCalendar;
import com.sahana.horizontalcalendar.OnDateSelectListener;
import com.sahana.horizontalcalendar.model.DateModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    TextView regUsername, regName, regLbl;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    Button wi_btn, wk_btn, foodbtn;
    private TextView monthDayText;
    TextView textView1, textView2, textView3, todaycalen;
    ProgressBar progressBar, progressBar1, progressBar2;
    private TextView dayOfWeekTV;
    String cal, wi,wk;
    String today = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        regUsername = findViewById(R.id.username);
        regName = findViewById(R.id.full_name);
        wi_btn = findViewById(R.id.wi_btn);
        regLbl = findViewById(R.id.label);
        wk_btn = findViewById(R.id.btnwk);
        foodbtn = findViewById(R.id.foodbtn);
        progressBar = findViewById(R.id.level);
        progressBar1 = findViewById(R.id.level1);
        progressBar2 = findViewById(R.id.level2);
        textView1 = findViewById(R.id.textprogress);
        textView2 =findViewById(R.id.textprogress1);
        textView3 = findViewById(R.id.textprogress3);
        todaycalen = findViewById(R.id.monthDayText);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        selectedDate = LocalDate.now();
        String today = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        initWidgets();
        showAllUserData();
        String username = regUsername.getText().toString();
        String name = regName.getText().toString();
        wi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Water.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Diet.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        wk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Workout.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.profile:
                        Intent intent = new Intent(Dashboard.this, User_profile.class);
                        intent.putExtra("username", username);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void daily() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String to = monthDayText.getText().toString();
        String today = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("user").child(username);
        Query checkUser = refer.orderByChild("progress");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    refer.child("now").setValue(to);
                    refer.child("now_t").setValue(today);
                    String c1 = refer.child("now").toString();
                    String c2 = refer.child("now_t").toString();
                    if(!to.equals(today)){
                        String cal = "0";
                        String wi = "0";
                        String wk = "0";
                        textView2.setText(cal);
                        textView1.setText(wk);
                        textView3.setText(wi);
                        progressBar.setProgress(Integer.parseInt(wk));
                        progressBar1.setProgress(Integer.parseInt(cal));
                        progressBar2.setProgress(Integer.parseInt(wi));
                    }else{
                        showAllUserData();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showAllUserData(){
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        regUsername.setText(user_username);
        regName.setText(user_name);
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lbl = snapshot.child("lbl").getValue().toString();
                String cal = snapshot.child("progress").child("food").getValue().toString();
                String wi = snapshot.child("progress").child("wi").getValue().toString();
                String wk = snapshot.child("progress").child("workout").getValue().toString();
                textView2.setText(cal);
                textView1.setText(wk);
                textView3.setText(wi);
                progressBar.setProgress(Integer.parseInt(wk));
                progressBar1.setProgress(Integer.parseInt(cal));
                progressBar2.setProgress(Integer.parseInt(wi));
                regLbl.setText(lbl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        String msg = "Selected Date "+ date;
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalenderUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        daily();
    }

    public void previousDayAction(View view)
    {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusDays(1);
        setDayView();
    }
}