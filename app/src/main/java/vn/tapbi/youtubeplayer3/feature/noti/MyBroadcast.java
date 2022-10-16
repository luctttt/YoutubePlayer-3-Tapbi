package vn.tapbi.youtubeplayer3.feature.noti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("action_name", intent.getAction()));
    }
}
