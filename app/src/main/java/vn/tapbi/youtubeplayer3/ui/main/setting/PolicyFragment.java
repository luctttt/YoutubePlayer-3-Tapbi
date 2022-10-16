package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.databinding.FragmentPolicyBinding;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;


public class PolicyFragment extends BaseBindingFragmentNoViewModel<FragmentPolicyBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_policy;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.textIntent.setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com")));
        });
    }
}