package com.lhs.sensorgui.view.fragment;

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
import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.sensor.gps.GPSManager;
import com.lhs.sensorgui.sensor.gps.LocationUpdatedBroadcastReceiver;
import com.lhs.sensorgui.sensor.torque.DataReadBroadcastReceiver;
import com.lhs.sensorgui.sensor.torque.SensorData;
import com.lhs.sensorgui.sensor.torque.SensorReader;

import org.w3c.dom.Text;

/**
 * Created by Dragan on 9/29/2016.
 */
public class MoreFragment extends Fragment {

    private TextView txtMoreCaloriesValue;

    private TextView txtMoreCurrentPowerValue;
    private TextView txtMoreLeftCurrentPowerValue;
    private TextView txtMoreLeftMaxPowerValue;
    private TextView txtMoreLeftAvgPowerValue;
    private TextView txtMoreMaxPowerValue;
    private TextView txtMoreRightCurrentPowerValue;
    private TextView txtMoreRightMaxPowerValue;
    private TextView txtMoreRightAvgPowerValue;
    private TextView txtMoreAvgPowerValue;
    private TextView txtMoreTorqueLeftMaxValue;
    private TextView txtMoreTorqueLeftPercentValue;
    private TextView txtMoreMaxTorqueValue;
    private TextView txtMoreCurrentTorqueValue;
    private TextView txtMoreCadenceCurrentValue;
    private TextView txtMoreTorqueRightMaxValue;
    private TextView txtMoreTorqueRightPercentValue;
    private TextView txtMoreAvgTorqueValue;
    private TextView txtMoreCadenceMaxValue;
    private TextView txtMoreCadenceAvgValue;
    private TextView txtMoreDistanceValue;
    private TextView txtMoreTotalTimeValue;
    private TextView txtMoreMaxSpeedValue;
    private TextView txtMoreAvgSpeedValue;


    public MoreFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MoreFragment newInstance(int sectionNumber) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more, container, false);
        txtMoreCaloriesValue = (TextView) rootView.findViewById(R.id.txtMoreCaloriesValue);
        txtMoreCurrentPowerValue = (TextView) rootView.findViewById(R.id.txtMoreCurrentPowerValue);
        txtMoreCurrentTorqueValue = (TextView) rootView.findViewById(R.id.txtMoreCurrentTorqueValue);
        txtMoreCadenceCurrentValue = (TextView) rootView.findViewById(R.id.txtMoreCadenceCurrentValue);
        txtMoreCurrentPowerValue = (TextView) rootView.findViewById(R.id.txtMoreCurrentPowerValue);
        txtMoreLeftCurrentPowerValue = (TextView) rootView.findViewById(R.id.txtMoreLeftCurrentPowerValue);
        txtMoreLeftMaxPowerValue = (TextView) rootView.findViewById(R.id.txtMoreLeftMaxPowerValue);
        txtMoreLeftAvgPowerValue = (TextView) rootView.findViewById(R.id.txtMoreLeftAvgPowerValue);
        txtMoreMaxPowerValue = (TextView) rootView.findViewById(R.id.txtMoreMaxPowerValue);
        txtMoreRightCurrentPowerValue = (TextView) rootView.findViewById(R.id.txtMoreRightCurrentPowerValue);
        txtMoreRightMaxPowerValue = (TextView) rootView.findViewById(R.id.txtMoreRightMaxPowerValue);
        txtMoreRightAvgPowerValue = (TextView) rootView.findViewById(R.id.txtMoreRightAvgPowerValue);
        txtMoreAvgPowerValue = (TextView) rootView.findViewById(R.id.txtMoreAvgPowerValue);
        txtMoreTorqueLeftMaxValue = (TextView) rootView.findViewById(R.id.txtMoreTorqueLeftMaxValue);
        txtMoreTorqueLeftPercentValue = (TextView) rootView.findViewById(R.id.txtMoreTorqueLeftPercentValue);
        txtMoreMaxTorqueValue = (TextView) rootView.findViewById(R.id.txtMoreMaxTorqueValue);
        txtMoreTorqueRightMaxValue = (TextView) rootView.findViewById(R.id.txtMoreTorqueRightMaxValue);
        txtMoreTorqueRightPercentValue = (TextView) rootView.findViewById(R.id.txtMoreTorqueRightPercentValue);
        txtMoreAvgTorqueValue = (TextView) rootView.findViewById(R.id.txtMoreAvgTorqueValue);
        txtMoreCadenceMaxValue = (TextView) rootView.findViewById(R.id.txtMoreCadenceMaxValue);
        txtMoreCadenceAvgValue = (TextView) rootView.findViewById(R.id.txtMoreCadenceAvgValue);
        txtMoreDistanceValue = (TextView) rootView.findViewById(R.id.txtMoreDistanceValue);
        txtMoreTotalTimeValue = (TextView) rootView.findViewById(R.id.txtMoreTotalTimeValue);
        txtMoreMaxSpeedValue = (TextView) rootView.findViewById(R.id.txtMoreMaxSpeedValue);
        txtMoreAvgSpeedValue = (TextView) rootView.findViewById(R.id.txtMoreAvgSpeedValue);

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

    private MoreDataReadBroadcastReceiver brDataRead = new MoreDataReadBroadcastReceiver();
    private MoreLocationUpdateBroadcastReceiver brLocationUpdated = new MoreLocationUpdateBroadcastReceiver();

    private class MoreDataReadBroadcastReceiver extends DataReadBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            SensorData sensorData = SensorReader.getInstance(context, null).getLastReadData();
            txtMoreCurrentTorqueValue.setText("" + sensorData.getTorque());
            txtMoreCaloriesValue.setText(""+(int)(app().getCurrentRide().getElapsedTime()*(app()).getUserBMR()));
            txtMoreCadenceCurrentValue.setText("" + sensorData.getRPM());
            txtMoreMaxTorqueValue.setText("" + app().getSR().getCurrentRide().getMaxTorque());
            txtMoreCadenceMaxValue.setText("" + app().getSR().getCurrentRide().getMaxCadence());

            int sec = (int)app().getCurrentRide().getElapsedTime();
            int min = sec/60;
            sec %= 60;
            txtMoreTotalTimeValue.setText(min+":"+((sec<10)?"0"+sec:sec));
        }
    }

    private class MoreLocationUpdateBroadcastReceiver extends LocationUpdatedBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            float totalDistance = GPSManager.getInstance(context).getTotalDistance();
            float maxSpeed = GPSManager.getInstance(context).getMaxSpeed();
            float avgSpeed = GPSManager.getInstance(context).getAvgSpeed();
            txtMoreDistanceValue.setText(String.format("%.2f", totalDistance));
            txtMoreAvgSpeedValue.setText(String.format("%.2f", avgSpeed));
            txtMoreMaxSpeedValue.setText(String.format("%.2f", maxSpeed));
        }
    }

    private TorqueApp app(){
        return TorqueApp.getInstance(getContext());
    }
}
