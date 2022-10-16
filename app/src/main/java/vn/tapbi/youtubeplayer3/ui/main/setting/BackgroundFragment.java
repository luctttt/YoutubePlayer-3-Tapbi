package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.os.Bundle;
import android.view.View;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.databinding.FragmentPlaybackgroundBinding;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;


public class BackgroundFragment extends BaseBindingFragmentNoViewModel<FragmentPlaybackgroundBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_playbackground;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        boolean check = SharedPreferenceHelper.getStateBackground(requireContext());
        binding.switchPlayBackground.setChecked(check);
        if (check)
            binding.switchPlayBackground.setText(getString(R.string.on));
        else binding.switchPlayBackground.setText(getString(R.string.off));

        binding.switchPlayBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.switchPlayBackground.setText(getString(R.string.on));
                SharedPreferenceHelper.saveStatePlayBackground(requireContext(),true);

            } else {
                binding.switchPlayBackground.setText(getString(R.string.off));
                SharedPreferenceHelper.saveStatePlayBackground(requireContext(),false);
            }
        });
    }


}