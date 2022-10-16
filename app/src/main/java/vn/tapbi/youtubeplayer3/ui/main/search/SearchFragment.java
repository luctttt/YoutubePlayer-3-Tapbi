package vn.tapbi.youtubeplayer3.ui.main.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.FragmentSearchBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.HistoryAdapter;
import vn.tapbi.youtubeplayer3.ui.adapter.RecentAdapter;
import vn.tapbi.youtubeplayer3.ui.adapter.SearchAdapter;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragment;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;
import vn.tapbi.youtubeplayer3.ui.splash.SplashActivity;


public class SearchFragment extends BaseBindingFragment<FragmentSearchBinding, SearchViewModel> implements RecentAdapter.clickItemVideoSearch, HistoryAdapter.OnclickItemVideoHistory, SearchAdapter.clickItemVideoSearch {

    SearchAdapter searchAdapter = new SearchAdapter();
    List<RecentModel> recentModelList;
    List<HistoryModel> list;

    private CallBackItemVideoSearch callBackItemVideoSearch;
    boolean isLoadVideo = false;

    public void setInter(CallBackItemVideoSearch callBackItemVideoSearch) {
        this.callBackItemVideoSearch = callBackItemVideoSearch;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof CallBackItemVideoSearch) {
            callBackItemVideoSearch = (CallBackItemVideoSearch) getActivity();
        }
    }

    @Override
    protected Class<SearchViewModel> getViewModel() {
        return SearchViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        getHistory();

        binding.searchVideo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("onQueryTextSubmit: ......%s", query);

                binding.progressBar.setVisibility(View.VISIBLE);

                if (recentModelList.size() == 0) {
                    viewModel.insertRecentSearch(new RecentModel(query));
                }

                list = viewModel.searchHistory(query);
                if (list.size() == 0) {
                    viewModel.insertHistorySearch(new HistoryModel(query));
                }
                binding.txtRecent.setVisibility(View.GONE);
                binding.recyclerViewRecentSearch.setVisibility(View.GONE);
                binding.recyclerViewRecentSearchHistory.setVisibility(View.INVISIBLE);
                binding.recyclerViewSearch.setVisibility(View.VISIBLE);

                getVideo(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Timber.d("onQueryTextChange !!");
                binding.recyclerViewSearch.setVisibility(View.GONE);
                binding.recyclerViewRecentSearchHistory.setVisibility(View.INVISIBLE);
                binding.recyclerViewRecentSearch.setVisibility(View.VISIBLE);
                binding.txtConnectAvailable.setVisibility(View.GONE);
                getRecent(newText);
                return false;
            }
        });

    }

    private void getHistory() {

        binding.recyclerViewRecentSearchHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.getHistory();
        viewModel.getGetHistory().observe(requireActivity(), historyModels -> {
            list = historyModels;
            HistoryAdapter historyAdapter = new HistoryAdapter(historyModels, getContext());
            historyAdapter.setInterface(this);
            binding.recyclerViewRecentSearchHistory.setAdapter(historyAdapter);
        });
    }

    private void getRecent(String keyword) {

        binding.searchVideo.requestFocus();

        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        binding.recyclerViewRecentSearch.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.searchRecent(keyword);
        viewModel.getSearchRecent().observe(requireActivity(), recentModels -> {
            recentModelList = recentModels;
            RecentAdapter searchAdapterRecent = new RecentAdapter(recentModels, this);
            binding.recyclerViewRecentSearch.setAdapter(searchAdapterRecent);
        });

    }

    private void getVideo(String keyword) {

        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getVideoFormSearchView(keyword);

        viewModel.getVideoRelated().observe(requireActivity(), itemVideos -> {
            isLoadVideo = true;
            binding.progressBar.setVisibility(View.INVISIBLE);

            searchAdapter.setData(itemVideos, getContext(), this);
            binding.recyclerViewSearch.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
            Timber.d("Load video successfully !!");

        });

        viewModel.isLoadData().observe(requireActivity(), aBoolean -> {
            if (!aBoolean) {
                Timber.d("Load false !!");
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.recyclerViewSearch.setVisibility(View.INVISIBLE);
                    binding.txtConnectAvailable.setVisibility(View.VISIBLE);
                }, 3000);
            }
        });

    }

    @Override
    public void clickItemVideo(RecentModel itemVideo) {
        binding.searchVideo.setQuery(itemVideo.getKeyword(), false);

        View view = requireActivity().getCurrentFocus();
        InputMethodManager in = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void clickHistory(HistoryModel item) {
        binding.searchVideo.setQuery(item.getKeyword(), false);

        View view = requireActivity().getCurrentFocus();
        InputMethodManager in = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.showSoftInput(view, 0);
    }

    @Override
    public void clickSearch(ItemVideo itemVideo, View view) {
        if (callBackItemVideoSearch != null) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                callBackItemVideoSearch.call(itemVideo);
            }, 500);
        }
    }

    public interface CallBackItemVideoSearch {
        void call(ItemVideo itemVideo);
    }
}