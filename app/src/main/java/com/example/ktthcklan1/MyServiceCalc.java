package com.example.ktthcklan1;
import static com.example.ktthcklan1.MyApplication.CHANEL_ID;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class MyServiceCalc extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Nhat Hoang", "My Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String strDataIntent = intent.getStringExtra("key_data_intent");
        sendNotification(strDataIntent);
        return START_NOT_STICKY;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public double sub(double a, double b) {
        return a - b;
    }

    public double multi(double a, double b) {
        return a * b;
    }

    public double div(double a, double b) {
        return a / b;
    }

    public class MyBinder extends Binder {
        public MyServiceCalc getService() {
            return MyServiceCalc.this;
        }

        ;
    }

    private void sendNotification(String strData) {
        Intent intent = new Intent(this, CalcActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setContentText(strData)
                .setContentTitle("Phép Tính ")
                .setSound(null)
                .build();

        startForeground(1,
                notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Nhat Hoang", "MyService onDestroy");
    }
}
