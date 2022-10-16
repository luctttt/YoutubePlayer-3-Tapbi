package vn.tapbi.youtubeplayer3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.ItemSearchFragmentBinding;
import vn.tapbi.youtubeplayer3.ui.utils.Convert;
import vn.tapbi.youtubeplayer3.ui.utils.EventClick;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    List<ItemVideo> listItemVideos;
    Context context;
    clickItemVideoSearch clickItemVideo;

    public SearchAdapter() {
    }

    public void setData(List<ItemVideo> listItemVideos, Context context, clickItemVideoSearch clickItemVideo) {
        this.listItemVideos = listItemVideos;
        this.context = context;
        this.clickItemVideo = clickItemVideo;
        notifyDataSetChanged();
    }

    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchFragmentBinding binding = ItemSearchFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ItemVideo itemVideo = listItemVideos.get(position);

        if (itemVideo == null || itemVideo.getSnippetItemVideo() == null || context == null) {
            Timber.d("onBindViewHolder: ....... : nulllll");
            return;
        }

        Glide.with(context).load(itemVideo.getSnippetItemVideo().getThumbnails().checkUrl())
                .centerCrop()
                .into(holder.binding.imgImageVideoSearch);

        holder.binding.txtAuthorItemSearch.setText(itemVideo.getItemChannels().getSnippetChannel().getTitle());      // name video

        String viewCount = "";
        if (itemVideo.getStatistics() != null) {
            viewCount = Convert.convertCountLong(itemVideo.getStatistics().getViewCount());
        } else {
            viewCount = "10000";
        }

        String view = context.getResources().getString(R.string.view);
        String des = viewCount + " " + view + " - " + Convert.convertTimeNew(itemVideo.getSnippetItemVideo().getTimeVideo(), context);
        holder.binding.txtTimeViewItemSearch.setText(des);
        String duration;
        if (itemVideo.getContentDetails() != null) {
            duration = Convert.convertDuration(itemVideo.getContentDetails().getDuration());
        } else {
            duration = "10:00";
        }
        holder.binding.txtDurationVideoSearch.setText(duration);
        holder.binding.txtNameVideoItemSearch.setText(itemVideo.getSnippetItemVideo().getTitleVideo());

        if (clickItemVideo != null){
            holder.binding.getRoot().setOnClickListener(v -> {
                EventClick.preventTwoClick(v,2000);
                clickItemVideo.clickSearch(itemVideo, v);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listItemVideos == null)
            return 0;
        else return listItemVideos.size();
    }


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ItemSearchFragmentBinding binding;

        public SearchViewHolder(@NonNull ItemSearchFragmentBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface clickItemVideoSearch {
        void clickSearch(ItemVideo itemVideo, View view);
    }


}
