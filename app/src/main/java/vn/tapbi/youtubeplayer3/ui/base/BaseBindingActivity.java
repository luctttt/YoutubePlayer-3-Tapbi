package vn.tapbi.youtubeplayer3.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseBindingActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity {

    public B binding;
    public VM viewModel;

    public abstract int getLayoutId();
    public abstract Class<VM> getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutId());
        viewModel = new ViewModelProvider(this).get(getViewModel());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
