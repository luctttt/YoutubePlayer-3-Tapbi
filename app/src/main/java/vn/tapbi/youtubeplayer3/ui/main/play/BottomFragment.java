package vn.tapbi.youtubeplayer3.ui.main.play;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.model.comment.ItemComment;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.FragmentBottomBinding;
import vn.tapbi.youtubeplayer3.ui.adapter.HomeAdapter;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragment;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;
import vn.tapbi.youtubeplayer3.ui.main.play.comment.CommentFragment;
import vn.tapbi.youtubeplayer3.ui.splash.SplashActivity;
import vn.tapbi.youtubeplayer3.ui.utils.Convert;


public class BottomFragment extends BaseBindingFragment<FragmentBottomBinding, VideoViewModel> implements HomeAdapter.OnclickItemVideo{

    ItemVideo item;
    HomeAdapter mainAdapter = new HomeAdapter();
    CommentFragment commentFragment = new CommentFragment();
    boolean isShowComment = false;

    List<ItemComment> listComment = new ArrayList<>();
    List<ItemVideo> listID = new ArrayList<>();

    CallBack callBack;

    @Override
    protected Class<VideoViewModel> getViewModel() {
        return VideoViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bottom;
    }

    public void setData(ItemVideo itemVideo) {
        this.item = itemVideo;
    }

    public void setInterface(CallBack callBack){
        this.callBack = callBack;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        if (item != null) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                getVideoRelated(item.getId());
                getComments(item);
                getVideoFromHome(item);
                showView(item);
            }, 1000);

        }
    }

    public void randomVideo() {
        if (listID.size() > 0) {
            Random rd = new Random();
            int a = rd.nextInt(listID.size()-1);
            this.item = listID.get(a);
            binding.scrollView.scrollTo(0, 0);
            callBack.playVideoRelated(item);
        }
    }

    private void getVideoRelated(String videoId) {

        viewModel.setId(videoId);
        viewModel.getListVideoRelated();
        binding.recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getVideoRelated().observe(getViewLifecycleOwner(), items -> {
            mainAdapter.setData(items, getContext());
            binding.recyclerViewMain.setAdapter(mainAdapter);
            mainAdapter.setInterface(this);
            mainAdapter.notifyDataSetChanged();
            listID.addAll(items);
        });

    }

    private void getComments(ItemVideo item) {
        String videoId = item.getId();
        viewModel.setId(videoId);
        viewModel.getCommentVideo();

        viewModel.getCommentLiveData().observe(getViewLifecycleOwner(), items -> {
            this.listComment = items;

            if (items.size() > 0) {
                Glide.with(requireContext()).load(items.get(0).getSnippet().getTopLevelComment().getSnippets().getAuthorProfileImageUrl())
                        .into(binding.imgAuthorProfileImageUrl);
                binding.txtComment.setText(items.get(0).getSnippet().getTopLevelComment().getSnippets().getTextDisplay());
            }

        });

        binding.btnShowComment.setOnClickListener(v -> {
            createFragmentComment(listComment, item.getStatistics().getCommentCount());
        });
    }

    private void createFragmentComment(List<ItemComment> itemss, int countComment) {
        if (commentFragment.isAdded()) {
            return;
        }
        if (!isShowComment) {
            isShowComment = true;
            commentFragment.setDataComment(itemss, Convert.convertCount(countComment));
            commentFragment.setCancelable(false);
            commentFragment.show(getChildFragmentManager(), commentFragment.getTag());
        }
        if (commentFragment.checkStateCommentFragment() && commentFragment != null) {
            isShowComment = false;
        }

    }

    private void getVideoFromHome(ItemVideo item) {       // update item - object

        Glide.with(this).load(item.getItemChannels().getSnippetChannel().getThumbnails().getAdefault().getUrlHigh())
                .centerCrop()
                .into(binding.imgAvatarVideo);
        binding.txtTitleVideo.setText(item.getSnippetItemVideo().getTitleVideo());
        binding.txtNameArtist.setText(item.getSnippetItemVideo().getChannelTitle());

    }

    public String getChannelTitle(){
        return item.getSnippetItemVideo().getChannelTitle();
    }
    public String getTitleVideo(){
        if (item.getSnippetItemVideo() != null){
            return item.getSnippetItemVideo().getTitleVideo();
        }else{
            return "";
        }

    }

    public void showView(ItemVideo item) {  // update main - id - view

        binding.txtAmountDislike.setText(Convert.convertCount(item.getStatistics().getDislikeCount()));
        binding.txtAmountLike.setText(Convert.convertCount(item.getStatistics().getLikeCount()));
        binding.txtCommentCount.setText(Convert.convertCount(item.getStatistics().getCommentCount()));

        String timeVideo = Convert.convertTimeNew(item.getItemChannels().getSnippetChannel().getPublishedAt(), getContext());
        String viewVideos = Convert.convertCountLong(item.getItemChannels().getStatistics().getViewCount());
        String detailVideos = viewVideos + " - " + timeVideo;

        binding.txtDetailVideo.setText(detailVideos);

        String viewVideo = Convert.convertCountLong(item.getStatistics().getViewCount());
        String views = requireContext().getResources().getString(R.string.view);

        String tim_viewVideo = viewVideo + " " + views + " - " + Convert.convertTimeNew(item.getSnippetItemVideo().getTimeVideo(), requireContext());
        binding.txtTimeViewVideo.setText(tim_viewVideo);

    }

    @Override
    public void click(ItemVideo item, View view) {
//        String videoId = item.getId();
//        this.item = item;
        binding.scrollView.scrollTo(0, 0);

        callBack.playVideoRelated(item);

//        getVideoFromHome(item);
//        getVideoRelated(videoId);
//        getComments(item);
//        showView(item);
    }

    public interface CallBack {
        void playVideoRelated(ItemVideo itemVideo);
    }
}
