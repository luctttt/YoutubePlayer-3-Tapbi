package vn.tapbi.youtubeplayer3.data.model.statistics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.tapbi.youtubeplayer3.data.model.item.ContentDetails;
import vn.tapbi.youtubeplayer3.data.model.item.Statistics;

public class ItemStatistics {
    @SerializedName("statistics")
    @Expose
    private Statistics statistics;

    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }
}
