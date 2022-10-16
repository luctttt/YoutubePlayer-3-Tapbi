package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Maxres implements Serializable {
    @SerializedName("url")
    @Expose
    private String urlMaxres;

    public String getUrlMaxres() {
        return urlMaxres;
    }

    public void setUrlMaxres(String urlMaxres) {
        this.urlMaxres = urlMaxres;
    }
}
