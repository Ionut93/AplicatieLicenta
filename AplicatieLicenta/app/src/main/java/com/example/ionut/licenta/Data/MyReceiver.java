package com.example.ionut.licenta.Data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ionut on 6/25/2015.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,MyAlarmService.class);
        context.startService(i);
    }
}
