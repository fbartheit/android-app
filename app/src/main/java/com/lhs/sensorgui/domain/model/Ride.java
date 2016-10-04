package com.lhs.sensorgui.domain.model;

import android.os.SystemClock;

import com.lhs.sensorgui.sensor.gps.GPSManager;
import com.lhs.sensorgui.sensor.torque.SensorData;
import com.lhs.sensorgui.sensor.torque.SensorReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragan on 9/1/2016.
 */
public class Ride {

    private long startTime;
    private long endTime;
    private double rideTime;

    private int maxCadence;
    private int maxTorque;

    private List<SensorData> readData;

    private SensorReader mSensorReader;
    private GPSManager mGPSManager;

    public Ride(){
        readData = new ArrayList<>();
        maxCadence = 0;
        maxTorque = 0;
    }

    public Ride(SensorReader sr, GPSManager gpsm){
        this();
        this.mSensorReader = sr;
        this.mGPSManager = gpsm;
    }

    public void start(){
        startTime = SystemClock.elapsedRealtime();
        mSensorReader.startReading(this);
        mGPSManager.startListening(this);
    }

    public void stop(){
        mSensorReader.stopReading();
        mGPSManager.stopListening();
        endTime = SystemClock.elapsedRealtime();
        calculateRideTime(startTime, endTime);
    }

    public double getElapsedTime(){
        return calculateElapsedTime(startTime, SystemClock.elapsedRealtime());
    }

    public double getRideTime(){ return rideTime; }

    public void calculateRideTime(long start, long stop){
        long rideMiliseconds = stop - start;
        rideTime =  rideMiliseconds/1000.0; // seconds
    }

    public double calculateElapsedTime(long start, long stop){
        long elapsedMiliseconds = stop - start;
        double elapsedTime =  elapsedMiliseconds/1000.0; // seconds
        return elapsedTime;
    }

    public void add(SensorData sd){
        readData.add(sd);
        if(sd.getRPM() > maxCadence) maxCadence = sd.getRPM();
        if(sd.getTorque() > maxTorque) maxTorque = sd.getTorque();
    }

    public SensorData getLastReadData(){
        return (!readData.isEmpty())?readData.get(readData.size()-1):null;
    }

    public List<String[]> getDataForCSVFile(){
        List<String[]> dataList = new ArrayList<>();
        for(SensorData data: readData){
            dataList.add(data.getDataAsStringArray());
        }
        return dataList;
    }

    public int getMaxCadence(){ return this.maxCadence; }
    public int getMaxTorque(){ return this.maxTorque; }

    //TODO implement data calculations and updates and reading

}
