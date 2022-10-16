package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class High implements Serializable {
    @SerializedName("url")
    @Expose
    private String urlHigh;

    public String getUrlHigh() {
        return urlHigh;
    }

    public void setUrlHigh(String urlHigh) {
        this.urlHigh = urlHigh;
    }
}
