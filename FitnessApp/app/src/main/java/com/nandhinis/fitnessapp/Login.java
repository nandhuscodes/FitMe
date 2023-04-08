package com.nandhinis.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button callSignUp, login_btn, forget_passwd;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    CheckBox rememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoName);
        sloganText = findViewById(R.id.sloganName);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_go);
        forget_passwd = findViewById(R.id.fp);
        rememberMe = findViewById(R.id.remember_me_chkb);
        sharedPreferences=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String userName=sharedPreferences.getString("username","");
        String passWord=sharedPreferences.getString("password","");
        password.getEditText().setText(passWord);
        username.getEditText().setText(userName);
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        forget_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Forget_passwd.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rememberMe.isChecked()){
                    editor.putString("username",username.getEditText().getText().toString());
                    editor.putString("password",password.getEditText().getText().toString());
                    editor.commit();
                }else{
                    editor.putString("username","");
                    editor.putString("password","");
                    editor.commit();
                }
                if(!validateUserName() | !validatePass()){
                    return;
                }else{
                    isUser();
                }
            }
        });
    }
    private Boolean validateUserName(){
        String val = username.getEditText().getText().toString();
        if(val.isEmpty() ){
            username.setError("This field cannot be empty");
            return false;
        } else{
            username.setErrorEnabled(false);
            username.setError(null);
            return true;
        }
    }
    private Boolean validatePass(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("This field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void isUser(){
        String userEnteredUsername = username.getEditText().getText().toString();
        String userEnteredPassword = password.getEditText().getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        startActivity(intent);
                        finish();
                    }else{
                        password.setError("wrong password");
                        password.requestFocus();
                    }
                }else{
                    username.setError("No such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}