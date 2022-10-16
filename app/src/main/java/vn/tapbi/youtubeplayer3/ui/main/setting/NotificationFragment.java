package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.databinding.FragmentGetNotificationBinding;
import vn.tapbi.youtubeplayer3.feature.noti.Notification;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;


public class NotificationFragment extends BaseBindingFragmentNoViewModel<FragmentGetNotificationBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_get_notification;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        boolean check = SharedPreferenceHelper.getStateNotification(requireContext());

        binding.switchNotification.setChecked(check);
        if (check){
            binding.switchNotification.setText(getString(R.string.on));
            getNotification();
        }
        else binding.switchNotification.setText(getString(R.string.off));

        binding.switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.switchNotification.setText(getString(R.string.on));

                SharedPreferenceHelper.saveStateNotification(requireContext(),true);

                getNotification();
            } else {
                binding.switchNotification.setText(getString(R.string.off));
                SharedPreferenceHelper.saveStateNotification(requireContext(),false);
            }
        });
    }

    private void getNotification() {
        // notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7); // At the hour you wanna fire
        calendar.set(Calendar.MINUTE, 0); // Particular minute
        calendar.set(Calendar.SECOND, 20); // particular second

        long startUpTime = calendar.getTimeInMillis();

        // startupTime + 24 hours if alarm is in past
        if (System.currentTimeMillis() > startUpTime) {
            startUpTime = startUpTime + 24 * 60 * 60 * 1000;
        }

        Intent intent = new Intent(getContext(), Notification.class);
        intent.setAction("MY_NOTIFICATION_MESSAGE");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        Timber.d("getNotification: version lon hon 19");
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startUpTime, 24 * 60 * 60 * 1000, pendingIntent);

    }
}