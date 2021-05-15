package com.bakrin.fblive.service;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bakrin.fblive.R;
import com.bakrin.fblive.info.Info;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.fcm.MessagingService;

import org.jetbrains.annotations.NotNull;

public class NotificationsMessagingService extends MessagingService implements Info {

    Context context;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {

        context = this;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        Log.i(TAG, "onMessageReceived: Messaging service");
        if (remoteMessage.getNotification() != null) {
            String string = remoteMessage.getNotification().getBody();
            String[] parts = string.split("(?<=#)");
            String title,
                    subtitle = "",
                    subtitle2 = "",
                    notification_id = "";


            title = remoteMessage.getNotification().getTitle();
            try {
                subtitle = parts[0];
                subtitle2 = parts[1];
                notification_id = parts[2];
            } catch (Exception e) {
                e.printStackTrace();
            }

            subtitle2 = subtitle2.replace("#","");
            subtitle = subtitle.replace("#","");


            Log.i(TAG, "onMessageReceived: " + remoteMessage.getNotification().getBody());
            Log.i(TAG, "onMessageReceived: " + title);
            Log.i(TAG, "onMessageReceived: " + subtitle);
            Log.i(TAG, "onMessageReceived: " + subtitle2);
            Log.i(TAG, "onMessageReceived: " + notification_id);

//            generateCustomLayoutNotification(
//                    title,
//                    subtitle,
//                    subtitle2,
//                    Integer.parseInt(notification_id));
        }

    }


    public void generateCustomLayoutNotification(String title, String subTitle, String subtitle2, int id) {
        Log.i(TAG, "///////generateNotification: ");

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        collapsedView.setTextViewText(R.id.title, title);
        collapsedView.setTextViewText(R.id.subtitle, subTitle);
        collapsedView.setTextViewText(R.id.subtitle_2, subtitle2);
//
//        Intent clickIntent = new Intent(context, FixtureDetailsActivity.class);
//        clickIntent.putExtra("fixture", fixtureItem);
//        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID + id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(collapsedView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setContentIntent(clickPendingIntent)
                .build();

        notificationManagerCompat.notify(id, notification);


    }
}
