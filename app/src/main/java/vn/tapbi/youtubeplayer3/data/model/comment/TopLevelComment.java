package vn.tapbi.youtubeplayer3.data.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopLevelComment {
    @SerializedName("snippet")
    @Expose
    private Snippets snippets;

    public Snippets getSnippets() {
        return snippets;
    }

    public void setSnippets(Snippets snippets) {
        this.snippets = snippets;
    }
}
