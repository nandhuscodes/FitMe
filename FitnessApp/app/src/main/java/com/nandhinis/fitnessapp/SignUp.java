package com.nandhinis.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Variable
    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    Button regBtn;
    TextInputLayout regName, regUsername, regPassword, regAge, regGender, regHeight, regWeight;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    AutoCompleteTextView autoCompleteTextView;
    String lbl = "";
    float bmi = 0;
    int wi = 0, workout = 0, food = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoName);
        sloganText = findViewById(R.id.sloganName);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_go);
        callSignUp = findViewById(R.id.login_screen);
        regName  =findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regAge  =findViewById(R.id.age);
        regUsername = findViewById(R.id.username);
        regGender = findViewById(R.id.gender);
        regHeight = findViewById(R.id.height);
        regWeight = findViewById(R.id.weight);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");
                if(!validateName() | !valGen() | !validateUserName() | !valWeight() | !validatePass() | !valHeight() | !valAge()){
                    return;
                }
                // get value
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String age = regAge.getEditText().getText().toString();
                String gender = autoCompleteTextView.getText().toString();
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
                UserHelperClass helperClass = new UserHelperClass(name, username,password, age, gender, height, weight, lbl, bmi, wi, workout, food);
                reference.child(username).setValue(helperClass);
                reference.child(username).child("progress").child("wi").setValue(0);
                reference.child(username).child("progress").child("workout").setValue(0);
                reference.child(username).child("progress").child("food").setValue(0);
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        String[] gender = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_gender, gender);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        callSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()){
            regName.setError("Thi field cannot be empty");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUserName(){
        String val = regUsername.getEditText().getText().toString();
        if(val.isEmpty() ){
            regUsername.setError("This field cannot be empty");
            return false;
        }
        else if (!val.matches("\\A\\w{4,20}\\z")){
            regUsername.setError("No whitespace allowed");
            return false;
        }else if(val.length() >= 30){
            regUsername.setError("Constraint length: 30");
            return false;
        }else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePass(){
        String val = regPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            regPassword.setError("This field cannot be empty");
            return false;
        }else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valAge(){
        String age = regAge.getEditText().getText().toString();
        if(age.length()<2 && Integer.parseInt(age)<18){
            regAge.setError("Violating age limit");
            return false;
        }else if(age.isEmpty()){
            regAge.setError("This field cannot be empty");
            return false;
        }else{
            regAge.setError(null);
            regAge.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valGen(){
        String gender = autoCompleteTextView.getText().toString();
        if(gender.isEmpty()){
            regGender.setError("This field cannot be empty");
            return false;
        }else{
            regGender.setError(null);
            regGender.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valHeight(){
        String height = regHeight.getEditText().getText().toString();
        if(height.isEmpty()){
            regHeight.setError("This field cannot be empty");
            return false;
        }else{
            regHeight.setError(null);
            regHeight.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valWeight(){
        String weight = regWeight.getEditText().getText().toString();
        if(weight.isEmpty()){
            regWeight.setError("This field cannot be empty");
            return false;
        }else{
            regWeight.setError(null);
            regWeight.setErrorEnabled(false);
            return true;
        }
    }
}