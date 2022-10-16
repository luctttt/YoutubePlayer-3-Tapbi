package vn.tapbi.youtubeplayer3.data.model.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.tapbi.youtubeplayer3.data.model.item.Thumbnails;

public class SnippetChannel implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @SerializedName("thumbnails")
    @Expose
    private Thumbnail thumbnails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Thumbnail getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnail thumbnails) {
        this.thumbnails = thumbnails;
    }
}
