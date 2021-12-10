package com.fpoly.managebookings.tool.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.fpoly.managebookings.MainActivity;
import com.fpoly.managebookings.MyApplication;
import com.fpoly.managebookings.R;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.login.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseNotification extends FirebaseMessagingService {

    private static final String TAG = "FirebaseNotification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        if (notification != null) {
//            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            String title = notification.getTitle();
//            String message = notification.getBody();
//
//            sendNotification(title,message);
//        }else {
//            return;
//        }

        //Data message
        Map<String, String> stringMap = remoteMessage.getData();

        String title = stringMap.get("title");
        String body = stringMap.get("message");
//        Log.e(TAG, ""+SharedPref_InfoUser.getInstance(this).LoggedInEmail());
        if (SharedPref_InfoUser.getInstance(this).LoggedInEmail() != null) {

            sendNotification(title, body);
        }
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);

        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String token) {
    }

    private void sendNotification(String title, String message) {

            Intent intent = new Intent(this, ListOrderWaitingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setDefaults(Notification.DEFAULT_ALL);

            Notification notification = notificationBuilder.build();

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager != null) {
                manager.notify(0, notification);
            }

    }
}
