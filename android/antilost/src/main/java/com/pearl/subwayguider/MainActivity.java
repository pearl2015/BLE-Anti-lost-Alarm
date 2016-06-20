package com.pearl.subwayguider;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.pearl.subwayguider.scan.view.ScanActivity;

public class MainActivity extends TabActivity {

    private TabHost myTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        myTabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        //the first tab
        intent = new Intent(this, ScanActivity.class);
        spec = myTabHost.newTabSpec("scantab")
                .setIndicator("", getResources().getDrawable(R.drawable.ble))
                .setContent(intent);
        myTabHost.addTab(spec);


        //the second tab
//        intent = new Intent(this, MapActivity.class);
//        spec = myTabHost.newTabSpec("maptab")
//                .setIndicator("", getResources().getDrawable(R.drawable.map))
//                .setContent(intent);
//        myTabHost.addTab(spec);
//
//        //the third tab
//        intent = new Intent(this, SettingActivity.class);
//        spec = myTabHost.newTabSpec("settingtab")
//                .setIndicator("", getResources().getDrawable(R.drawable.setting))
//                .setContent(intent);
//        myTabHost.addTab(spec);

        myTabHost.setCurrentTab(0);
    }
}
