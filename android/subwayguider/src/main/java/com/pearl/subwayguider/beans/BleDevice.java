package com.pearl.subwayguider.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 30/05/2016.
 */
public class BleDevice implements Parcelable{

    private String blename;
    private String major;
    private String minor;
    private int txPower;
    private int rssi;

    public BleDevice(){
        this.blename = null;
        this.major = null;
        this.minor = null;
    }
    public BleDevice(String blename, String major, String minor, int txPower, int rssi) {
        this.blename = blename;
        this.major = major;
        this.minor = minor;
        this.txPower = txPower;
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public String getBlename() {
        return blename;
    }

    public void setBlename(String blename) {
        this.blename = blename;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }


    public static final Parcelable.Creator<BleDevice> CREATOR = new Creator<BleDevice>() {
        @Override
        public BleDevice createFromParcel(Parcel source) {

            BleDevice ble = new BleDevice();
            ble.setBlename(source.readString());
            ble.setMajor(source.readString());
            ble.setMinor(source.readString());
            ble.setTxPower(source.readByte());
            ble.setRssi(source.readInt());

            return ble;
        }

        @Override
        public BleDevice[] newArray(int size) {
            return new BleDevice[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(blename);
        dest.writeString(major);
        dest.writeString(minor);
        dest.writeInt(txPower);
        dest.writeInt(rssi);

    }
}
