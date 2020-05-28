package com.silverit.alarmmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyAlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
        Log.d("MyAlarmReceiver", "Alarm went off");


        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "channel_name";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);


        /*RemoteViews notificationView = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
        notificationView.setTextViewText(R.id.txt_songname,songName);
        notificationView.setTextViewText(R.id.txt_songnamear,songNamear);
        SharedPreferences.Editor editor = getSharedPreferences("juke1", MODE_PRIVATE).edit();
        editor.putString("pos",""+intPOs);
        editor.commit();
        Intent notificationIntent = new Intent(this, MusicPlayerActivity.class);
        notificationIntent.putExtra("Position",intPOs);
        notificationIntent.putExtra("page","not");
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;

        //this is the intent that is supposed to be called when the button is clicked

        Intent switchIntent = new Intent(Constants.NOTIFICATION_CLICK_ACTION);
        switchIntent.setAction(Constants.ACTION_PLAY);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(MusicPlayerActivity.this, 100, switchIntent, 0);

        Intent switchIntent1 = new Intent(Constants.NOTIFICATION_CLICK_ACTION);
        switchIntent1.setAction(Constants.ACTION_NEXT);
        PendingIntent pendingSwitchIntent1 = PendingIntent.getBroadcast(MusicPlayerActivity.this, 101, switchIntent1, 0);

        Intent switchIntent2 = new Intent(Constants.NOTIFICATION_CLICK_ACTION);
        switchIntent2.setAction(Constants.ACTION_PREV);
        PendingIntent pendingSwitchIntent2 = PendingIntent.getBroadcast(MusicPlayerActivity.this, 102, switchIntent2, 0);

        Intent switchIntent3 = new Intent(Constants.NOTIFICATION_CLICK_ACTION);
        switchIntent3.setAction(Constants.ACTION_CLOSE);
        PendingIntent pendingSwitchIntent3 = PendingIntent.getBroadcast(MusicPlayerActivity.this, 103, switchIntent3, 0);

        notificationView.setOnClickPendingIntent(R.id.btn_play_pause_in_notification, pendingSwitchIntent);
        notificationView.setOnClickPendingIntent(R.id.btn_next, pendingSwitchIntent1);
        notificationView.setOnClickPendingIntent(R.id.btn_prev, pendingSwitchIntent2);
        notificationView.setOnClickPendingIntent(R.id.btn_close, pendingSwitchIntent3);
        notificationManager.notify(1, notification);*/



        android.app.Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setChannelId(CHANNEL_ID).build();


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager.notify(notifyID , notification);
    }
}