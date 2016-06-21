package com.pearl.subwayguider.scan.model;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.pearl.subwayguider.Tools.Tools;
import com.pearl.subwayguider.beans.BleDevice;
import com.pearl.subwayguider.locate.LocateActivity;
import com.pearl.subwayguider.scan.presenter.ScanPresenter;

import java.util.ArrayList;

/**
 * Created by Victoria on 30/05/2016.
 */
public class ScanModelIm extends Service implements ScanModel {

    private Context context;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ScanPresenter scanPresenter;
    private ArrayList<BleDevice> bleList;

    private Handler mHandler;
    private boolean mScanning;

    private final static long SCAN_PERIOD = 10000000;

    public ScanModelIm() {
        bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mHandler = new Handler();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //start to execute tasks

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public ScanModelIm(Context context, ScanPresenter iscanPresenter) {
        this.context = context;
        bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mHandler = new Handler();

        this.scanPresenter = iscanPresenter;

        init();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        if (bluetoothManager == null)
            bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (null == bluetoothManager)
            Log.e("TAG", "null == bluetoothManager");
        if (null == mBluetoothAdapter)
            Log.e("TAG", "null == mBluetoothAdapter");
        if (!mBluetoothAdapter.isEnabled())
            Log.i("TAG", "enable mBluetoothAdapter");
        mBluetoothAdapter.enable();

        bleList = new ArrayList<BleDevice>();

    }


    @Override
    public ArrayList<BleDevice> getList() {
        return bleList;
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    int major = ((scanRecord[25] & 0xFF) << 8)
                            | (scanRecord[26] & 0xFF);
                    int minor = ((scanRecord[27] & 0xFF) << 8)
                            | (scanRecord[28] & 0xFF);
                    byte txpw = scanRecord[29];


                    String major_s = "0" + Tools.decTohex(major + "");
                    String minor_s = "0" + Tools.decTohex(minor + "");


                    String locate = major_s + minor_s;

                    String name = Tools.SampleBleNames.lookup(locate);




                    //if(!name.equals("unknown device")) {

                        //定义一个intent
                        Intent intent = new Intent().setAction(LocateActivity.action).putExtra("rssi", rssi);
                        //广播出去
                        context.sendBroadcast(intent);
                        BleDevice newdevice = new BleDevice(name, major_s, minor_s, txpw, rssi);
                        addDevice(newdevice);
                 //   }
                }
            });
        }

    };


    @Override
    public ArrayList<BleDevice> findBleDevices(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

        return bleList;
    }


    @Override
    public void addDevice(BleDevice newdevice) {
        scanPresenter.addDevice(newdevice);
    }

    public class MyBinder extends Binder {
        public void startScan() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //start detail tasks
                    findBleDevices(true);
                }
            }).start();
        }
    }
}

