package vn.tapbi.youtubeplayer3.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import vn.tapbi.youtubeplayer3.data.local.db.RecentDatabase;
import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;

public class RecentRepository {

    RecentDatabase recentDatabase;

    @Inject
    public RecentRepository(RecentDatabase recentDatabase) {
        this.recentDatabase = recentDatabase;
    }

    public List<HistoryModel> getHistory() {
        return recentDatabase.recentDao().getListHistory();
    }

    public List<RecentModel> searchRecent(String name) {
        return recentDatabase.recentDao().searchRecent(name);
    }

    public List<HistoryModel>searchHistory(String name) {
        return recentDatabase.recentDao().searchHistory(name);
    }

    public void insertRecentSearch(RecentModel recentModel) {
        recentDatabase.recentDao().insertRecentSearch(recentModel);
    }

    public void insertHistorySearch(HistoryModel historyModel) {
        recentDatabase.recentDao().insertHistorySearch(historyModel);
    }




}
