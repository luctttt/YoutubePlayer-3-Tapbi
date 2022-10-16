package vn.tapbi.youtubeplayer3.data.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippets {

    @SerializedName("textDisplay")
    @Expose
    private String textDisplay;

    @SerializedName("authorDisplayName")
    @Expose
    private String authorDisplayName;

    @SerializedName("authorProfileImageUrl")
    @Expose
    private String authorProfileImageUrl;

    @SerializedName("authorChannelUrl")
    @Expose
    private String authorChannelUrl;


    public String getTextDisplay() {
        return textDisplay;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public String getAuthorChannelUrl() {
        return authorChannelUrl;
    }

    public void setAuthorChannelUrl(String authorChannelUrl) {
        this.authorChannelUrl = authorChannelUrl;
    }
}
