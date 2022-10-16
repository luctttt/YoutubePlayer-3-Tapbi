package vn.tapbi.youtubeplayer3.data.model.item;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import vn.tapbi.youtubeplayer3.data.model.channels.ListChannel;

public class VideoDetail implements Serializable {
    @SerializedName("items")
    @Expose
    private List<ItemVideo>items = null ;

    private List<ListChannel>listChannels = null ;

    public List<ListChannel> getListChannels() {
        return listChannels;
    }

    public void setListChannels(List<ListChannel> listChannels) {
        this.listChannels = listChannels;
    }

    public List<ItemVideo> getItems() {
        return items;
    }


    public void setItems(List<ItemVideo> items) {
        this.items = items;
    }
}
