package in.ac.siesgst.arena.epicentre.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import in.ac.siesgst.arena.epicentre.R;

/**
 * Created by SIESGSTarena on 06/12/18.
 */
public class FirebaseUtils extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            // TODO: Add a check to show notification for signed in users only
            createNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    // TODO: Work On Btter Notification Views and Grouping Maybe
    private void createNotification(String title, String body) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, title)
                        .setSmallIcon(R.drawable.ic_notifcation)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // For Android Oreo and above, create a Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                NotificationChannel channel = new NotificationChannel(title, title + " Notifications",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Notify by building above entities
        try {
            notificationManager.notify((int) new Date().getTime() / 1000, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
