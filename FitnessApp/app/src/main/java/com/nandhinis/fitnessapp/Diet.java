package com.nandhinis.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Diet extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Diet");
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user"), refr;
    Button add, add1, add2, add3, addcal;
    TextView prog, user;
    ProgressBar progressBar;
    TextInputLayout foodb, food1, food2, food3, addfood, addc;
    String fn, food, calorie, cal, un;
    public int progressValue = 0, calories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_diet);
        foodb = findViewById(R.id.food);
        food1 = findViewById(R.id.food1);
        food2 = findViewById(R.id.food2);
        food3 = findViewById(R.id.food3);
        user = findViewById(R.id.username);
        add = findViewById(R.id.add);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        addfood = findViewById(R.id.addfood);
        addcal = findViewById(R.id.as);
        addc = findViewById(R.id.addcal);
        progressBar = findViewById(R.id.progress);
        showAllUserData();
        prog = findViewById(R.id.textprogress);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn = foodb.getEditText().getText().toString();
                Intent intent = getIntent();
                String user_username = intent.getStringExtra("username");
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
                isFood();
                if (progressValue < 2000) {
                    progressValue += calories;
                    prog.setText(String.valueOf(progressValue));
                    progressBar.setProgress(progressValue);
                }
                ref.child("progress").child("food").setValue(Integer.parseInt(prog.getText().toString()));
                ref.child("food").setValue(Integer.parseInt(prog.getText().toString()));
            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn = food1.getEditText().getText().toString();
                Intent intent = getIntent();
                String user_username = intent.getStringExtra("username");
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
                isFood1();
                if (progressValue < 2000) {
                    progressValue += calories;
                    prog.setText(String.valueOf(progressValue));
                    progressBar.setProgress(progressValue);
                }
                ref.child("progress").child("food").setValue(Integer.parseInt(prog.getText().toString()));
                ref.child("food").setValue(Integer.parseInt(prog.getText().toString()));
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn = food2.getEditText().getText().toString();
                Intent intent = getIntent();
                String user_username = intent.getStringExtra("username");
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
                isFood2();
                if (progressValue < 2000) {
                    progressValue += calories;
                    prog.setText(String.valueOf(progressValue));
                    progressBar.setProgress(progressValue);
                }
                ref.child("progress").child("food").setValue(Integer.parseInt(prog.getText().toString()));
                ref.child("food").setValue(Integer.parseInt(prog.getText().toString()));
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn = food3.getEditText().getText().toString();
                Intent intent = getIntent();
                String user_username = intent.getStringExtra("username");
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
                isFood3();
                if (progressValue < 2000) {
                    progressValue += calories;
                    prog.setText(String.valueOf(progressValue));
                    progressBar.setProgress(progressValue);
                }
                ref.child("progress").child("food").setValue(Integer.parseInt(prog.getText().toString()));
                ref.child("food").setValue(Integer.parseInt(prog.getText().toString()));
            }
        });
        addcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food = addfood.getEditText().getText().toString();
                calorie = addc.getEditText().getText().toString();
                Intent intent = getIntent();
                String user_username = intent.getStringExtra("username");
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(user_username);
                UserHelperClass helperClass = new UserHelperClass(food, calorie);
                reference.child("progress").child(food).setValue(helperClass);
                reference.child(food).setValue(helperClass);
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
                Integer.parseInt(cal = snapshot.child("progress").child("food").getValue().toString());
                prog.setText(cal);
                progressValue = Integer.parseInt(cal);
                progressBar.setProgress(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void isFood(){
        Query checkUser = reference.orderByChild("fn").equalTo(fn);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    calories = Integer.parseInt(snapshot.child(fn).child("c").getValue().toString());
                }else{
                    foodb.setError("No such food item exist");
                    foodb.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void isFood1(){
        Query checkUser = reference.orderByChild("fn").equalTo(fn);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    calories = Integer.parseInt(snapshot.child(fn).child("c").getValue().toString());
                }else{
                    food1.setError("No such food item exist");
                    food1.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void isFood2(){
        Query checkUser = reference.orderByChild("fn").equalTo(fn);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    calories = Integer.parseInt(snapshot.child(fn).child("c").getValue().toString());
                }else{
                    food2.setError("No such food item exist");
                    food2.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void isFood3(){
        Query checkUser = reference.orderByChild("fn").equalTo(fn);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    calories = Integer.parseInt(snapshot.child(fn).child("c").getValue().toString());
                }else{
                    food3.setError("No such food item exist");
                    food3.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}