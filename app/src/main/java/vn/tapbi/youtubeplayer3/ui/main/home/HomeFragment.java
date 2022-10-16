package vn.tapbi.youtubeplayer3.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.FragmentHomeBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.HomeAdapter;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragment;


public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding, HomeViewModel> implements HomeAdapter.OnclickItemVideo {

    private HomeAdapter homeFragmentAdapter;
    private CallBackItemVideo callBackItemVideo;
    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof CallBackItemVideo) {
            callBackItemVideo = (CallBackItemVideo) getActivity();
        }
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        checkStateFragment();
    }

    private void checkStateFragment() {
        mainViewModel.getAllVideoHome();
        if (getContext() != null && mainViewModel.getDataHome() != null) {
            binding.recyclerViewHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));
                homeFragmentAdapter = new HomeAdapter(mainViewModel.getDataHome(), getContext());
                homeFragmentAdapter.setInterface(this);

                binding.recyclerViewHomeFragment.setAdapter(homeFragmentAdapter);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewHomeFragment.setVisibility(View.VISIBLE);
                homeFragmentAdapter.notifyDataSetChanged();
            Timber.d("lucttt : video don't save");
        } else {
            getVideo();
        }
    }

    private void getVideo() {
        mainViewModel.getAllVideoHome();

        mainViewModel.getListItemsHome().observe(requireActivity(), items -> {
            binding.recyclerViewHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));
            homeFragmentAdapter = new HomeAdapter(items, getContext());
            homeFragmentAdapter.setInterface(this);

            binding.recyclerViewHomeFragment.setAdapter(homeFragmentAdapter);
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerViewHomeFragment.setVisibility(View.VISIBLE);
            homeFragmentAdapter.notifyDataSetChanged();
        });

        mainViewModel.isLoadDataHome().observe(requireActivity(), aBoolean -> {
            if (!aBoolean) {
                Timber.d("Load false !!");
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.txtNoDataHome.setVisibility(View.VISIBLE);
                }, 3000);
            } else {
                binding.txtNoDataHome.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void click(ItemVideo item, View view) {      // call back
        callBackItemVideo.call(item);
    }

    long currentVisiblePosition = 0;

    @Override
    public void onStop() {
        super.onStop();
        if (binding.recyclerViewHomeFragment.getLayoutManager() != null) {
            currentVisiblePosition = ((LinearLayoutManager) Objects.requireNonNull(binding.recyclerViewHomeFragment.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
            SharedPreferenceHelper.savePosition(requireContext(), (int) currentVisiblePosition, SharedPreferenceHelper.POSITION_HOME);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        int position = SharedPreferenceHelper.getPosition(requireContext(), SharedPreferenceHelper.POSITION_HOME);
        if (position != 0 && (binding.recyclerViewHomeFragment.getLayoutManager()) != null) {
            (binding.recyclerViewHomeFragment.getLayoutManager()).scrollToPosition(position);
        }
        currentVisiblePosition = 0;
    }

    public interface CallBackItemVideo {
        void call(ItemVideo itemVideo);
    }
}