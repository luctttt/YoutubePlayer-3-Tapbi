package vn.tapbi.youtubeplayer3.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recentsearch")
public class RecentModel {

    public RecentModel(String keyword) {
        this.keyword = keyword;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
