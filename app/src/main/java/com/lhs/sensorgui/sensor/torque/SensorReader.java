package com.lhs.sensorgui.sensor.torque;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.domain.model.Ride;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Dragan on 8/23/2016.
 */
public class SensorReader {

    private final ThreadLocal<Handler> mHandler = new ThreadLocal<>();
    private Context context;
    private static SensorReader mSensorReader;

    private byte[] readCommand = new byte[]{ 0x3a,0x39,0x33,0x36,0x32,0x30,0x32,0x39,0x30,0x0d,0x0a};

    private UsbDevice mDevice;
    private UsbDeviceConnection usbConnection;
    private UsbSerialDevice serial;
    private boolean readDataFlag;
    private Ride currentRide;

    private SensorReader(Context context, UsbDevice usbDevice){
        this.context = context;
        mDevice = usbDevice;
        readDataFlag = false;

        serial = null;
        refreshDevices();
    }

    public static SensorReader getInstance(Context context, UsbDevice usbDevice){
        if(mSensorReader == null){
            mSensorReader = new SensorReader(context, usbDevice);
        }
        return mSensorReader;
    }

    public void startReading(Ride currentRide){
        if(!readDataFlag) {
        //    if(serial != null) {
                mHandler.set(new Handler());
                this.currentRide = currentRide;
                readDataFlag = true;
                //serial.open();
                //serial.setBaudRate(19200);
                //serial.setDataBits(UsbSerialInterface.DATA_BITS_8);
                //serial.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                //serial.setParity(UsbSerialInterface.PARITY_NONE);
                //serial.setStopBits(UsbSerialInterface.STOP_BITS_1);
                mHandler.get().post(r);
                // start periodically reading the data
        //    }
        }
    }

    public void refreshDevices(){
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext()){
            mDevice = deviceIterator.next();
            break;
        }

        if(mDevice != null){
            usbConnection = manager.openDevice(mDevice);
            serial = UsbSerialDevice.createUsbSerialDevice(mDevice, usbConnection);
        }
    }

    private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] bytes) {
            /*Intent i = new Intent(DATA_READ_BROADCAST);
            i.putExtra("DATA", new String(bytes));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);*/
        }
    };

    public void stopReading(){
        readDataFlag = false;
    }

    public SensorData getLastReadData(){
        return currentRide.getLastReadData();
    }

    public void addData(SensorData sd){
        currentRide.add(sd);
    }

    public boolean getIsReading(){
        return readDataFlag;
    }

    private Runnable r = new Runnable(){

        @Override
        public void run() {
            if(readDataFlag) {
                //new AsyncSensorReader(context, mSensorReader).execute();
                //serial.read(new UsbSerialInterface.UsbReadCallback() {
                //    @Override
                //    public void onReceivedData(byte[] bytes) {
                        // TODO convert real data and add it to rhe current ride
                        Random rand = new Random();
                        int torque = rand.nextInt(150);
                        int position = rand.nextInt(359);
                        int RPM = rand.nextInt(100);
                        SensorData sd = new SensorData(torque, position, RPM);
                        TorqueApp.getInstance(context).getCurrentRide().add(sd);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(DataReadBroadcastReceiver.DATA_READ_BROADCAST));
                //    }
                //});
                mHandler.get().postDelayed(r, 100);
            }
        }
    };

    public Ride getCurrentRide(){
        return this.currentRide;
    }
}
