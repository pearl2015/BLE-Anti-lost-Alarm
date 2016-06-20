package com.pearl.subwayguider.scan.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pearl.subwayguider.R;
import com.pearl.subwayguider.beans.BleDevice;
import com.pearl.subwayguider.locate.LocateActivity;
import com.pearl.subwayguider.scan.model.ScanModelIm;
import com.pearl.subwayguider.scan.presenter.ScanPresenterIm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScanActivity extends AppCompatActivity implements ScanView {

    private ScanPresenterIm scanpresenter;
    private Button scan_btn;
    private Button stop_btn;

    private boolean btnstatus;
    private ListView devicelist_lv;

    private SimpleAdapter simpleAdapter;
    private ArrayList<BleDevice> blelist;
    private List<Map<String, Object>> list;


    private int service_start_count;
    private ScanModelIm.MyBinder myBinder;

    HashMap<String, Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btnstatus = true;

        //init
        scanpresenter = new ScanPresenterIm(this, getApplicationContext());
        scan_btn = (Button)ScanActivity.this.findViewById(R.id.scan_btn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnstatus) {
                    if(service_start_count==0)
                    {
                        Intent intent = new Intent(getApplicationContext(), ScanModelIm.class);
                        bindService(intent,connection, Context.BIND_AUTO_CREATE);
                    }

                    scanpresenter.scan(true);
                    scan_btn.setText("stop");
                    btnstatus = false;
                } else {
                    scanpresenter.scan(false);
                    scan_btn.setText("scan");
                    btnstatus = true;
                }
            }
        });
        initlist();
    }

    public void initlist() {

        //list
        blelist = new ArrayList<BleDevice>();
        devicelist_lv = (ListView) findViewById(R.id.bledevice_list);
        String[] strings = {"devicename", "major", "minor", "txpw", "rssi"};
        int[] ids = {R.id.blename_tv, R.id.blemajor_tv,
                R.id.bleminor_tv, R.id.bletxpw_tv, R.id.blerssi_tv};
        simpleAdapter = new SimpleAdapter(this, getBLE(),
                R.layout.device_item_fragement, strings, ids);


        devicelist_lv.setAdapter(simpleAdapter);
        devicelist_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BleDevice newdevice = new BleDevice();

                ListView listviews = (ListView)parent;
                map = (HashMap<String, Object>) listviews.getItemAtPosition(position);
                newdevice.setBlename((String)map.get("devicename"));

                //Toast.makeText(getApplicationContext(),map.get("devicename"),Toast.LENGTH_SHORT).show();


                Integer tx = (Integer)map.get("txpw");
                int tx_value = tx.intValue();

                Integer rssi = (Integer)map.get("rssi");
                int rssi_value = rssi.intValue();


                newdevice.setTxPower(tx_value);
                newdevice.setRssi(rssi_value);
//
//               // Toast.makeText(getApplicationContext(),blename,Toast.LENGTH_SHORT).show();
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("com.pearl.subwayguider.beans", newdevice);


                //跳转
                Intent intent = new Intent();
                intent.setClass(ScanActivity.this, LocateActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);


            }
        });
    }

    public List<Map<String, Object>> getBLE() {
        list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        if (blelist != null) {
            for (BleDevice a : blelist) {
                map.put("devicename", a.getBlename());
                map.put("major", a.getMajor());
                map.put("minor", a.getMinor());
                map.put("txpw", a.getTxPower());
                map.put("rssi", a.getRssi());

                list.add(map);
            }

        }

        return list;
    }


    @Override
    public void notifylist(BleDevice newdevice) {
        Map<String, Object> map = new HashMap<String, Object>();


        map.put("devicename", newdevice.getBlename());
        map.put("major", newdevice.getMajor());
        map.put("minor", newdevice.getMinor());
        map.put("txpw", newdevice.getTxPower());
        map.put("rssi", newdevice.getRssi());

        int rssi = newdevice.getRssi();
        list.add(map);

        simpleAdapter.notifyDataSetChanged();

    }



    @Override
    public void addDevice(BleDevice newdevice) {
        blelist.add(newdevice);
    }

    @Override
    public ArrayList<BleDevice> getBlelist() {
        return blelist;
    }

    @Override
    public void notifychange(int index, int rssi) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("devicename", blelist.get(index).getBlename());
        map.put("major", blelist.get(index).getMajor());
        map.put("minor", blelist.get(index).getMinor());
        map.put("txpw", blelist.get(index).getTxPower());
        map.put("rssi", rssi);

        list.set(index, map);

        simpleAdapter.notifyDataSetChanged();
    }



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (ScanModelIm.MyBinder) service;
            myBinder.startScan();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
