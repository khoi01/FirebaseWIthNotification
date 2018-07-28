package com.masjid.khoi.masjidsultanazlanshah.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.masjid.khoi.masjidsultanazlanshah.EventDetailsActivity;
import com.masjid.khoi.masjidsultanazlanshah.R;
import com.masjid.khoi.masjidsultanazlanshah.fragment.MainActivity;

/**
 * Created by User on 10/16/2016.
 */

public class FcmMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0){
            Log.d("notification", "Message data payload: " + remoteMessage.getData());
            getInfoFromRemoteMessage(remoteMessage);
        }

        if(remoteMessage.getNotification() != null){
            Log.d("notification", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }



    }

    public void getInfoFromRemoteMessage(RemoteMessage remoteMessage){
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("body");
        String type = remoteMessage.getData().get("type");
        String id = remoteMessage.getData().get("id");

        startNotification(id,title,message,type);
    }





    public void startNotification(String id,String title,String message,String type){
        Log.i("notification","active");

        //check if notification eihter is reminder or new quote/event
        if (!type.equals("reminder") && id.equals("none")) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(message);
            notificationBuilder.setSmallIcon(R.drawable.icon_masjid_no_bg);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
            //super.onMessageReceived(remoteMessage);
        }else

        if(type.equals("reminder") && ! id.equals("none")) {

            Intent intent = new Intent(this, EventDetailsActivity.class);
            intent.putExtra("id",id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(message);
            notificationBuilder.setSmallIcon(R.drawable.icon_logo_only);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }

    }
}
