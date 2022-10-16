package vn.tapbi.youtubeplayer3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDexApplication;

import java.util.Locale;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;
import vn.tapbi.youtubeplayer3.common.Constant;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.feature.timber.DebugTree;

@HiltAndroidApp
public class App extends MultiDexApplication {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(throwable -> {
        }); // nothing or some logging
        mContext = this;
        createNotificationChannel();
        initLog();
        Timber.i(" APP : lucttt language : %s", Locale.getDefault().getLanguage());
    }

    public static Context getContext() {
        return mContext;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.Policy);
            String description = getString(R.string.Get_notifications_in_morning);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constant.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        String languageDefault = SharedPreferenceHelper.getLanguage(base);
        Timber.d("lucttt languageDefault : %s", languageDefault);
        if (languageDefault.equals("")) {
            languageDefault = Constant.LANGUAGE_ENGLISH;
            Timber.d("lucttt : language null");
        }
        super.attachBaseContext(SharedPreferenceHelper.onAttach(base, languageDefault));
    }

    private void initLog() {
        Timber.plant(new DebugTree());
    }
}
