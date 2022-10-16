package vn.tapbi.youtubeplayer3.data.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListComment {
    @SerializedName("items")
    @Expose
    private List<ItemComment> listItem;

    public List<ItemComment> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemComment> listItem) {
        this.listItem = listItem;
    }
}
