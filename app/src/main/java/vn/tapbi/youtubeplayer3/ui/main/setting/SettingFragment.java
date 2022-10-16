package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.common.ModelHomeSetting;
import vn.tapbi.youtubeplayer3.databinding.FragmentSettingBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.SettingAdapter;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragment;


public class SettingFragment extends BaseBindingFragment<FragmentSettingBinding, SettingViewModel> implements SettingAdapter.OnClickItemSetting {

    @Override
    protected Class<SettingViewModel> getViewModel() {
        return SettingViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.recyclerViewSettingFragment.setLayoutManager(new LinearLayoutManager(getContext()));

        SettingAdapter settingAdapter = new SettingAdapter(getFunctionSettings(), this);

        binding.recyclerViewSettingFragment.setAdapter(settingAdapter);

    }

    private List<ModelHomeSetting> getFunctionSettings() {
        List<ModelHomeSetting> dsSettings = new ArrayList<>();

        dsSettings.add(new ModelHomeSetting(R.drawable.icon_languages, getString(R.string.language)));
        dsSettings.add(new ModelHomeSetting(R.drawable.icon_play_background, getString(R.string.play_background)));
        dsSettings.add(new ModelHomeSetting(R.drawable.icon_notification, getString(R.string.Get_notifications_in_morning)));
        dsSettings.add(new ModelHomeSetting(R.drawable.icon_policy, getString(R.string.Policy)));
        dsSettings.add(new ModelHomeSetting(R.drawable.icon_keyapi, getString(R.string.create_api_key)));

        return dsSettings;

    }

    @Override
    public void OnClickFunctionSetting(int position, View view) {

        NavController navController = Navigation.findNavController(view);
        switch (position) {
            case 0:
                navController.navigate(R.id.action_fragmentSetting_to_languageFragment);
                break;
            case 1:
                navController.navigate(R.id.action_fragmentSetting_to_playbackgroundFragment);
                break;
            case 2:
                navController.navigate(R.id.action_fragmentSetting_to_getNotificationFragment);
                break;
            case 3:
                //navController.navigate(R.id.action_fragmentSetting_to_policyFragment);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com")));
                break;
            case 4:
                navController.navigate(R.id.action_nav_setting_to_createApiFragment);
        }

    }
}