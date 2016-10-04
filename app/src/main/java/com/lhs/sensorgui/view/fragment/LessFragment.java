package com.lhs.sensorgui.view.fragment;

/**
 * Created by Dragan on 8/20/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhs.sensorgui.R;

/**
 * Created by Dragan on 8/20/2016.
 */

import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.sensor.gps.GPSManager;
import com.lhs.sensorgui.sensor.gps.LocationUpdatedBroadcastReceiver;
import com.lhs.sensorgui.sensor.torque.SensorData;
import com.lhs.sensorgui.sensor.torque.DataReadBroadcastReceiver;
import com.lhs.sensorgui.sensor.torque.SensorReader;

/**
 * A placeholder fragment containing a simple view.
 */
public class LessFragment extends Fragment {

    private static String LES_POWER_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_POWER_VALUE";
    private static String LES_TORQUE_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_TORQUE_VALUE";
    private static String LES_CADENCE_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_CADENCE_VALUE";
    private static String LES_LEFT_LEG_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_LEFT_LEG_VALUE";
    private static String LES_RIGHT_LEG_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_RIGHT_LEG_VALUE";
    private static String LES_DISTANCE_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_DISTANCE_VALUE";
    private static String LES_CALORIES_VALUE = "com.lhs.sensorgui.view.fragment.LessFragment.LES_CALORIES_VALUE";

    private TextView txtLessPowerValue;
    private TextView txtLessTorqueValue;
    private TextView txtLessCadenceValue;

    private TextView txtLessLeftLegPercentValue;
    private TextView txtLessRightLegPercentValue;
    private TextView txtLessCaloriesValue;
    private TextView txtLessDistanceValue;

    public LessFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LessFragment newInstance(int sectionNumber) {
        LessFragment fragment = new LessFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_less, container, false);
        txtLessPowerValue   = (TextView) rootView.findViewById(R.id.txtLessPowerValue);
        txtLessTorqueValue  = (TextView) rootView.findViewById(R.id.txtLessTorqueValue);
        txtLessCadenceValue = (TextView) rootView.findViewById(R.id.txtLessCadenceValue);
        txtLessLeftLegPercentValue  = (TextView) rootView.findViewById(R.id.txtLessLeftLegPercentValue);
        txtLessRightLegPercentValue = (TextView) rootView.findViewById(R.id.txtLessRightLegPercentValue);
        txtLessDistanceValue = (TextView) rootView.findViewById(R.id.txtLessDistanceValue);
        txtLessCaloriesValue = (TextView) rootView.findViewById(R.id.txtLessCaloriesValue);

        if(savedInstanceState == null) {
            txtLessPowerValue.setText(R.string.default_value);
            txtLessTorqueValue.setText(R.string.default_value);
            txtLessCadenceValue.setText(R.string.default_value);
            txtLessLeftLegPercentValue.setText(R.string.default_value);
            txtLessRightLegPercentValue.setText(R.string.default_value);
            txtLessDistanceValue.setText(R.string.default_value);
            txtLessCaloriesValue.setText(R.string.default_value);
        }else{
            txtLessPowerValue.setText(savedInstanceState.getString(LES_POWER_VALUE));
            txtLessTorqueValue.setText(savedInstanceState.getString(LES_TORQUE_VALUE));
            txtLessCadenceValue.setText(savedInstanceState.getString(LES_CADENCE_VALUE));
            txtLessLeftLegPercentValue.setText(savedInstanceState.getString(LES_LEFT_LEG_VALUE));
            txtLessRightLegPercentValue.setText(savedInstanceState.getString(LES_RIGHT_LEG_VALUE));
            txtLessDistanceValue.setText(savedInstanceState.getString(LES_DISTANCE_VALUE));
            txtLessCaloriesValue.setText(savedInstanceState.getString(LES_CALORIES_VALUE));
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter inF = new IntentFilter(DataReadBroadcastReceiver.DATA_READ_BROADCAST);
        IntentFilter inF2 = new IntentFilter(LocationUpdatedBroadcastReceiver.LOCATION_UPDATED_BROADCAST);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(brDataRead, inF);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(brLocationUpdated, inF2);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(brDataRead);
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(brLocationUpdated);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LES_POWER_VALUE, txtLessPowerValue.getText().toString());
        outState.putString(LES_TORQUE_VALUE, txtLessTorqueValue.getText().toString());
        outState.putString(LES_CADENCE_VALUE, txtLessCadenceValue.getText().toString());
        outState.putString(LES_LEFT_LEG_VALUE, txtLessLeftLegPercentValue.getText().toString());
        outState.putString(LES_RIGHT_LEG_VALUE, txtLessRightLegPercentValue.getText().toString());
        outState.putString(LES_DISTANCE_VALUE, txtLessDistanceValue.getText().toString());
        outState.putString(LES_CALORIES_VALUE, txtLessCaloriesValue.getText().toString());
    }

    private LessDataReadBroadcastReceiver brDataRead = new LessDataReadBroadcastReceiver();
    private LessLocationUpdateBroadcastReceiver brLocationUpdated = new LessLocationUpdateBroadcastReceiver();

    private class LessDataReadBroadcastReceiver extends DataReadBroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            SensorData sensorData = SensorReader.getInstance(context, null).getLastReadData();
            txtLessTorqueValue.setText("" + sensorData.getTorque());
            txtLessCaloriesValue.setText(""
                    + (int)(app().getCurrentRide().getElapsedTime()
                        *(app()).getUserBMR()));
            txtLessCadenceValue.setText("" + sensorData.getRPM());
        }
    }

    private class LessLocationUpdateBroadcastReceiver extends LocationUpdatedBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            float totalDistance = GPSManager.getInstance(context).getTotalDistance();
            float maxSpeed = GPSManager.getInstance(context).getMaxSpeed();
            float avgSpeed = GPSManager.getInstance(context).getAvgSpeed();
            txtLessDistanceValue.setText(String.format("%.2f m", totalDistance));
            txtLessLeftLegPercentValue.setText(String.format("%.2f km/s", avgSpeed));
            txtLessRightLegPercentValue.setText(String.format("%.2f km/s", maxSpeed));
        }
    }

    private TorqueApp app(){
        return TorqueApp.getInstance(getContext());
    }
}

