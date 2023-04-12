package com.example.projet;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings extends AppCompatActivity {

    Switch swDarkMode;
    ImageView btPlayMusic;
    boolean isPlaying;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        swDarkMode = findViewById(R.id.settings_dark_mode_switch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            swDarkMode.setChecked(true);

        btPlayMusic = findViewById(R.id.settings_music_bt);
        isPlaying = isMyServiceRunning(MusicService.class);
        if(isPlaying)
            btPlayMusic.setImageResource(R.drawable.stop);
    }

    public void playAndPauseMusic(View view){
        if (isPlaying) {
            isPlaying = false;
            btPlayMusic.setImageResource(R.drawable.play);
            stopService(new Intent(this, MusicService.class));
        }
        else {
            isPlaying = true;
            btPlayMusic.setImageResource(R.drawable.stop);
            Intent serviceIntent = new Intent(getApplicationContext(), MusicService.class);
            startService(serviceIntent);
        }
    }

    public void switchTheme(View view){
        if(swDarkMode.isChecked())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @SuppressWarnings("deprecation")
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settings_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_back) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
