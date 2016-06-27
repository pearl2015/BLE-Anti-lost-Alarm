package com.pearl.subwayguider.scan.presenter;

import android.content.Context;

import com.pearl.subwayguider.beans.BleDevice;
import com.pearl.subwayguider.scan.model.ScanModel;
import com.pearl.subwayguider.scan.model.ScanModelIm;
import com.pearl.subwayguider.scan.view.ScanView;

import java.util.ArrayList;

/**
 * Created by Administrator on 30/05/2016.
 */

public class ScanPresenterIm implements ScanPresenter {

    private ScanView scanview_ins;
    private ScanModel scanModel_ins;
    private Context context;

    public ScanPresenterIm(ScanView iScanView, Context context) {
        this.scanview_ins = iScanView;
        this.context = context;
        this.scanModel_ins = new ScanModelIm(this.context, this);
    }

    @Override
    public void scan(boolean isscan) {
        scanModel_ins.findBleDevices(isscan);
    }

    @Override
    public void addDevice(BleDevice newdevice) {

        ArrayList<BleDevice> oldlist = new ArrayList<BleDevice>();
        oldlist = scanview_ins.getBlelist();


        String name = newdevice.getBlename();

        boolean is = false;
        int index = -1;
        if (oldlist != null) {

            for (BleDevice abc : oldlist) {
                index++;
                if (abc.getBlename().equals(name)) {
                    is = true;
                    break;
                }
            }

        }
        if (is) {
            oldlist.set(index, newdevice);
            scanview_ins.notifychange(index, newdevice.getRssi());
        } else {
            scanview_ins.addDevice(newdevice);
            scanview_ins.notifylist(newdevice);
        }

    }



}
