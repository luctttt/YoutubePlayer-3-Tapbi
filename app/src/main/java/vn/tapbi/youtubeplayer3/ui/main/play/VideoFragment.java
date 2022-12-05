package vn.tapbi.youtubeplayer3.ui.main.play;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.FragmentTopBinding;
import vn.tapbi.youtubeplayer3.feature.noti.NotificationLoadURL;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;

public class VideoFragment extends BaseBindingFragmentNoViewModel<FragmentTopBinding> {

    private String videoId = "";
    private ItemVideo item = new ItemVideo();
    YouTubePlayer youTubePlayer;

    private boolean checkPlayVideoBackground = false;
    private boolean isStop = false;

    public static int mState = -1;
    public static final int UNSTARTED = -1;
    public static final int BUFFERING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    public static final int ENDED = 3;

    ClickVideo clickVideo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_top;
    }

    public void setData(ItemVideo itemVideo, ClickVideo clickVideo) {
        this.item = itemVideo;
        this.clickVideo = clickVideo;
    }

    public String getIdVideo() {
        return videoId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onStateChange(@Nullable YouTubePlayer youTubePlayer, @Nullable PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                switch (state) {
                    case PLAYING:
                        Timber.d("lucttt : PLAYING");
                        if (mState == ENDED) {
                            clickVideo.loadAgainVideo();
                        }
                        if (mState == PAUSED) {
                            clickVideo.play();
                        }
                        mState = PLAYING;
                        break;
                    case PAUSED:
                        Timber.d("lucttt : PAUSED");
                        if (!isStop || !SharedPreferenceHelper.getStateBackground(requireContext())) {
                            if (mState == PLAYING) {
                                clickVideo.pause();
                            }
                            mState = PAUSED;
                        }
                        break;
                    case ENDED:
                        Timber.d("lucttt : ENDED");
                        mState = ENDED;
                        clickVideo.ended();
                        break;
                    case BUFFERING:
                        Timber.d("lucttt : BUFFERING");
                        if (mState == PLAYING) {
                            clickVideo.ended();
                        }
                        clickVideo.buffering();
                        mState = BUFFERING;
                        break;
                    case UNKNOWN:
                        Timber.d("lucttt : UNKNOWN");
                        mState = PAUSED;
                        break;
                    case UNSTARTED:
                        if (mState == PLAYING) {
                            clickVideo.unStarted();
                        }
                        Timber.d("lucttt : UNSTARTED");
                        mState = UNSTARTED;
                        break;
                }
            }
        });

        if (item != null) {
            videoId = item.getId();
            playVideo();
        }

    }

    public void playVideo() {
        getLifecycle().addObserver(binding.youtubePlayerView);

        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@Nullable YouTubePlayer mYouTubePlayer) {
                super.onReady(mYouTubePlayer);
                youTubePlayer = mYouTubePlayer;
                if (videoId != null) {
                    mYouTubePlayer.loadVideo(videoId, 0);
                }
                addCustomActionsToPlayer();
            }

            @Override
            public void onVideoLoadedFraction(@Nullable YouTubePlayer youTubePlayer, float loadedFraction) {
                super.onVideoLoadedFraction(youTubePlayer, loadedFraction);
            }
        });
    }

    private void addCustomActionsToPlayer() {       // button action youtube

        binding.youtubePlayerView.enableBackgroundPlayback(true);

        Drawable customAction1Icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_prerious);
        Drawable customAction2Icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_next_video);
        assert customAction1Icon != null;
        assert customAction2Icon != null;

        binding.youtubePlayerView.getPlayerUiController().setCustomAction1(customAction1Icon, view -> clickVideo.previous());
        binding.youtubePlayerView.getPlayerUiController().setCustomAction2(customAction2Icon, view -> clickVideo.next());

    }

    /* call back */
    public void setIsEnded(int isEnded) {
        mState = isEnded;
    }

    public void hideButtonYoutube() {
        binding.youtubePlayerView.getPlayerUiController().showUi(false);
    }

    public void showButtonYoutube() {
        binding.youtubePlayerView.getPlayerUiController().showUi(true);
    }

    public int isPlay() {
        return mState;
    }

    public void play() {
        youTubePlayer.play();
        mState = PLAYING;
    }

    public void pause() {
        mState = PAUSED;
        if (youTubePlayer != null)
            youTubePlayer.pause();
    }

    public void stop() {
        mState = PAUSED;
        binding.youtubePlayerView.release();
    }

    @Override
    public void onStart() {
        super.onStart();
        isStop = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
        if (SharedPreferenceHelper.getStateBackground(requireContext())) {
            checkPlayVideoBackground = true;
            binding.youtubePlayerView.enableBackgroundPlayback(true);
            if (youTubePlayer != null) {
                youTubePlayer.play();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (SharedPreferenceHelper.getStateBackground(requireContext())) {
            binding.youtubePlayerView.release();
            if (youTubePlayer != null) {
                NotificationLoadURL.removeNotification();
            }
        }
    }

    public interface ClickVideo {
        void loadAgainVideo();
        void previous();
        void play();
        void next();
        void ended();
        void pause();
        void unStarted();
        void buffering();
    }


}