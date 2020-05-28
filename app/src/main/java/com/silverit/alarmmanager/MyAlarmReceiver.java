package com.silverit.alarmmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
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


//        RemoteViews notificationView = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
//        notificationView.setTextViewText(R.id.textAlarmMessage, alarmMessage);


        String voice_name = intent.getStringExtra("voice_name");
        Boolean isPlayable = intent.getBooleanExtra("playable", false);
        String play_count = "";



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setChannelId(CHANNEL_ID);


        String alarmMessage;

        if(isPlayable){

            play_count = intent.getStringExtra("play_count");

            // Play recorded voice with voice_name play_count times
            alarmMessage = String.format("%s will play %s %s times as your schedule.", context.getString(R.string.app_name), voice_name, play_count);

        }else{

            // Show only push notification
            alarmMessage = String.format("This notification was sent from %s as your schedule.", context.getString(R.string.app_name));
        }

        Log.d("AlarmManager", alarmMessage);

        builder.setContentText(alarmMessage);
        android.app.Notification notification = builder.build();


        NotificationManager mNotificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.createNotificationChannel(mChannel);
        mNotificationManager.notify(notifyID , notification);
    }
}