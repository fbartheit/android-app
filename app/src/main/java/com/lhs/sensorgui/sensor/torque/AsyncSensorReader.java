package com.lhs.sensorgui.sensor.torque;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

/**
 * Created by Dragan on 8/23/2016.
 */
public class AsyncSensorReader extends AsyncTask<Void, Void, Void>{

    private SensorReader mSensorReader;
    private Context context;
    private static int lastAngle;

    public AsyncSensorReader(Context context, SensorReader sensorReader){
        this.context = context;
        mSensorReader = sensorReader;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // read data, and update sensor reader, then send broadcast so view knows to update UI
        SensorData lsd = mSensorReader.getLastReadData();
        if(lsd != null) {
            lastAngle = lsd.getPosition();
        }else{
            lastAngle = 0;
        }
        Random r = new Random();
        int sampleTorque = r.nextInt(15000)/100;
        //int sampleAngle = ((r.nextInt()*1200 + lastAngle)%3600)/10;
        //int angleDiff = (sampleAngle>lastAngle)?(sampleAngle-lastAngle):((3600-lastAngle)+sampleAngle);
        int angleDiff = new Random().nextInt(5000);
        lastAngle += angleDiff;

        int RPM = (int)((angleDiff/0.628)/60);
        mSensorReader.addData(new SensorData(sampleTorque, lastAngle%360, RPM));
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataReadBroadcastReceiver.DATA_READ_BROADCAST));
        return null;
    }
}
