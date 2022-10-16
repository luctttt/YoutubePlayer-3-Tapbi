package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemRelated {
    @SerializedName("id")
    @Expose
    private Id id;

    @SerializedName("snippet")
    @Expose
    private SnippetItemVideo snippetItemVideo;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public SnippetItemVideo getSnippetItemVideo() {
        return snippetItemVideo;
    }


    public void setSnippetItemVideo(SnippetItemVideo snippetItemVideo) {
        this.snippetItemVideo = snippetItemVideo;
    }
}
