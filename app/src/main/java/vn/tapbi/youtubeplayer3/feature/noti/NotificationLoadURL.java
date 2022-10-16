package vn.tapbi.youtubeplayer3.feature.noti;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.atomic.AtomicInteger;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;

public class NotificationLoadURL {

    static int id_notification = 0;
    @SuppressLint("StaticFieldLeak")
    static NotificationManagerCompat notificationManager;
    public static final String ACTION_PLAY = "action_play";
    static boolean isPlay = false;

    public static void createNotification(String title, String content,
                                          Context context, String url , int playButton) {

        Timber.d("luctttt : Create notification !!!");
        if (title == null || content == null || url == null) {
            title = "title";
            content = "content";
            url = "https://i.ytimg.com/vi/PhoxHtER6jU/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLDgf3iUKG7MON13hW2-mWqHSiHPIg";
        }

        id_notification = NotificationID.getID();
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Intent intentPlay = new Intent(context, HomeActivity.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, "channel")
                        .setSmallIcon(R.drawable.icon_app_mini)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setOnlyAlertOnce(true)//show notification for only first time
                        .setShowWhen(false)
                        .addAction(playButton, "Play", pendingIntentPlay)       // action
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_LOW);

        notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel",
                    "channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
        }

        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //largeIcon
                        notificationBuilder.setLargeIcon(resource);
                        Notification notification = notificationBuilder.build();
                        notificationManager.notify(id_notification, notification);
                        Timber.d("luctttt : create notification successfully !!!");
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Timber.d("luctttt : load image from url false !!");
                        Notification notification = notificationBuilder.build();
                        notificationManager.notify(id_notification, notification);
                    }
                });
    }

    public static void removeNotification() {
        if (notificationManager != null) {
            notificationManager.cancel(id_notification);
        }
    }


    static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(100);

        public static int getID() {
            return c.incrementAndGet();
        }
    }

}
