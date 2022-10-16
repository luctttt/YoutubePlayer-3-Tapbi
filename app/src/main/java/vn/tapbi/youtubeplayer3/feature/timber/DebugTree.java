package vn.tapbi.youtubeplayer3.feature.timber;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class DebugTree extends Timber.DebugTree {
    @Override
    protected @Nullable
    String createStackElementTag(@Nullable StackTraceElement element) {
        return String.format("C:%s:%s",
                super.createStackElementTag(element),
                element.getLineNumber());
    }
}
