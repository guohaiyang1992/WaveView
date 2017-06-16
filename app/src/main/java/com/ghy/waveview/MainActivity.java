package com.ghy.waveview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ghy.wave.ui.WaveView;


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

        findViewById(R.id.changeColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.setColor(0x000000);
            }
        });
        findViewById(R.id.changeNum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.setWaveCount(3);
            }
        });
        findViewById(R.id.changeDuration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.setDuration(2500);
            }
        });

    }
}
