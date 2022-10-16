package vn.tapbi.youtubeplayer3.feature.noti;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Date;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;

public class Notification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeating123 = new Intent(context, HomeActivity.class);
//
//        repeating123.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating123, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID")
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Title")
//                .setContentText("Text")
//                .setAutoCancel(true);
//
//        if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
//            notificationManager.notify(getID(), mBuilder.build());
//            Timber.d("onReceive: Notify true");
//        } else {
//            Timber.d("onReceive: notify false ");
//        }

    }

    private int getID() {
        return (int) new Date().getTime();
    }
}
