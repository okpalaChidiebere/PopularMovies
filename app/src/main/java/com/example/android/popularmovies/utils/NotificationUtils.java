package com.example.android.popularmovies.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.android.popularmovies.R;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationUtils {

    private static final int MOVIES_NOTIFICATION_ID = 3004;

    /**
     * This notification channel id is used to link notifications to this channel
     */
    private static final String MOVIES_NOTIFICATION_CHANNEL_ID = "movies_notification_channel";


    public static void notifyUserOfNewWeather(Context context) {

        String notificationTitle = context.getString(R.string.app_name);
        final String notificationText = "Movies list successfully Synchronized";

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Create a notification channel for Android O devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    MOVIES_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, MOVIES_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.play_arrow_black_36) //a must have
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                //.setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);

        /*I really dont need pending intent. Maybe add it later to load main Activity*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(MOVIES_NOTIFICATION_ID, notificationBuilder.build());

    }
}
