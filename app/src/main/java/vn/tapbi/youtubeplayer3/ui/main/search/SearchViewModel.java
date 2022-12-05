package vn.tapbi.youtubeplayer3.ui.main.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import vn.tapbi.youtubeplayer3.common.Constant;
import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;
import vn.tapbi.youtubeplayer3.data.model.channels.ItemChannels;
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.item.ItemRelated;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.data.model.item.VideoRelated;
import vn.tapbi.youtubeplayer3.data.model.statistics.ItemStatistics;
import vn.tapbi.youtubeplayer3.data.model.statistics.ViewDetailStatistics;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;
import vn.tapbi.youtubeplayer3.data.repository.RecentRepository;
import vn.tapbi.youtubeplayer3.data.repository.VideoRepository;
import vn.tapbi.youtubeplayer3.di.AppModule;
import vn.tapbi.youtubeplayer3.ui.base.BaseViewModel;

@HiltViewModel
public class SearchViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private VideoRepository videoRepository;
    private RecentRepository recentRepository;

    private MutableLiveData<List<ItemVideo>> videoRelatedLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkStateData = new MutableLiveData<>();

    private MutableLiveData<List<HistoryModel>> getHistory = new MutableLiveData<>();
    private MutableLiveData<List<RecentModel>> searchRecent = new MutableLiveData<>();
    private MutableLiveData<List<HistoryModel>> searchHistory = new MutableLiveData<>();


    private boolean isCheckData = false;

    public MutableLiveData<Boolean> isLoadData() {
        return checkStateData;
    }

    @Inject
    public SearchViewModel(ApiInterface apiInterface, VideoRepository videoRepository , RecentRepository recentRepository) {
        this.apiInterface = apiInterface;
        this.videoRepository = videoRepository;
        this.recentRepository = recentRepository;
    }

    /* set/get data in room */
    public void insertRecentSearch(RecentModel recentModel){
        recentRepository.insertRecentSearch(recentModel);
    }

    public void insertHistorySearch(HistoryModel historyModel){
        recentRepository.insertHistorySearch(historyModel);
    }

    public void getHistory(){
        getHistory.postValue(recentRepository.getHistory());
    }

    public void searchRecent(String name){
        searchRecent.postValue(recentRepository.searchRecent(name));
    }

    public List<HistoryModel> searchHistory(String name){
        return recentRepository.searchHistory(name);
    }

    public MutableLiveData<List<HistoryModel>> getGetHistory() {
        return getHistory;
    }

    public MutableLiveData<List<RecentModel>> getSearchRecent() {
        return searchRecent;
    }

    public MutableLiveData<List<HistoryModel>> getSearchHistory() {
        return searchHistory;
    }


    /* get data viewModel */
    public void getVideoFormSearchView(String keyword) {
        videoRepository.getSearchVideoRepository("snippet", 25, "rating", keyword, "video", Constant.API)
                .subscribe(new SingleObserver<VideoRelated>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull VideoRelated videoRelated) {
                        checkStateData.postValue(true);
                        isCheckData = true;
                        setValueItem(videoRelated.getItemRelated());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isCheckData = false;
                        checkStateData.postValue(false);
                    }
                });
    }

    private int position;

    public void setValueItem(List<ItemRelated> listVideos) {

        List<ItemChannels> mListChannel = new ArrayList<>();
        int n = listVideos.size();

        for (int i = 0; i < n; i++) {
            position = i;
            if (listVideos.get(i).getSnippetItemVideo() != null) {

                String channelId = listVideos.get(i).getSnippetItemVideo().getId();

                videoRepository.getChannels("statistics,snippet", channelId, Constant.API)
                        .subscribe(new SingleObserver<ListChannel>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull ListChannel listChannel) {
                                mListChannel.add(listChannel.getChannelsList().get(0));
                                if (position == listVideos.size() - 1) {
                                    setChannelItem(mListChannel, listVideos);

                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Timber.d("search false : %s", e.getMessage());
                            }
                        });

            } else {
                Timber.d("Channel null ");
            }
        }
    }

    public void setChannelItem(List<ItemChannels> listChannels, List<ItemRelated> itemList) {
        List<ItemVideo> listItem = new ArrayList<>();
        for (int i = 0; i < listChannels.size(); i++) {

            ItemVideo item = new ItemVideo();

            item.setSnippetItemVideo(itemList.get(i).getSnippetItemVideo());     // id
            item.setId(itemList.get(i).getId().getVideoId());        // spinnet

            item.setItemChannels(listChannels.get(i));

            listItem.add(item);

        }

        callStatistics(listItem);
    }


    private int positionStatistics;

    public void callStatistics(List<ItemVideo> listVideos) {        // call statistics
        List<ItemStatistics> listStatistics = new ArrayList<>();
        apiInterface = AppModule.getApiClient().create(ApiInterface.class);

        List<ItemVideo> listVideoItems = new ArrayList<>();

        for (int i = 0; i < listVideos.size(); i++) {
            positionStatistics = i;

            String videoId = listVideos.get(i).getId();
            ItemVideo itemVideo = listVideos.get(i);

            videoRepository.getStatisticsRepository("statistics,contentDetails", videoId, Constant.API)
                    .subscribe(new SingleObserver<ViewDetailStatistics>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(@NonNull ViewDetailStatistics viewDetailStatistics) {
                            listStatistics.add(viewDetailStatistics.getItemStatistics().get(0));     // size
                            listVideoItems.add(itemVideo);

                            if (positionStatistics == listVideos.size() - 1) {
                                setStatistics(listStatistics, listVideoItems);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });

        }

    }

    private void setStatistics(List<ItemStatistics> listStatistics, List<ItemVideo> listVideos) {
        for (int i = 0; i < listVideos.size(); i++) {
            listVideos.get(i).setStatistics(listStatistics.get(i).getStatistics());
            listVideos.get(i).setContentDetails(listStatistics.get(i).getContentDetails());
        }
        videoRelatedLiveData.postValue(listVideos);
    }

    public MutableLiveData<List<ItemVideo>> getVideoRelated() {      // return list item
        return videoRelatedLiveData;
    }

}
