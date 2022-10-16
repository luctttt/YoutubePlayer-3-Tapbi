package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.tapbi.youtubeplayer3.data.model.channels.ItemChannels;

public class ItemVideo implements Serializable {

    public ItemVideo() {
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("snippet")
    @Expose
    private SnippetItemVideo snippetItemVideo;

    @SerializedName("statistics")       // null
    @Expose
    private Statistics statistics;

    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;

    private ItemChannels itemChannels;


    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ItemChannels getItemChannels() {
        return itemChannels;
    }

    public void setItemChannels(ItemChannels itemChannels) {
        this.itemChannels = itemChannels;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SnippetItemVideo getSnippetItemVideo() {
        return snippetItemVideo;
    }

    public void setSnippetItemVideo(SnippetItemVideo snippetItemVideo) {
        this.snippetItemVideo = snippetItemVideo;
    }

}
