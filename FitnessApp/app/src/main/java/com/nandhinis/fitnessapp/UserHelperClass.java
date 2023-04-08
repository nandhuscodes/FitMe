package com.nandhinis.fitnessapp;

public class UserHelperClass {
    String name, username, password, age, height, weight, gender, lbl, food1, calorie;
    float bmi;
    int wi, workout, food;
    public UserHelperClass() {

    }

    public String getFood1() {
        return food1;
    }

    public void setFood1(String food1) {
        this.food1 = food1;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public int getWorkout() {
        return workout;
    }

    public void setWorkout(int workout) {
        this.workout = workout;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public UserHelperClass(String name, String username, String password, String age, String gender, String height, String weight, String lbl, float bmi, int wi, int workout, int food) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.lbl = lbl;
        this.bmi = bmi;
        this.wi = wi;
        this.workout = workout;
        this.food = food;
    }
    public UserHelperClass(String age, String height, String weight, String lbl, float bmi) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.lbl = lbl;
        this.bmi = bmi;
    }

    public UserHelperClass(String food, String calorie) {
        this.food1 = food;
        this.calorie = calorie;
    }

    public UserHelperClass(int wi, String cal, String wk) {
        this.wi = wi;
        this.food1 = cal;
        this.calorie =wk;
    }

    public String getFn() {
        return food1;
    }

    public void setFn(String food) {
        this.food1 = food;
    }

    public String getC() {
        return calorie;
    }

    public void setC(String calorie) {
        this.calorie = calorie;
    }

    public int getWi() {
        return wi;
    }

    public void setWi(int wi) {
        this.wi = wi;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public String getLbl() {
        return lbl;
    }

    public void setLbl(String lbl) {
        this.lbl = lbl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
