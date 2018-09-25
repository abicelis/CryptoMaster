package ve.com.abicelis.cryptomaster.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.service.AlarmSnoozeReceiver;
import ve.com.abicelis.cryptomaster.ui.coindetail.CoinDetailActivity;

/**
 * Created by abicelis on 21/9/2018.
 */
public class NotificationUtil {

    public static void showNotification(Context context, long coinId, long alarmId, @NonNull String channelId, @DrawableRes int smallIcon,
                                        String textTitle, String textContent, int notificationColor, int alarmColor, @Nullable Bitmap largeIcon, long notificationId) {

        if(!isNotificationVisible(context, notificationId)) {

            //Intent for notification's snooze action
            Intent snoozeIntent = new Intent(context, AlarmSnoozeReceiver.class);
            snoozeIntent.setAction(Constants.NOTIFICATION_ACTION_SNOOZE);
            snoozeIntent.putExtra(Constants.NOTIFICATION_EXTRA_ALARM_ID, alarmId);
            snoozeIntent.putExtra(Constants.NOTIFICATION_EXTRA_NOTIFICATION_ID, notificationId);
            PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, (int)coinId, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            //Intent for notification tap
            Intent contentIntent = new Intent(context, CoinDetailActivity.class);
            contentIntent.putExtra(Constants.EXTRA_COIN_DETAIL_COIN_ID, coinId);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(contentIntent);
            PendingIntent contentPendingIntent = stackBuilder.getPendingIntent((int)coinId, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(smallIcon)
                    .setColorized(true)
                    .setColor(notificationColor)
                    .setLights(alarmColor, 1000, 1000)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(textContent))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .addAction(R.drawable.ic_snooze, context.getString(R.string.notification_snooze), snoozePendingIntent)
                    .setContentIntent(contentPendingIntent)
                    .setAutoCancel(true);

            if (largeIcon != null)
                mBuilder.setLargeIcon(largeIcon);

            NotificationManagerCompat.from(context).notify((int)notificationId, mBuilder.build());
        }

    }

    private static boolean isNotificationVisible(Context context, long notificationId) {
//        Intent notificationIntent = new Intent(context, CoinDetailActivity.class);
//        PendingIntent test = PendingIntent.getActivity(context, (int)notificationId, notificationIntent, PendingIntent.FLAG_NO_CREATE);
//        return test != null;

        //usable only api 23+ M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

            try {
                for (StatusBarNotification notification : notificationManager.getActiveNotifications())
                    if (notification.getId() == notificationId)
                        return true;
            } catch (NullPointerException e) {
                return false;
            }
        }

        return false;
    }


    //Needed for Android 8 Oreo
    public static void createNotificationChannel(NotificationManager notificationManager, @NonNull String channelId, String channelName, String channelDescription) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(channelDescription);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
    }

}
