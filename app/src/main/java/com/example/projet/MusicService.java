package com.example.projet;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        player.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                player = MediaPlayer.create(getApplicationContext(), R.raw.music_lofi);
                player.setLooping(true);
                player.start();
            }
        });
        t.start();
        return START_STICKY;
    }
}
