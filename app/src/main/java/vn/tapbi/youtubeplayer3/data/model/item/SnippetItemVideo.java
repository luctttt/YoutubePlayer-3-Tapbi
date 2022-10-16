package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SnippetItemVideo implements Serializable {
    @SerializedName("channelId")
    @Expose
    private String id;

    @SerializedName("publishedAt")
    @Expose
    private String timeVideo;

    @SerializedName("title")
    @Expose
    private String titleVideo;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;

    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;



    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeVideo() {
        return timeVideo;
    }

    public void setTimeVideo(String timeVideo) {
        this.timeVideo = timeVideo;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public boolean isNameEmpty(){
        return !description.isEmpty() && !titleVideo.isEmpty() && !timeVideo.isEmpty() && !id.isEmpty();
    }
}
