package com.nandhinis.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_profile extends AppCompatActivity {
    TextView regUsername, regName, proglay;
    TextInputLayout regAge, regGender, regHeight, regWeight, regBMI;
    Button regBtn;
    String lbl = "";
    float bmi = 0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);
        regAge  =findViewById(R.id.age);
        regUsername = findViewById(R.id.username);
        regGender = findViewById(R.id.gender);
        regHeight = findViewById(R.id.height);
        regWeight = findViewById(R.id.weight);
        regBtn = findViewById(R.id.calc_bmi);
        regName = findViewById(R.id.full_name);
        progressBar = findViewById(R.id.progressc);
        proglay = findViewById(R.id.tvc);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        showAllUserData();
        String username = regUsername.getText().toString();
        String name = regName.getText().toString();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");
                // get value
                String username = regUsername.getText().toString();
                String age = regAge.getEditText().getText().toString();
                String height = regHeight.getEditText().getText().toString();
                String weight = regWeight.getEditText().getText().toString();
                if(height!=null && !"".equals(height) && weight!=null && !"".equals(weight)){
                    float hf = Float.parseFloat(height);
                    float wf = Float.parseFloat(weight);
                    bmi = (wf / (hf*hf))*10000;
                    if(bmi<=18.5){
                        lbl="UnderWeight: Weight Gain";
                    }else if(bmi>18.5 && bmi<= 24.9){
                        lbl="Normal: Healthy";
                    }else if(bmi>24.9 && bmi<= 30) {
                        lbl = "Overweight: Weight Loss";
                    }else{
                        lbl="Obesity: Weight Loss";
                    }
                }
                reference = FirebaseDatabase.getInstance().getReference().child("user").child(username);
                reference.child("age").setValue(age);
                reference.child("weight").setValue(weight);
                reference.child("height").setValue(height);
                reference.child("bmi").setValue(bmi);
                reference.child("lbl").setValue(lbl);
                Intent intent = new Intent(User_profile.this, Login.class);
                Toast.makeText(User_profile.this, "after updation user login agin", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.dashboard:
                        Intent intent = new Intent(User_profile.this, Dashboard.class);
                        intent.putExtra("username", username);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
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
                float win = Integer.parseInt(snapshot.child("wi").getValue().toString());
                float wok = Integer.parseInt(snapshot.child("workout").getValue().toString());
                float fd = Integer.parseInt(snapshot.child("food").getValue().toString());
                float valuep = ((win*100) / 96000)+((wok *100)/15000)+((fd *100)/60000);
                progressBar.setProgress((int)valuep/3);
                valuep = Float.parseFloat(String.format("%.2f", valuep/3));
                proglay.setText((valuep)+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}