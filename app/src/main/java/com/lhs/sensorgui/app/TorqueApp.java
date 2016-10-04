package com.lhs.sensorgui.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.model.User;
import com.lhs.sensorgui.db.DAO.UserDAO;
import com.lhs.sensorgui.db.DBHelper;
import com.lhs.sensorgui.db.UserContract;
import com.lhs.sensorgui.domain.model.Ride;
import com.lhs.sensorgui.sensor.gps.GPSManager;
import com.lhs.sensorgui.sensor.gps.GPSTracker;
import com.lhs.sensorgui.sensor.torque.SensorReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dragan on 8/23/2016.
 */
public class TorqueApp {

    private static TorqueApp app;
    private DBHelper dbHelper;
    private SensorReader mSensorReader;
    private GPSManager mGPSManager;
    private GPSTracker mGPSTracker;
    private Context context;
    private String username;
    private User mUser;
    private Ride currentRide;
    private ArrayList<Ride> allRides;

    private TorqueApp(Context context){
        this.context = context;
        mUser = UserDAO.loadUserFromSharedPreferences(context);
        dbHelper = new DBHelper(context);
        mSensorReader = SensorReader.getInstance(context, null);
        mGPSManager = GPSManager.getInstance(context);
        allRides = new ArrayList<>();
        //mGPSTracker = GPSTracker.getInstance(context);
    }

    public static TorqueApp getInstance(Context c){
        if(app == null){
            app = new TorqueApp(c);
        }
        return app;
    }

    public void readUser(){
        User u = UserDAO.read(dbHelper, username);
        mUser.setEmail(u.getEmail());
        mUser.setUsername(u.getUsername());
        mUser.setFirstName(u.getFirstName());
        mUser.setLastName(u.getLastName());
    }

    public SensorReader getSR(){ return this.mSensorReader; }

    public boolean isReading(){
        return mSensorReader.getIsReading();
    }

    public Ride getCurrentRide(){
        return this.currentRide;
    }

    public void createNewRide(){
        currentRide = new Ride(mSensorReader, mGPSManager);
        allRides.add(currentRide);
    }

    public void startRide(){
        currentRide.start();
    }

    public void stopRide(){
        currentRide.stop();
    }

    public double getUserBMR(){
        return mUser.getBMR();
    }

    public User getUser(){ return mUser; }

    public boolean saveRideDataToCSVFile(){
        List<String[]> data = mSensorReader.getCurrentRide().getDataForCSVFile();

        try {
            File csvFile = new File(context.getFilesDir().getAbsolutePath() + "/Ride_" + new Date().toString() + ".csv");
            if(!csvFile.exists()){
                csvFile.createNewFile();
            }
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            writer.writeAll(data);
            writer.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public User checkUserLogin(String username, String password){
        return UserDAO.read(dbHelper, username, password);
    }

    public void loginUser(User user){

        SharedPreferences sp = context.getSharedPreferences(UserDAO.USER_LOGIN_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(UserContract.UserEntry.USER_USERNAME, user.getUsername());
        editor.commit();
    }

    public void logoutUser(){
        SharedPreferences sp = context.getSharedPreferences(UserDAO.USER_LOGIN_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(UserContract.UserEntry.USER_USERNAME, null);
        editor.commit();
    }

    public void registerUser(User user){
        UserDAO.add(dbHelper, user);
    }

    public void setUsername(String username){
        this.username = username;
    }
}
