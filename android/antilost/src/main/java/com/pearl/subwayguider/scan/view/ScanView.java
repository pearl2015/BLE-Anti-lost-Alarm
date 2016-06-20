package com.pearl.subwayguider.scan.view;

import com.pearl.subwayguider.beans.BleDevice;

import java.util.ArrayList;

/**
 * Created by Administrator on 30/05/2016.
 */
public interface ScanView {


    void notifylist(BleDevice newdevice);

    ArrayList<BleDevice> getBlelist();

    void notifychange(int index, int rssi);

    void addDevice(BleDevice newdevice);

}
