package com.nandhinis.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forget_passwd extends AppCompatActivity {
    TextInputLayout user, name, pass, cpass;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button regBtn, goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_passwd);
        user = findViewById(R.id.username);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        cpass = findViewById(R.id.cpassword);
        regBtn = findViewById(R.id.reg_btn);
        goback = findViewById(R.id.login_screen);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");
                // get value
                String username = user.getEditText().getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child("user").child(username);
                reference.child("password").setValue(pass.getEditText().getText().toString());
                Intent intent = new Intent(Forget_passwd.this, Login.class);
                Toast.makeText(Forget_passwd.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forget_passwd.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}