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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Water extends AppCompatActivity {
    TextView textView, RegUsername;
    ProgressBar progressBar;
    Button fifty, onefifty, twofifty, threefifty, fivehun, azalt;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();;
    DatabaseReference reference = rootNode.getReference("user");
    String WI, un;

    public int progressValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_water);
        textView = findViewById(R.id.textprogress);
        RegUsername = findViewById(R.id.username);
        progressBar = findViewById(R.id.progress);
        fifty = findViewById(R.id.button50);
        onefifty = findViewById(R.id.button150);
        twofifty = findViewById(R.id.button250);
        threefifty = findViewById(R.id.button350);
        fivehun = findViewById(R.id.button500);
        azalt = findViewById(R.id.buttonrst);
        showAllUserData();
        progressValue = Integer.parseInt(textView.getText().toString());
        fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue < 3200) {
                    progressValue += 50;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
        onefifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue < 3200) {
                    progressValue += 150;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
        twofifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue < 3200) {
                    progressValue += 250;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
        threefifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue < 3200) {
                    progressValue += 350;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
        fivehun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue < 3200) {
                    progressValue += 500;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
        azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressValue > 0) {
                    progressValue = 0;
                    progressBar.setProgress(progressValue);
                    textView.setText(String.valueOf(progressValue));
                    reference.child("progress").child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                    reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
                }
            }
        });
    }
    public void Update(View view){
        reference.child("wi").setValue(Integer.parseInt(textView.getText().toString()));
    }
    private void showAllUserData(){
        Intent intent = getIntent();
        un = intent.getStringExtra("username");
        RegUsername.setText(un);
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(un);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer.parseInt(WI = snapshot.child("progress").child("wi").getValue().toString());
                textView.setText(WI);
                progressValue = Integer.parseInt(WI);
                progressBar.setProgress(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private boolean isWIChanged() {
        if(!WI.equals(textView.getText().toString())){
            reference.child("username").child("progress").child("wi").setValue(textView.getText());
            return true;
        }else{
            return false;
        }
    }
}