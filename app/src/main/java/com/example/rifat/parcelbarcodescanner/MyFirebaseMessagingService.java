package com.example.rifat.parcelbarcodescanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG="MyFirebaseMessagingServ";

    @Override
    public void onNewToken(String s) {

        Log.d(TAG, "onNewToken: "+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: "+ remoteMessage.getFrom());
        Log.d(TAG, "Notification: "+ Objects.requireNonNull(remoteMessage.getNotification()).getBody());

       /* NotificationManager notification= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder(getApplicationContext()).
                setContentText(remoteMessage.getNotification().getBody())
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        if (notification != null) {
            notification.notify(0, notify);
        }*/


    }
}
