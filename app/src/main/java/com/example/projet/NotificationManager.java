package com.example.projet;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.Manifest;


import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationManager extends BroadcastReceiver {
    static String CHANNEL_ID= "channel_01";
    static int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.day_calendar)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("text"))
                .build();

        sendNotification(context,notification);
    }

    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel(Context context) {
        // Create a NotificationChannel, only for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                    android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channel.setVibrationPattern(new long[] { 1000, 1000, 1000, 1000, 1000 });
            channel.enableLights(true);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(Context context, Notification notification)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        if(!prefs.getBoolean("Notifications",false))
//            return;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED)
            return;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public static void scheduleNotification(Context context, long time, String title, String text) {
        Intent intent = new Intent(context, NotificationManager.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pending = PendingIntent.getBroadcast(context, 42, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Schdedule notification
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pending);
    }

    public static void cancelNotification(Context context, String title, String text) {
        Intent intent = new Intent(context, NotificationManager.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pending = PendingIntent.getBroadcast(context, 42, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Cancel notification
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pending);
    }

}
