package uk.ac.tees.b1241570.travelexplorer.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import studio.knowhere.travelappg.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    NotificationManagerCompat mNotificationManagerCompat;
    final String CHANNEL_ID = "Important_mail_channel";

    @Override
    public void onReceive(Context context, Intent intent) {


        // Toast.makeText(context, "Alarm Ringing", Toast.LENGTH_LONG).show();
        Log.v("TAG", "ALARM TRIGGERS");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Channel name
            CharSequence name = "Important_mail_channel";

            //Channel description
            String description = "This channel will show notification only to important people";

            //The importance level you assign to a channel applies to all notifications that you post to it.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            //Create the NotificationChannel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            //Set channel description
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        mNotificationManagerCompat = NotificationManagerCompat.from(context);


        mNotificationManagerCompat.cancelAll();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_audiotrack_dark);

        //open the url when user taps the notification
        Intent intentn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.c1ctech.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Travel expolorer")
                .setContentText("You have an new notification ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        // notificationId is a unique int for each notification that you must define
        mNotificationManagerCompat.notify(1, notification);

    }

}
