package vn.tapbi.youtubeplayer3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;
import vn.tapbi.youtubeplayer3.databinding.ItemRecentSearchHistoryBinding;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    Context context;
    clickItemVideoSearch clickItemRecent;
    List<RecentModel>recentModelList = new ArrayList<>();

    ItemRecentSearchHistoryBinding binding;

    public RecentAdapter(List<RecentModel> recentModelList , clickItemVideoSearch clickItemRecent) {
        Collections.reverse(recentModelList);
        this.recentModelList = recentModelList;
        this.clickItemRecent = clickItemRecent;
    }


    public void setData(List<RecentModel> listItemVideos) {
        this.recentModelList = listItemVideos;
        notifyDataSetChanged();
    }


    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         binding = ItemRecentSearchHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RecentViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        RecentModel recentModel = recentModelList.get(position);

        binding.txtNameVideoSearch.setText(recentModel.getKeyword());
        Timber.d("onBindViewHolder: click recent");
        if (clickItemRecent != null)
            holder.binding.getRoot().setOnClickListener(v -> clickItemRecent.clickItemVideo(recentModel));
    }

    @Override
    public int getItemCount() {
        if (recentModelList == null)
            return 0;
        else return recentModelList.size();
    }


    public static class RecentViewHolder extends RecyclerView.ViewHolder {
        ItemRecentSearchHistoryBinding binding;

        public RecentViewHolder(@NonNull ItemRecentSearchHistoryBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface clickItemVideoSearch {
        void clickItemVideo(RecentModel itemVideo);
    }


}
