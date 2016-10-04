package com.lhs.sensorgui.sensor.torque;

/**
 * Created by Dragan on 8/23/2016.
 */
public class SensorData {

    private int torque;
    private int position;
    private int RPM;

    public SensorData(int torque, int position, int RPM){
        this.torque = torque;
        this.position = position;
        this.RPM = RPM;
    }

    public int getTorque() {
        return torque;
    }

    public int getPosition() {
        return position;
    }

    public int getRPM() { return RPM; }

    public String[] getDataAsStringArray(){
        return new String[]{""+torque, ""+position, ""+RPM};
    }

}
