package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContentDetails implements Serializable {
    @SerializedName("duration")
    @Expose
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
