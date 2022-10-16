package vn.tapbi.youtubeplayer3.data.model.statistics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewDetailStatistics {
    @SerializedName("items")
    @Expose
    private List<ItemStatistics> itemStatistics;

    public List<ItemStatistics> getItemStatistics() {
        return itemStatistics;
    }

    public void setItemStatistics(List<ItemStatistics> itemStatistics) {
        this.itemStatistics = itemStatistics;
    }
}
