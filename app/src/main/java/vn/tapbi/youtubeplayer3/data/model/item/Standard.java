package vn.tapbi.youtubeplayer3.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Standard implements Serializable {
    @SerializedName("url")
    @Expose
    private String uriStandard;

    public String getUriStandard() {
        return uriStandard;
    }

    public void setUriStandard(String uriStandard) {
        this.uriStandard = uriStandard;
    }
}
