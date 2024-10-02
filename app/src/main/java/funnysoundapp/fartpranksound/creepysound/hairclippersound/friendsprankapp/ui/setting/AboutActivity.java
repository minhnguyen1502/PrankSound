package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.BuildConfig;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityAboutBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.policy.PolicyActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {

    @Override
    public ActivityAboutBinding getBinding() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        EventTracking.logEvent(AboutActivity.this, "about_view");

        binding.txt.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

        binding.ivBack.setOnClickListener(v -> onBack());

        binding.tvPolicy.setOnClickListener(v -> {
            EventTracking.logEvent(AboutActivity.this, "about_privacy_policy_click");

            startActivity(new Intent(AboutActivity.this, PolicyActivity.class));
        });
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {
        finish();
    }

}