package vn.tapbi.youtubeplayer3.ui.main.home;

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
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.data.model.item.VideoDetail;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;
import vn.tapbi.youtubeplayer3.data.repository.VideoRepository;
import vn.tapbi.youtubeplayer3.ui.base.BaseViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    ApiInterface apiInterface;
    VideoRepository videoRepository;
    MutableLiveData<List<ItemVideo>> liveDataVideo = new MutableLiveData<>();

    List<String> listApi = Constant.setListApi();
    int index = 0;

    MutableLiveData<Boolean> checkStateData = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLoadData() {
        return checkStateData;
    }

    @Inject
    public HomeViewModel(ApiInterface apiInterface, VideoRepository videoRepository) {
        this.apiInterface = apiInterface;
        this.videoRepository = videoRepository;
    }

    public void getAllVideo() {
        videoRepository.getAllVideoRepository("snippet,contentDetails,statistics", "mostPopular", 30, Constant.API)
                .subscribe(new SingleObserver<VideoDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull VideoDetail videoDetail) {
//                        Constant.API = listApi.get(index);
                        Timber.d("API : %s", Constant.API);
                        checkStateData.postValue(true);
                        getAllChannels(videoDetail.getItems());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("onFailure getDataFromRetrofit : %s", e.getMessage());
                        index = (index + 1) % listApi.size();
                        checkStateData.postValue(false);
                        getAllVideo();
                    }
                });
    }

    public void getAllChannels(List<ItemVideo> itemList) {        // load all on channels
        List<ItemVideo> list = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            ItemVideo itemVideo = itemList.get(i);
            String channelId = itemList.get(i).getSnippetItemVideo().getId();

            videoRepository.getChannels("statistics,snippet", channelId, Constant.API)
                    .subscribe(new SingleObserver<ListChannel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            //Log.e("hoangld", "onSubscribe: "+ Thread.currentThread());
                        }

                        @Override
                        public void onSuccess(@NonNull ListChannel listChannel) {
                            itemVideo.setItemChannels(listChannel.getChannelsList().get(0));
                            list.add(itemVideo);
                            liveDataVideo.postValue(list);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }
                    });
        }

    }

    public MutableLiveData<List<ItemVideo>> getListItems() {
        return liveDataVideo;
    }

}
