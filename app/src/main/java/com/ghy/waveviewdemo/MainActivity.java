package com.ghy.waveviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ghy.corelib.ui.WaveView;

public class MainActivity extends AppCompatActivity {
    WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waveView = (WaveView) findViewById(R.id.waveView);
        findViewById(R.id.turnOn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.startAnim();
            }
        });
        findViewById(R.id.turnOff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.endAnim();
            }
        });
    }
}
