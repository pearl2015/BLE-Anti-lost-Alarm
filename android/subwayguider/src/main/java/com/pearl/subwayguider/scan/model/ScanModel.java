package com.pearl.subwayguider.scan.model;

import android.content.Context;

import com.pearl.subwayguider.beans.BleDevice;

import java.util.ArrayList;

/**
 * Created by Administrator on 30/05/2016.
 */
public interface ScanModel {

    public ArrayList<BleDevice> findBleDevices(boolean enable);
    public void init();

    public ArrayList<BleDevice> getList();


    //method 2
    public void addDevice(BleDevice newdevice);
}
