package vn.tapbi.youtubeplayer3.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import vn.tapbi.youtubeplayer3.common.Constant;
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.data.model.item.VideoDetail;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;
import vn.tapbi.youtubeplayer3.data.repository.VideoRepository;
import vn.tapbi.youtubeplayer3.ui.base.BaseViewModel;

@HiltViewModel
public class HomeActivityViewModel extends BaseViewModel {

    /*trending*/
    private ApiInterface apiInterface;
    private VideoRepository videoRepository;
    private MutableLiveData<List<ItemVideo>> liveDataTrending = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkStateDataTrending = new MutableLiveData<>();

    /*home*/
    private MutableLiveData<List<ItemVideo>> liveDataVideoHome = new MutableLiveData<>();
    //    List<String> listApi = Constant.setListApi();
    private int index = 0;
    private MutableLiveData<Boolean> checkStateDataHome = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLoadDataHome() {
        return checkStateDataHome;
    }

    public MutableLiveData<Boolean> isLoadDataTrending() {
        return checkStateDataTrending;
    }

    @Inject
    public HomeActivityViewModel(ApiInterface apiInterface, VideoRepository videoRepository) {
        this.apiInterface = apiInterface;
        this.videoRepository = videoRepository;
    }

    public void getAllVideoTrending() {
        videoRepository.getVideoList("snippet,contentDetails,statistics", "mostPopular", 30, "VN", Constant.API)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new SingleObserver<VideoDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull VideoDetail videoDetail) {
                        checkStateDataTrending.postValue(true);
                        getAllChannelsTrending(videoDetail.getItems());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        checkStateDataTrending.postValue(false);
                        Timber.d("onError Trending call : %s", e.getMessage());
                    }
                });
    }

    public void getAllChannelsTrending(List<ItemVideo> itemList) {        // load all on channels
        List<ItemVideo> list = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            ItemVideo itemVideo = itemList.get(i);
            String channelId = itemList.get(i).getSnippetItemVideo().getId();

            videoRepository.getChannels("statistics,snippet", channelId, Constant.API)
                    .subscribe(new SingleObserver<ListChannel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(@NonNull ListChannel listChannel) {
                            itemVideo.setItemChannels(listChannel.getChannelsList().get(0));
                            list.add(itemVideo);
                            liveDataTrending.postValue(list);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Timber.d("onFailure: getChannels......false : %s", e.getMessage());
                        }
                    });
        }

    }

    public void getAllVideoHome() {
        videoRepository.getAllVideoRepository("snippet,contentDetails,statistics", "mostPopular", 30, Constant.API)
                .subscribe(new SingleObserver<VideoDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull VideoDetail videoDetail) {
//                        Constant.API = listApi.get(index);
                        Timber.d("API : %s", Constant.API);
                        checkStateDataHome.postValue(true);
                        getAllChannelsHome(videoDetail.getItems());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("onFailure getDataFromRetrofit : %s", e.getMessage());
//                        index = (index + 1) % listApi.size();
                        checkStateDataHome.postValue(false);
                    }
                });
    }

    public void getAllChannelsHome(List<ItemVideo> itemList) {        // load all on channels
        List<ItemVideo> list = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            ItemVideo itemVideo = itemList.get(i);
            String channelId = itemList.get(i).getSnippetItemVideo().getId();

            videoRepository.getChannels("statistics,snippet", channelId, Constant.API)
                    .subscribe(new SingleObserver<ListChannel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(@NonNull ListChannel listChannel) {
                            itemVideo.setItemChannels(listChannel.getChannelsList().get(0));
                            list.add(itemVideo);
                            liveDataVideoHome.postValue(list);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }
                    });
        }

    }

    public List<ItemVideo> getDataHome() {
        return liveDataVideoHome.getValue();
    }

    public List<ItemVideo> getDataTrending() {
        return liveDataTrending.getValue();
    }

    public MutableLiveData<List<ItemVideo>> getListItemsTrending() {
        return liveDataTrending;
    }

    public MutableLiveData<List<ItemVideo>> getListItemsHome() {
        return liveDataVideoHome;
    }

}
