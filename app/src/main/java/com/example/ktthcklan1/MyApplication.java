package com.example.ktthcklan1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {

    public static final String CHANEL_ID = "con_cac";

    @Override
    public void onCreate() {
        super.onCreate();

        createChanelNotification();
    }

    private void createChanelNotification() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,
                    "Chanel Service Example",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setSound(null,null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null) {
                manager.createNotificationChannel(channel);
            }

        }
    }
}
