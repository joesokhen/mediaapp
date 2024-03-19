package com.example.austmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button play, pause, stop;
    MediaPlayer mp;
    ListView mediaList;
    int flag =1;
    List <String> mediaFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.btnPlay);
        pause = findViewById(R.id.btnPause);
        stop = findViewById(R.id.btnStop);
        mediaList = findViewById(R.id.lvMedia);


        mediaFiles = getRawResourceNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_media_item, mediaFiles);
        mediaList.setAdapter(adapter);

        mediaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playMedia(position);
            }
    });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0 || mp == null) {
                    // On cold app boot, if play button is pressed: play default position
                    playMedia(0);
                    flag = 1;
                } else {
                    mp.start();
                    updateButtonStates(false, true,true);
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null && mp.isPlaying()) {
                    mp.pause();
                    updateButtonStates(true, false, false);
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMedia();
                updateButtonStates(true, false, false);
                flag = 0;
            }
        });

    }
    private void playMedia(int position) {
        stopMedia();

        int resId = getResources().getIdentifier(mediaFiles.get(position), "raw", getPackageName());
        mp = MediaPlayer.create(this, resId);
        mp.start();
        updateButtonStates(false, true, true);
        flag = 1;
    }

    private void stopMedia() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
            flag = 0;
        }
    }

    private void updateButtonStates(boolean playEnabled, boolean pauseEnabled, boolean stopEnabled) {
        play.setEnabled(playEnabled);
        pause.setEnabled(pauseEnabled);
        stop.setEnabled(stopEnabled);
    }
    private List<String> getRawResourceNames() {
        List<String> rawList = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            rawList.add(field.getName());
        }
        return rawList;
    }
}