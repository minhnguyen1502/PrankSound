package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivitySplashBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.intro.IntroActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.LanguageStartActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.SharePrefUtils;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.Utils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {


    @Override
    public ActivitySplashBinding getBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        EventTracking.logEvent(this, "splash_open");
        new Handler(Looper.getMainLooper()).postDelayed(this::runAfterFinish,3000);

    }
    public void runAfterFinish() {

        if (!isFinishing() && !isDestroyed()) {

            if (!Utils.isLanguageSelected()) {
                Intent intent = new Intent(this, LanguageStartActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {

    }
}
