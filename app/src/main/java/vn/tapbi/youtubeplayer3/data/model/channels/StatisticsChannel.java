package vn.tapbi.youtubeplayer3.data.model.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatisticsChannel implements Serializable {
    @SerializedName("viewCount")
    @Expose
    private long viewCount;

    @SerializedName("subscriberCount")
    @Expose
    private int subscriberCount;

    @SerializedName("videoCount")
    @Expose
    private int videoCount;

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(int subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }
}
