package com.example.austmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button play, pause, stop;
    MediaPlayer mp;
    int flag =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.btnPlay);
        pause = findViewById(R.id.btnPause);
        stop = findViewById(R.id.btnStop);

        mp = MediaPlayer.create(this, R.raw.sample_getyoutothemoon);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag ==0)
                    mp = MediaPlayer.create(MainActivity.this, R.raw.sample_getyoutothemoon);

                mp.start();
                play.setEnabled(false);
                pause.setEnabled(true);
                stop.setEnabled(true);
                flag = 1;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                stop.setEnabled(false);
                pause.setEnabled(false);
                play.setEnabled(true);
                flag = 0;
            }
        });

    }
}