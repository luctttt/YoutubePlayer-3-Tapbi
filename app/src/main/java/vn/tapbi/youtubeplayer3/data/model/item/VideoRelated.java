package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoRelated {
    @SerializedName("items")
    @Expose
    private List<ItemRelated> itemRelated = null ;

    public List<ItemRelated> getItemRelated() {
        return itemRelated;
    }

    public void setItemRelated(List<ItemRelated> itemRelated) {
        this.itemRelated = itemRelated;
    }
}
