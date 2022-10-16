package vn.tapbi.youtubeplayer3.data.model.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemChannels implements Serializable {
    @SerializedName("snippet")
    @Expose
    private SnippetChannel snippetChannel;

    @SerializedName("statistics")
    @Expose
    private StatisticsChannel statistics;

    public StatisticsChannel getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsChannel statistics) {
        this.statistics = statistics;
    }

    public SnippetChannel getSnippetChannel() {
        return snippetChannel;
    }

    public void setSnippetChannel(SnippetChannel snippetChannel) {
        this.snippetChannel = snippetChannel;
    }
}
