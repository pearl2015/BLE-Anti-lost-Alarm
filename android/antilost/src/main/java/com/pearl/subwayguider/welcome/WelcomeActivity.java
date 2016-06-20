package com.pearl.subwayguider.welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pearl.subwayguider.MainActivity;
import com.pearl.subwayguider.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        go2Main();
    }

    @Override
    public void go2Main() {
        Timer timer = new Timer();
        final Intent intent  = new Intent(this, MainActivity.class);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        };
        timer.schedule(task, 1000);
    }
}
