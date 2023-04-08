package com.nandhinis.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Workout extends AppCompatActivity {
    Button fb,ub,lb,arms;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user"), refr;
    ProgressBar progressBar;
    TextView tv, user;
    int progressValue;
    String un, wk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workout);
        fb = findViewById(R.id.btnfb);
        ub = findViewById(R.id.btnub);
        lb = findViewById(R.id.btnlb);
        arms = findViewById(R.id.arms);
        progressBar = findViewById(R.id.progress);
        tv = findViewById(R.id.textprogress);
        user = findViewById(R.id.username);
        showAllUserData();
        String username = user.getText().toString();
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workout.this, FB.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        ub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workout.this, UB.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workout.this, LB.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workout.this, Arms.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
    private void showAllUserData(){
        Intent intent = getIntent();
        un = intent.getStringExtra("username");
        user.setText(un);
        refr = FirebaseDatabase.getInstance().getReference().child("user").child(un);
        refr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer.parseInt(wk = snapshot.child("progress").child("workout").getValue().toString());
                tv.setText(wk);
                progressValue = Integer.parseInt(wk);
                progressBar.setProgress(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}