package com.lhs.sensorgui.sensor.gps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.lhs.sensorgui.domain.model.Ride;

/**
 * Created by Dragan on 9/20/2016.
 */
public class GPSManager {
    private static long MIN_TIME_BW_UPDATES = 1000;
    private static int MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    private static GPSManager mGPSManager = null;
    private LocationManager locationManager;
    private Context context;

    private Location previousLocation;
    private Location currentLocation;
    private float totalDistance;

    private float maxSpeed;
    private float avgSpeed;
    private int numMeasurements;

    private Ride currentRide;

    private GPSManager(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static GPSManager getInstance(Context context){
        if(mGPSManager == null){
            mGPSManager = new GPSManager(context);
        }
        return mGPSManager;
    }

    public void startListening(Ride currentRide){
        clearData();
        this.currentRide = currentRide;
        if ( ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            //ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
            //LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
            //Log.d("GPS Enabled", "GPS Enabled");
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);
        }
    }

    public void stopListening(){
        if ( ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void clearData(){
        previousLocation = null;
        currentLocation = null;
        totalDistance = 0;
        maxSpeed = 0;
        avgSpeed = 0;
        numMeasurements = 0;
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(LocationUpdatedBroadcastReceiver.LOCATION_UPDATED_BROADCAST));
    }

    public float getTotalDistance(){
        return this.totalDistance;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //if (location.hasAccuracy() && location.getAccuracy() < 3) {
                previousLocation = currentLocation;
                currentLocation = location;
                if (previousLocation != null) {
                    float[] results = new float[1];
                    Location.distanceBetween(
                            previousLocation.getLatitude(), previousLocation.getLongitude(),
                            currentLocation.getLatitude(), currentLocation.getLongitude(), results);
                    totalDistance += results[0];

                    // update speeds
                    float currSpeed = currentLocation.getSpeed();
                    if(currSpeed > maxSpeed){
                        maxSpeed = currSpeed;
                    }
                    float sp = avgSpeed*numMeasurements;
                    sp += currSpeed;
                    numMeasurements++;
                    avgSpeed = sp/numMeasurements;
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(LocationUpdatedBroadcastReceiver.LOCATION_UPDATED_BROADCAST));
                }
            //}
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public float getMaxSpeed(){
        return this.maxSpeed;
    }

    public float getAvgSpeed(){
        return this.avgSpeed;
    }

}
