package vn.tapbi.youtubeplayer3.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.databinding.ActivitySplashBinding;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingActivity;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent_Splash = new Intent(SplashActivity.this , HomeActivity.class);
            startActivity(intent_Splash);
            finish();
        }, 3000);

    }
}