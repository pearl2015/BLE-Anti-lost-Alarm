package com.pearl.subwayguider.scan.presenter;

import com.pearl.subwayguider.beans.BleDevice;

import java.util.ArrayList;

/**
 * Created by Administrator on 30/05/2016.
 */
public interface ScanPresenter{

    public void scan(boolean isscan); //stop or start scan
    //method 2
    public void addDevice(BleDevice newdevice);
}
