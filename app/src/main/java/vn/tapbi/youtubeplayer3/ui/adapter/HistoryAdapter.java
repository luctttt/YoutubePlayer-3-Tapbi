package vn.tapbi.youtubeplayer3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.databinding.ItemRecentSearchBinding;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewModelHistory> {

    List<HistoryModel> historyModels;
    Context context;

    OnclickItemVideoHistory clickItemVideo;
    ItemRecentSearchBinding bindingHistory;

    public HistoryAdapter(List<HistoryModel> listData, Context context) {
        Collections.reverse(listData);
        this.historyModels = listData;
        this.context = context;
    }


    public void setInterface(OnclickItemVideoHistory clickItemVideo) {
        this.clickItemVideo = clickItemVideo;
    }

    @NonNull
    @Override
    public ViewModelHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        bindingHistory = DataBindingUtil.inflate(inflater, R.layout.item_recent_search, parent, false);

        return new ViewModelHistory(bindingHistory);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModelHistory holder, int position) {

        HistoryModel his = historyModels.get(position);

        holder.bindingHistory.txtNameHistory.setText(his.getKeyword());

        if (clickItemVideo != null )
            holder.itemView.setOnClickListener(v -> clickItemVideo.clickHistory(his));        // null click
    }

    @Override
    public int getItemCount() {

        if (historyModels == null)
            return 0;
        return historyModels.size();
    }

    public static class ViewModelHistory extends RecyclerView.ViewHolder {

        private final ItemRecentSearchBinding bindingHistory;

        public ViewModelHistory(@NonNull ItemRecentSearchBinding homeFragmentBinding) {
            super(homeFragmentBinding.getRoot());
            this.bindingHistory = homeFragmentBinding;

        }
    }

    public interface OnclickItemVideoHistory {
        void clickHistory(HistoryModel item);
    }
}
