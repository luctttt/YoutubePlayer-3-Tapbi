package vn.tapbi.youtubeplayer3.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.comment.ListComment;
import vn.tapbi.youtubeplayer3.data.model.item.VideoDetail;
import vn.tapbi.youtubeplayer3.data.model.item.VideoRelated;
import vn.tapbi.youtubeplayer3.data.model.statistics.ViewDetailStatistics;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;

public class VideoRepository {
    ApiInterface apiInterface;

    @Inject
    public VideoRepository(ApiInterface apiClient) {
        this.apiInterface = apiClient;
    }

    public Single<VideoDetail> getVideoList(String part, String chart, int maxResults, String regionCode, String apiKey) {
        return apiInterface.getAllVideosTrending(part, chart, maxResults, regionCode, apiKey).subscribeOn(Schedulers.io());
    }

    public Single<VideoDetail> getAllVideoRepository(String part, String chart, int maxResults, String apiKey) {
        return apiInterface.getAllVideo(part, chart, maxResults, apiKey).subscribeOn(Schedulers.io());
    }

    public Single<ListChannel> getChannels(String part, String channelID, String apiKey) {
        return apiInterface.getChannels(part, channelID, apiKey).subscribeOn(Schedulers.io());
    }

    public Single<ViewDetailStatistics> getStatisticsRepository(String part, String channelID, String apiKey) {
        return apiInterface.getStatistics(part, channelID, apiKey).subscribeOn(Schedulers.io());
    }

    public Single<VideoRelated> getSearchVideoRepository(String part, int maxResults, String order, String q, String type, String key) {
        return apiInterface.getSearchVideo(part, maxResults, order, q, type, key).subscribeOn(Schedulers.io());
    }

    public Single<ListComment> getCommentRepository(String key, String textFormat, String part, String videoId, int maxResults) {
        return apiInterface.getComments(key, textFormat, part, videoId, maxResults).subscribeOn(Schedulers.io());
    }

    public Single<VideoRelated> getVideoRelatedRepository(String part, String relatedToVideoId, String type, String key, int maxResults) {
        return apiInterface.getVideoRelated( part,  relatedToVideoId,  type,  key,  maxResults).subscribeOn(Schedulers.io());
    }

}
