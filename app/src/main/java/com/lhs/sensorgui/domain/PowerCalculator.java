package com.lhs.sensorgui.domain;

import com.lhs.sensorgui.app.model.User;

/**
 * Created by Dragan on 9/1/2016.
 */
public class PowerCalculator {

    public static double calculateBMR(int height, double weight, long age, String sex){
        // TODO check these calculations, result is not correct
        double bmr = 0.0;

        double ampHeight = height * 10;
        double ampWeight = weight * 6.25;

        long time = System.currentTimeMillis() - age;
        int days = (int)(((time/1000)/60)/60)/24;   // number of days
        int yearsInt = (int) days/365;             // number of whole years
        //double years = yearsInt + (days%365)/365;  // number of years scaled
        double years = days/365;

        bmr = ampHeight + ampWeight - years;
        bmr = (User.SEX_MALE.equals(sex))?(bmr+5):(bmr-161); // daily BRM (basal metabolic rate)
        bmr /= (3600*24); // BMR for one second
        return bmr;
    }
}
