package com.bakrin.fblive;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.info.Info;


public class MainApplication extends Application implements Info {

    private static MainApplication instance;
    private APIManager apiManager;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        apiManager = APIManager.getInstance(this);
        createNotificationChannel();
        createNotificationChannel2();
        createNotificationChannel3();
        createNotificationChannel4();
        createNotificationChannel5();
        createNotificationChannel6();
    }

    public APIManager getApiManager() {
        return apiManager;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_1,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_2,
                    CHANNEL_NAME_2,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION_2);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel3() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_3,
                    CHANNEL_NAME_3,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION_3);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel4() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_4,
                    CHANNEL_NAME_4,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION_4);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel5() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_5,
                    CHANNEL_NAME_5,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION_5);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel6() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_6,
                    CHANNEL_NAME_6,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION_6);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}
