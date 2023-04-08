package com.nandhinis.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LB extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user"), refr;
    ImageButton ac, kp, td, p;
    TextView tv1, tv2, tv3, tv4, user, prog;
    String un, wk;
    public int progressValue = 0, calories = 0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lb);
        ac = findViewById(R.id.ac);
        kp = findViewById(R.id.kpu);
        td = findViewById(R.id.td);
        p = findViewById(R.id.p);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        user = findViewById(R.id.username);
        progressBar = findViewById(R.id.progress);
        prog = findViewById(R.id.textprogress);
        showAllUserData();
        String uname = user.getText().toString();
        progressValue = Integer.parseInt(prog.getText().toString());
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressValue < 500){
                    calories = Integer.parseInt(tv1.getText().toString());
                    progressValue += calories;
                    progressBar.setProgress(progressValue);
                    prog.setText(String.valueOf(progressValue));
                    ref.child(uname).child("progress").child("workout").setValue(progressValue);
                    ref.child(uname).child("workout").setValue(progressValue);
                }
            }
        });
        kp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressValue < 500){
                    calories = Integer.parseInt(tv2.getText().toString());
                    progressValue += calories;
                    progressBar.setProgress(progressValue);
                    prog.setText(String.valueOf(progressValue));
                    ref.child(uname).child("progress").child("workout").setValue(progressValue);
                    ref.child(uname).child("workout").setValue(progressValue);
                }
            }
        });
        td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressValue < 500){
                    calories = Integer.parseInt(tv3.getText().toString());
                    progressValue += calories;
                    progressBar.setProgress(progressValue);
                    prog.setText(String.valueOf(progressValue));
                    ref.child(uname).child("progress").child("workout").setValue(progressValue);
                    ref.child(uname).child("workout").setValue(progressValue);
                }
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressValue < 500){
                    calories = Integer.parseInt(tv4.getText().toString());
                    progressValue += calories;
                    progressBar.setProgress(progressValue);
                    prog.setText(String.valueOf(progressValue));
                    ref.child(uname).child("progress").child("workout").setValue(progressValue);
                    ref.child(uname).child("workout").setValue(progressValue);
                }
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
                prog.setText(wk);
                progressValue = Integer.parseInt(wk);
                progressBar.setProgress(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }}