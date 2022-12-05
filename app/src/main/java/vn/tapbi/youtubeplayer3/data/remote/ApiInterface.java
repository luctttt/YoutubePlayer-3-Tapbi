package vn.tapbi.youtubeplayer3.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;
import vn.tapbi.youtubeplayer3.data.model.comment.ListComment;
import vn.tapbi.youtubeplayer3.data.model.item.VideoDetail;
import vn.tapbi.youtubeplayer3.data.model.item.VideoRelated;
import vn.tapbi.youtubeplayer3.data.model.statistics.ViewDetailStatistics;

public interface ApiInterface {


    @GET("videos")
    Single<VideoDetail> getAllVideo(@Query("part") String part,
                                    @Query("chart") String chart,
                                    @Query("maxResults") int maxResults,
                                    @Query("key") String key);

    @GET("videos")
    Single<ViewDetailStatistics> getStatistics(@Query("part") String part,
                                               @Query("id") String id,
                                               @Query("key") String key);

    @GET("commentThreads")
    Single<ListComment> getComments(@Query("key") String key,
                                    @Query("textFormat") String textFormat,
                                    @Query("part") String part,
                                    @Query("videoId") String videoId,
                                    @Query("maxResults") int maxResults);

    @GET("search")
    Single<VideoRelated> getVideoRelated(@Query("part") String part,
                                         @Query("relatedToVideoId") String relatedToVideoId,
                                         @Query("type") String type,
                                         @Query("key") String key,
                                         @Query("maxResults") int maxResults);

    @GET("channels")
    Single<ListChannel> getChannels(@Query("part") String part,
                                    @Query("id") String id,
                                    @Query("key") String key);

    @GET("search")
    Single<VideoRelated> getSearchVideo(@Query("part") String part,
                                        @Query("maxResults") int maxResults,
                                        @Query("order") String order,
                                        @Query("q") String q,
                                        @Query("type") String type,
                                        @Query("key") String key);


    @GET("videos")
    Single<VideoDetail> getAllVideosTrending(@Query("part") String part,
                                             @Query("chart") String chart,
                                             @Query("maxResults") int maxResults,
                                             @Query("regionCode") String regionCode,
                                             @Query("key") String key);

}
