package vn.tapbi.youtubeplayer3.ui.utils;

import android.view.View;

public class EventClick {
    public static void preventTwoClick(final View view, int time){
        if (view.isAttachedToWindow()) {
            view.setEnabled(false);
            view.postDelayed(() -> view.setEnabled(true), time);
        }
    }
}
