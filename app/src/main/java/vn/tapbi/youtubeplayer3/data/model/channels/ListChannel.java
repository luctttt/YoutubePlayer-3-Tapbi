package vn.tapbi.youtubeplayer3.data.model.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListChannel implements Serializable {
    @SerializedName("items")
    @Expose
    private List<ItemChannels> channelsList;

    public List<ItemChannels> getChannelsList() {
        return channelsList;
    }

    public void setChannelsList(List<ItemChannels> channelsList) {
        this.channelsList = channelsList;
    }
}
