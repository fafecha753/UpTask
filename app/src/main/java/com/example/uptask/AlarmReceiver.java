package com.example.uptask;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle= intent.getExtras();
        int id=bundle.getInt("alarmId");
        String titulo= bundle.getString("titulo");
        Intent service1 = new Intent(context, NotificationService.class);
        service1.putExtra("titulo", titulo);
        service1.putExtra("alarmId", id);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );

    }
}