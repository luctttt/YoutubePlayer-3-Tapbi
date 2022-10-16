package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.common.Constant;
import vn.tapbi.youtubeplayer3.databinding.FragmentCreateApiBinding;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;

public class KeyApiFragment extends BaseBindingFragmentNoViewModel<FragmentCreateApiBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_api;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.btnConfirm.setOnClickListener(v->{
            String keyApi = binding.edtApiKey.getText().toString();
            if (!keyApi.equals("")){
                Constant.API = keyApi;
                Intent refresh = new Intent(getActivity(), HomeActivity.class);
                startActivity(refresh);
            }
        });


    }

}