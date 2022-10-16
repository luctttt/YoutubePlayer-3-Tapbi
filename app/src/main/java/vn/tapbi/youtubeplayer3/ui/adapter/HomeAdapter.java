package vn.tapbi.youtubeplayer3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.ItemHomeFragmentBinding;
import vn.tapbi.youtubeplayer3.ui.utils.EventClick;
import vn.tapbi.youtubeplayer3.ui.utils.Convert;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderAPIYoutube> {

    List<ItemVideo> listData;
    Context context;

    OnclickItemVideo clickItemVideo;

    public HomeAdapter() {
    }

    public void setData(List<ItemVideo> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    public HomeAdapter(List<ItemVideo> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    public void setInterface(OnclickItemVideo clickItemVideo) {
        this.clickItemVideo = clickItemVideo;
    }

    @NonNull
    @Override
    public ViewHolderAPIYoutube onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHomeFragmentBinding homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.item_home_fragment, parent, false);
        return new ViewHolderAPIYoutube(homeFragmentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAPIYoutube holder, int position) {

        ItemVideo item = listData.get(position);

        if (item == null || item.getSnippetItemVideo() == null) {
            Timber.d("onBindViewHolder: ....... : nulllll");
            return;
        }

        String linkImage = item.getSnippetItemVideo().getThumbnails().checkUrl();

        Glide.with(context).load(linkImage)     // fail
                .centerCrop()
                .dontAnimate()
                .into(holder.homeFragmentBinding.imgVideo);
        Glide.with(context).load(item.getItemChannels().getSnippetChannel().getThumbnails().getHigh().getUrlHigh())
                .centerCrop()
                .into(holder.homeFragmentBinding.imgAvatarVideo);

        holder.homeFragmentBinding.txtNameVideo.setText(item.getSnippetItemVideo().getTitleVideo());

        if (item.getStatistics() != null || item.getContentDetails() != null) {

            String viewCount = Convert.convertCountLong(item.getStatistics().getViewCount());
            String view = context.getResources().getString(R.string.view);
            String des = item.getItemChannels().getSnippetChannel().getTitle() + " - " + viewCount + " " + view + " - " + Convert.convertTimeNew(item.getSnippetItemVideo().getTimeVideo(), context);

            holder.homeFragmentBinding.txtTimeVideo.setText(des);
            holder.homeFragmentBinding.txtDurationVideo.setText(Convert.convertDuration(item.getContentDetails().getDuration()));
        }

        if (clickItemVideo != null && item.getItemChannels().getSnippetChannel().getThumbnails().getHigh().getUrlHigh() != null){

            holder.itemView.setOnClickListener(v -> {
                EventClick.preventTwoClick(v,2000);
                clickItemVideo.click(item, v);
            });
        }

        else Timber.d("clickItemvideo : null ");
    }



    @Override
    public int getItemCount() {

        if (listData == null)
            return 0;
        return listData.size();
    }

    public static class ViewHolderAPIYoutube extends RecyclerView.ViewHolder {

        private final ItemHomeFragmentBinding homeFragmentBinding;

        public ViewHolderAPIYoutube(@NonNull ItemHomeFragmentBinding homeFragmentBinding) {
            super(homeFragmentBinding.getRoot());
            this.homeFragmentBinding = homeFragmentBinding;

        }
    }

    public interface OnclickItemVideo {
        void click(ItemVideo item, View view);
    }
}
