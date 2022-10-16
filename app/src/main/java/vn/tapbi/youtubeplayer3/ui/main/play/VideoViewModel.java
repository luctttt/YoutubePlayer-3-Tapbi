package vn.tapbi.youtubeplayer3.ui.main.play;

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
import vn.tapbi.youtubeplayer3.data.model.channels.ItemChannels;
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.comment.ItemComment;
import vn.tapbi.youtubeplayer3.data.model.comment.ListComment;
import vn.tapbi.youtubeplayer3.data.model.item.ItemRelated;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.data.model.item.VideoRelated;
import vn.tapbi.youtubeplayer3.data.model.statistics.ItemStatistics;
import vn.tapbi.youtubeplayer3.data.model.statistics.ViewDetailStatistics;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;
import vn.tapbi.youtubeplayer3.data.repository.VideoRepository;
import vn.tapbi.youtubeplayer3.di.AppModule;
import vn.tapbi.youtubeplayer3.ui.base.BaseViewModel;
@HiltViewModel
public class VideoViewModel extends BaseViewModel { // ctr + alt + j : boi den 1 dong

    ApiInterface apiInterface;
    VideoRepository videoRepository ;

    MutableLiveData<List<ItemVideo>> videoRelatedLiveData = new MutableLiveData<>();
    MutableLiveData<List<ItemComment>> commentLiveData = new MutableLiveData<>();

    @Inject
    public VideoViewModel(ApiInterface apiInterface, VideoRepository videoRepository) {
        this.apiInterface = apiInterface;
        this.videoRepository = videoRepository;
    }

    String id = "";

    public void setId(String id) {
        this.id = id;
    }

    public void getListVideoRelated() {         // call videoRelated

        videoRepository.getVideoRelatedRepository("snippet", id, "video", Constant.API, 20)
                .subscribe(new SingleObserver<VideoRelated>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull VideoRelated videoRelated) {
                        setValueItem(videoRelated.getItemRelated());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("get video related false : %s", e.getMessage());
                    }
                });

    }

    private int position;

    public void setValueItem(List<ItemRelated> listVideos) {

        List<ItemChannels> mListChannel = new ArrayList<>();
        int n = listVideos.size();

        for (int i = 0; i < n; i++) {
            position = i;
            if (listVideos.get(i).getSnippetItemVideo() != null) {        // item related have not channelId
                String channelId = listVideos.get(i).getSnippetItemVideo().getId();

                videoRepository.getChannels("statistics,snippet", channelId, Constant.API)
                        .subscribe(new SingleObserver<ListChannel>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

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
            item.setSnippetItemVideo(itemList.get(i).getSnippetItemVideo());
            item.setId(itemList.get(i).getId().getVideoId());
            item.setItemChannels(listChannels.get(i));

            listItem.add(item);
        }
        callStatistics(listItem);
    }

    private int positionStatistics;
    public void callStatistics(List<ItemVideo> listItemVideos) {        // call statistics

        List<ItemStatistics> listStatistics = new ArrayList<>();
        apiInterface = AppModule.getApiClient().create(ApiInterface.class);

        List<ItemVideo> listVideoItems = new ArrayList<>();

        int n = listItemVideos.size();
        //Log.d(TAG, "callStatistics: size item video = "+n);
        for (int i = 0; i < n; i++) {
            positionStatistics = i ;
            String videoId = listItemVideos.get(i).getId();
            ItemVideo itemVideo = listItemVideos.get(i);

            videoRepository.getStatisticsRepository("statistics,contentDetails", videoId, Constant.API)
                    .subscribe(new SingleObserver<ViewDetailStatistics>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull ViewDetailStatistics viewDetailStatistics) {
                            listStatistics.add(viewDetailStatistics.getItemStatistics().get(0));     // size
                            listVideoItems.add(itemVideo);

                            if (positionStatistics == listItemVideos.size() - 1) {
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

    public void getCommentVideo() {

        videoRepository.getCommentRepository(Constant.API, "plainText", "snippet", id, 20)
                .subscribe(new SingleObserver<ListComment>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ListComment listComment) {
                        commentLiveData.postValue(listComment.getListItem());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("get comment false : .............%s", e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<ItemVideo>> getVideoRelated() {      // return list item
        return videoRelatedLiveData;
    }

    public MutableLiveData<List<ItemComment>> getCommentLiveData() {
        return commentLiveData;
    }


}
