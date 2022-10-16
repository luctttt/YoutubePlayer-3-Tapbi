package vn.tapbi.youtubeplayer3.data.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("topLevelComment")
    @Expose
    private TopLevelComment topLevelComment;

    public TopLevelComment getTopLevelComment() {
        return topLevelComment;
    }

    public void setTopLevelComment(TopLevelComment topLevelComment) {
        this.topLevelComment = topLevelComment;
    }
}
