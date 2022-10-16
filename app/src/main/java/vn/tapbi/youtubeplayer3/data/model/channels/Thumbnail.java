package vn.tapbi.youtubeplayer3.data.model.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.tapbi.youtubeplayer3.data.model.item.Default;
import vn.tapbi.youtubeplayer3.data.model.item.High;
import vn.tapbi.youtubeplayer3.data.model.item.Maxres;
import vn.tapbi.youtubeplayer3.data.model.item.Medium;
import vn.tapbi.youtubeplayer3.data.model.item.Standard;

public class Thumbnail implements Serializable {

    @SerializedName("high")
    @Expose
    private High high;


    @SerializedName("medium")
    @Expose
    private Medium medium;

    @SerializedName("default")
    @Expose
    private Default adefault;

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Default getAdefault() {
        return adefault;
    }

    public void setAdefault(Default adefault) {
        this.adefault = adefault;
    }
}
