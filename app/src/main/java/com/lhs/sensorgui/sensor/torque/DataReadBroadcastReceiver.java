package com.lhs.sensorgui.sensor.torque;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dragan on 8/23/2016.
 */
public abstract class DataReadBroadcastReceiver extends BroadcastReceiver {
    public static final String DATA_READ_BROADCAST = "com.lhs.sensorgui.sensor.torque.DataReadBroadcastReceiver.BROADCAST_FLAG";
}
