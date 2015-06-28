package com.example.ionut.licenta.Data;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.ionut.licenta.Activities.MainActivity;
import com.example.ionut.licenta.R;

import java.util.Random;

/**
 * Created by Ionut on 6/25/2015.
 */
public class MyAlarmService extends Service {

    private NotificationManager notificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        notificationManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        Random r = new Random();
        MainActivity.factNumber = r.nextInt(MainActivity.max - MainActivity.min + 1) + MainActivity.min;
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                                                    .setSmallIcon(R.drawable.ic_launcher)
                                                    .setContentTitle("Did you know...?")
                                                    .setContentText(MainActivity.facts.get(MainActivity.factNumber).getFact());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent1);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(0,nBuilder.build());


        return 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
