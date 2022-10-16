package vn.tapbi.youtubeplayer3.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.internal.BaselineLayout;
import com.tuanhav95.drag.DragView;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.common.Constant;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.data.model.item.ItemVideo;
import vn.tapbi.youtubeplayer3.databinding.ActivityFragmentBinding;
import vn.tapbi.youtubeplayer3.feature.noti.MyService;
import vn.tapbi.youtubeplayer3.feature.noti.NotificationLoadURL;
import vn.tapbi.youtubeplayer3.feature.noti.Playable;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingActivity;
import vn.tapbi.youtubeplayer3.ui.main.home.HomeFragment;
import vn.tapbi.youtubeplayer3.ui.main.play.BottomFragment;
import vn.tapbi.youtubeplayer3.ui.main.play.VideoFragment;
import vn.tapbi.youtubeplayer3.ui.main.search.SearchFragment;
import vn.tapbi.youtubeplayer3.ui.main.trendding.TrendingFragment;
import vn.tapbi.youtubeplayer3.ui.utils.Convert;

public class HomeActivity extends BaseBindingActivity<ActivityFragmentBinding, HomeActivityViewModel> implements HomeFragment.CallBackItemVideo,
        SearchFragment.CallBackItemVideoSearch, TrendingFragment.CallBackItemVideo, BottomFragment.CallBack, VideoFragment.ClickVideo, Playable {

    NavController navController;
    VideoFragment topFragment;
    BottomFragment bottomFragment;

    FragmentManager fm = getSupportFragmentManager();
    ItemVideo item = new ItemVideo();

    boolean isFullScreen = true;
    boolean isPlay = false;
    boolean isbuffering = false;
    boolean isCrash = false;

    Thread thread;
    int duration = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public Class<HomeActivityViewModel> getViewModel() {
        return HomeActivityViewModel.class;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), MyService.class));
        }

        /*create notification*/
        NotificationLoadURL.createNotification(null, null, getBaseContext(), null, R.drawable.ic_baseline_play_arrow_24);

        if (savedInstanceState != null) {
            Fragment fragmentTop = this.getSupportFragmentManager().findFragmentByTag(VideoFragment.class.getSimpleName());
            Fragment fragmentBottom = this.getSupportFragmentManager().findFragmentByTag(BottomFragment.class.getSimpleName());

            if (fragmentTop != null && fragmentBottom != null) {
                isCrash = false;
                topFragment = (VideoFragment) fragmentTop;
                bottomFragment = (BottomFragment) fragmentBottom;
            } else {
                ItemVideo itemVideo = (ItemVideo) savedInstanceState.getSerializable(Constant.ITEM_VIDEO);
                if (itemVideo != null) {
                    call(itemVideo);
                }
                isCrash = true;
            }

        }

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        binding.navView.setItemIconTintList(null);

        /* gradient color of bottomnav  */
        BottomNavigationMenuView bottomMenuView = (BottomNavigationMenuView) binding.navView.getChildAt(0);
        for (int i = 0; i < 4; i++) {
            BottomNavigationItemView bottomItemView = (BottomNavigationItemView) bottomMenuView.getChildAt(i);
            BaselineLayout baselineLayout = (BaselineLayout) bottomItemView.getChildAt(1);
            TextView txt_large = (TextView) baselineLayout.getChildAt(1);
            setTextViewColor(txt_large, getResources().getIntArray(R.array.arrayColor));
        }
        if (!isCrash)
            binding.dragView.close();

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /* giu trang thai khi kill app */

        if (topFragment != null && bottomFragment != null) {
            outState.putString(Constant.VIDEO, topFragment.getIdVideo());
            outState.putSerializable(Constant.ITEM_VIDEO, item);
        }
    }

    public static void setTextViewColor(TextView textView, int[] color) {
        TextPaint textPaint = textView.getPaint();
        float width = textPaint.measureText(textView.getText().toString());
        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }

    @Override
    public void call(ItemVideo itemVideo) {

        binding.dragView.maximize();
        if (topFragment == null || bottomFragment == null) {
            topFragment = new VideoFragment();
            bottomFragment = new BottomFragment();
        }
        if (topFragment.isAdded() || bottomFragment.isAdded()) {
            fm.beginTransaction().remove(topFragment).commit();
            fm.beginTransaction().remove(bottomFragment).commit();
            topFragment = new VideoFragment();
            bottomFragment = new BottomFragment();
        }

        fm.beginTransaction().add(R.id.frameTop, topFragment).commit();
        fm.beginTransaction().add(R.id.frameBottom, bottomFragment).commit();

        this.item = itemVideo;
        topFragment.setData(itemVideo, this);       // send data
        bottomFragment.setData(itemVideo);
        bottomFragment.setInterface(this);

        isbuffering = false;

        if (isPlay) {
            hideProgressBar();
            synchronized (thread) {
                thread.interrupt();
            }
        }
        binding.progressBar.setProgress(0);

        thread = new Thread(() -> {
            isPlay = true;
            startProgress(0, Convert.convertDuration(itemVideo.getContentDetails().getDuration()));
        });

        binding.dragView.isPlay();
        binding.dragView.setVisibility(View.VISIBLE);
        hideNavigation();
        View shadow = findViewById(R.id.shadow);

        binding.dragView.setDragListener(new DragView.DragListener() {      // event scroll item play
            @Override
            public void onExpanded() {
            }

            @Override
            public void onChangeState(@Nullable DragView.State state) {
                if (state == DragView.State.MIN) {
                    showNavigation();
                    isFullScreen = false;
                    topFragment.hideButtonYoutube();
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (state == DragView.State.MAX) {
                    hideNavigation();
                    topFragment.showButtonYoutube();
                    isFullScreen = true;
                }
            }

            @Override
            public void onChangePercent(float v) {
                shadow.setAlpha(v);
                if (isFullScreen) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void startProgress(int index, String s) {
        int n = Convert.getTimeVideo(s);
        binding.progressBar.setMax(n);
        for (int i = index + 1; i <= n; i++) {
            Timber.d(String.valueOf(i));
            if (!isPlay) {
                duration = i;
                return;
            }
            if (topFragment != null && VideoFragment.mState == VideoFragment.ENDED) {
                binding.progressBar.setProgress(n);
                return;
            }
            try {
                Thread.sleep(1000);
                binding.progressBar.setProgress(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* event item min */
    public String getTitleVideo() {
        if (bottomFragment == null)
            return "";
        return bottomFragment.getTitleVideo();
    }

    public String getChannelVideo() {
        if (bottomFragment == null)
            return "";
        return bottomFragment.getChannelTitle();

    }

    /* event call back video */
    public void playVideo() {
        topFragment.play();
        binding.dragView.isPlay();
        if (thread != null) {
            synchronized (thread) {
                thread.interrupt();
                thread = new Thread(() -> {
                    isPlay = true;
                    if (VideoFragment.mState == VideoFragment.ENDED) {
                        binding.progressBar.setProgress(0);
                        topFragment.setIsEnded(VideoFragment.PLAYING);
                        startProgress(0, Convert.convertDuration(item.getContentDetails().getDuration()));
                    } else if (VideoFragment.mState == VideoFragment.UNSTARTED) {
                        binding.progressBar.setProgress(0);
                        topFragment.setIsEnded(VideoFragment.PLAYING);
                    } else {
                        startProgress(duration, Convert.convertDuration(item.getContentDetails().getDuration()));
                    }
                });
                thread.start();
            }
        }
    }

    public void pauseVideo() {
        if (topFragment != null) {
            topFragment.pause();
            isPlay = false;
            synchronized (thread) {
                thread.interrupt();
            }
        }
    }

    public void stopVideo() {
        binding.dragView.close();
        topFragment.stop();
        topFragment = null;
        bottomFragment = null;
    }

    public void showNavigation() {
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.open_nav_view);
        binding.navView.setAnimation(ani);
    }

    public void hideNavigation() {
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.exit_nav_view);
        binding.navView.setAnimation(ani);
    }

    public void hideProgressBar() {
        isPlay = false;
        synchronized (thread) {
            thread.interrupt();
        }
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
    /*------------*/

    /* call back activity */
    @Override
    public void onBackPressed() {
        if (topFragment == null || bottomFragment == null || !isFullScreen) {
            super.onBackPressed();
        } else {
            binding.dragView.minimize();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (topFragment != null && SharedPreferenceHelper.getStateBackground(getApplicationContext())) {
            NotificationLoadURL.removeNotification();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (topFragment != null) {
            if (SharedPreferenceHelper.getStateBackground(getApplicationContext())) {
                NotificationLoadURL.createNotification(item.getSnippetItemVideo().getTitleVideo(),
                        item.getSnippetItemVideo().getChannelTitle(),
                        getApplicationContext(), item.getSnippetItemVideo().getThumbnails().getMedium().getUrlHigh(), R.drawable.ic_baseline_play_arrow_24);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferenceHelper.savePosition(getApplicationContext(), 0, SharedPreferenceHelper.POSITION_HOME);
        SharedPreferenceHelper.savePosition(getApplicationContext(), 0, SharedPreferenceHelper.POSITION_TRENDING);
        NotificationLoadURL.removeNotification();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    /* interface bottomFragment */

    @Override
    public void playVideoRelated(ItemVideo itemVideo) {
        call(itemVideo);
    }

    @Override
    public void previous() {
        if (thread != null) {
            synchronized (thread) {
                thread.interrupt();
            }
        }
        bottomFragment.randomVideo();
    }

    @Override
    public void play() {
        playVideo();
    }

    @Override
    public void next() {
        if (thread != null) {
            synchronized (thread) {
                thread.interrupt();
            }
        }
        bottomFragment.randomVideo();
    }

    @Override
    public void ended() {
        binding.dragView.isPause();
    }

    @Override
    public void pause() {
        pauseVideo();
        binding.dragView.isPause();
    }

    @Override
    public void unStarted() {
        playVideo();
        //binding.dragView.isPause();
    }

    @Override
    public void buffering() {
        if (!isbuffering && thread != null && !thread.isAlive()) {
            thread.start();
        }
        isbuffering = true;
        topFragment.setIsEnded(VideoFragment.BUFFERING);
        binding.dragView.isPlay();

    }

    @Override
    public void loadAgainVideo() {
        playVideo();
    }

    /* interface topFragment */


    /* service */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // TODO: 10/5/2021
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("action_name");
            Timber.d("luctttt : action : %s",action);
            if (action.equals(NotificationLoadURL.ACTION_PLAY)) {
                if (VideoFragment.mState == VideoFragment.PLAYING) {
                    onPauseNoti();
                } else if (VideoFragment.mState == VideoFragment.PAUSED || VideoFragment.mState == VideoFragment.ENDED) {
                    onPlayNoti();
                }
            }
        }
    };

    /* interface service */
    @Override
    public void onPlayNoti() {
        playVideo();
        NotificationLoadURL.createNotification(null, null, getApplicationContext(), null, R.drawable.ic_baseline_pause_24);
        Timber.d("luctttt : service intent : Play");
    }

    @Override
    public void onPauseNoti() {
        pauseVideo();
        NotificationLoadURL.createNotification(null, null, getApplicationContext(), null, R.drawable.ic_baseline_play_arrow_24);
        Timber.d("luctttt : service intent : Pause");
    }


}