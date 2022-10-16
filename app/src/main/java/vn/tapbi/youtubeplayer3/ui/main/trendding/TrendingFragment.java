package vn.tapbi.youtubeplayer3.ui.main.trendding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.FragmentTrendingBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.HomeAdapter;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragment;

@AndroidEntryPoint
public class TrendingFragment extends BaseBindingFragment<FragmentTrendingBinding, TrendingViewModel> implements HomeAdapter.OnclickItemVideo {

    private HomeAdapter homeFragmentAdapter;
    private CallBackItemVideo callBackItemVideo;
    public void setCallBackItemVideo(CallBackItemVideo callBackItemVideo) {
        Timber.d("setCallBackItemVideo: ..........");
        this.callBackItemVideo = callBackItemVideo;
    }


    @Override
    protected Class<TrendingViewModel> getViewModel() {
        return TrendingViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trending;
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
        if (getContext() != null && mainViewModel.getDataTrending() != null && mainViewModel.getDataTrending().size() != 0) {
            binding.recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getContext()));
                homeFragmentAdapter = new HomeAdapter(mainViewModel.getDataTrending(), getContext());
                homeFragmentAdapter.setInterface(this);
                binding.recyclerViewTrending.setAdapter(homeFragmentAdapter);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewTrending.setVisibility(View.VISIBLE);
                homeFragmentAdapter.notifyDataSetChanged();
            Timber.d("lucttt : video don't save , size item : %s",mainViewModel.getDataTrending().size());
        } else {
            Timber.d("lucttt : video saved");
            getVideo();
        }
    }

    private void getVideo() {
        mainViewModel.getAllVideoTrending();

        mainViewModel.getListItemsTrending().observe(requireActivity(), items -> {
            binding.recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getContext()));
            homeFragmentAdapter = new HomeAdapter(items, getContext());
            homeFragmentAdapter.setInterface(this);

            binding.recyclerViewTrending.setAdapter(homeFragmentAdapter);
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerViewTrending.setVisibility(View.VISIBLE);
        });

        mainViewModel.isLoadDataTrending().observe(requireActivity(), aBoolean -> {
            if (!aBoolean) {
                Timber.d("Load false !!");
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.txtNoDataTrending.setVisibility(View.VISIBLE);
                }, 3000);
            } else {
                binding.txtNoDataTrending.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void click(ItemVideo item, View view) {
        callBackItemVideo.call(item);
    }

    public interface CallBackItemVideo {
        void call(ItemVideo itemVideo);
    }

    long currentVisiblePosition = 0;

    @Override
    public void onStop() {
        super.onStop();
        if (binding.recyclerViewTrending.getLayoutManager() != null) {
            currentVisiblePosition = ((LinearLayoutManager) Objects.requireNonNull(binding.recyclerViewTrending.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
            SharedPreferenceHelper.savePosition(requireContext(), (int) currentVisiblePosition, SharedPreferenceHelper.POSITION_TRENDING);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        int position = SharedPreferenceHelper.getPosition(requireContext(), SharedPreferenceHelper.POSITION_TRENDING);
        if (position != 0 && (binding.recyclerViewTrending.getLayoutManager()) != null) {
            (binding.recyclerViewTrending.getLayoutManager()).scrollToPosition(position);
        }
        currentVisiblePosition = 0;
    }

}