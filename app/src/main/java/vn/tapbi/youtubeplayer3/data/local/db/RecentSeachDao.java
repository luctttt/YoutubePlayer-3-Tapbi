package vn.tapbi.youtubeplayer3.data.local.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;

@Dao
public interface RecentSeachDao {
    @Query("SELECT * FROM recentsearch WHERE keyword LIKE '%' || :name || '%' ")
    List<RecentModel> searchRecent(String name);

    @Insert
    void insertRecentSearch(RecentModel recentModel);


    @Query("SELECT * FROM history WHERE keyword LIKE '%' || :name || '%' ")
    List<HistoryModel> searchHistory(String name);

    @Insert
    void insertHistorySearch(HistoryModel historyModel);

    @Query("SELECT * FROM history")
    List<HistoryModel> getListHistory();
}
