package vn.tapbi.youtubeplayer3.feature.timber;

import android.util.Log;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, @Nullable String tag,
                       @Nullable String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }
    }
}
