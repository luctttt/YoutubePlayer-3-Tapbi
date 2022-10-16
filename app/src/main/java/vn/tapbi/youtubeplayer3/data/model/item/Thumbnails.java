package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnails implements Serializable {
    @SerializedName("maxres")
    @Expose
    private Maxres maxres;

    @SerializedName("standard")
    @Expose
    private Standard standard;

    @SerializedName("high")
    @Expose
    private High high;


    @SerializedName("medium")
    @Expose
    private Medium medium;

    @SerializedName("default")
    @Expose
    private Default adefault;


    public Default getAdefault() {
        return adefault;
    }

    public void setAdefault(Default adefault) {
        this.adefault = adefault;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Maxres getMaxres() {
        return maxres;
    }

    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }


    public String checkUrl() {
        if (maxres != null)
            return maxres.getUrlMaxres();
        else if ( standard != null)
            return standard.getUriStandard();
        else if (high != null)
            return high.getUrlHigh();
        else if (medium != null)
            return medium.getUrlHigh();
        else if (adefault != null)
            return adefault.getUrlHigh();
        else return "";
    }
}
