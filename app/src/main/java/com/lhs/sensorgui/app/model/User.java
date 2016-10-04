package com.lhs.sensorgui.app.model;

import com.lhs.sensorgui.domain.PowerCalculator;

import java.util.Date;

/**
 * Created by Dragan on 8/25/2016.
 */
public class User {
    public static final String SEX_MALE = "male";
    public static final String SEX_FEMALE = "female";

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String username;

    private int height;
    private double weight;
    private String name;
    private long age; // in miliseconds
    private String ageString; // preferences date_of_birth value
    private String sex; // int value
    private double BMR;

    public User(){}

    public User(String name, int height, double weight, long dateOfBirth, String sex){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = dateOfBirth;
        this.ageString = new Date(dateOfBirth).toString();
        this.sex = sex;
        this. BMR = PowerCalculator.calculateBMR(height, weight, dateOfBirth, sex);
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public long getAge() {
        return age;
    }

    public String getAgeString() {
        return ageString;
    }

    public String getSex() {
        return sex;
    }

    public double getBMR() { return BMR; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
}
