package com.pearl.subwayguider.locate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pearl.subwayguider.R;
import com.pearl.subwayguider.beans.BleDevice;

public class LocateActivity extends AppCompatActivity {

    private double distance;
    final static int ALARM = 1;
    private BleDevice ble;
    private int txpower;
    private double distance_warm;

    public static final String action = "com.pearl.subwayguider.scan.view.ScanActivity";

    DrawView drawView = null;
    private TextView distance_tv;
    private SeekBar distance_bar;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int rssi = intent.getExtras().getInt("rssi");
            distance = calculate(txpower, rssi);

            drawView.setCircleX(360);
            drawView.setCircleY(9*100-((float)distance)*100);

            drawView.invalidate();


            if (distance > distance_warm) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        distance_warm = 1.5;
        txpower = 0;


        drawView = (DrawView) findViewById(R.id.drawView);
        distance_tv = (TextView) findViewById(R.id.distance_tv);
        distance_bar = (SeekBar) findViewById(R.id.seekbar_dis);

        distance_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance_tv.setText("the warning distance is: " + (double) progress / 10);
                distance_warm = (double) progress / 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IntentFilter filter = new IntentFilter(LocateActivity.action);
        registerReceiver(broadcastReceiver, filter);

        ble = new BleDevice();
        ble = (BleDevice) getIntent().getParcelableExtra("com.pearl.subwayguider.beans");

        Log.e("blename", ble.getBlename().toString());
        txpower = ble.getTxPower();

        distance = calculate(ble.getTxPower(), ble.getRssi());


    }

    public double calculate(int txPower, int rssi) {
        return Math.pow(10d, ((double) (txPower - rssi)) / (10 * 2));
    }

}
