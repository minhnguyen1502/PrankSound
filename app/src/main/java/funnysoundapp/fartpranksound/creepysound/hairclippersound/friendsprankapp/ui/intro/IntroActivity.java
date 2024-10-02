package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.intro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityIntroBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.main.MainActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.permission.PermissionActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

public class IntroActivity extends BaseActivity<ActivityIntroBinding> {
    ImageView[] dots = null;
    IntroAdapter introAdapter;
    String[] content;
    ViewPager viewPager;

    @Override
    public ActivityIntroBinding getBinding() {
        return ActivityIntroBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        viewPager = binding.viewPager2;

        content = new String[]{getString(R.string.playful_prank_sounds_for_you), getString(R.string.troll_your_friends_with_funny_sound), getString(R.string.get_a_good_laugh_anytime)};

        dots = new ImageView[]{binding.ivCircle01, binding.ivCircle02, binding.ivCircle03};

        introAdapter = new IntroAdapter(this);

        viewPager.setAdapter(introAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeContentInit(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void bindView() {
        binding.btnNext.setOnClickListener(view -> {
            if (viewPager.getCurrentItem() == 0) {
                EventTracking.logEvent(IntroActivity.this, "Intro1_next_click");
            } else if (viewPager.getCurrentItem() == 1) {
                EventTracking.logEvent(IntroActivity.this, "Intro2_next_click");
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });

        binding.btnStart.setOnClickListener(view -> {
            EventTracking.logEvent(IntroActivity.this, "Intro3_next_click");

            goToNextScreen();
        });
    }

    @Override
    public void onBack() {
        finishAffinity();
    }

    private void changeContentInit(int position) {
        binding.tvContent.setText(content[position]);

        for (int i = 0; i < 3; i++) {
            if (i == position) dots[i].setImageResource(R.drawable.ic_intro_sn);
            else dots[i].setImageResource(R.drawable.ic_intro_s);
        }

        switch (position) {
            case 0:
                EventTracking.logEvent(IntroActivity.this, "Intro1_view");

                binding.viewBottom.setGravity(Gravity.CENTER);
            case 1:
                EventTracking.logEvent(IntroActivity.this, "Intro2_view");

                binding.btnNext.setVisibility(View.VISIBLE);
                binding.btnStart.setVisibility(View.GONE);
                binding.shadow.setVisibility(View.VISIBLE);
                break;
            case 2:
                EventTracking.logEvent(IntroActivity.this, "Intro3_view");

                binding.btnNext.setVisibility(View.GONE);
                binding.shadow.setVisibility(View.GONE);
                binding.btnStart.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void goToNextScreen() {
        if(checkMediaPermission ()){
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(IntroActivity.this, PermissionActivity.class);
            startActivity(intent);

        }
    }

    private boolean checkMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For Android versions below 13, check the legacy permission
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeContentInit(viewPager.getCurrentItem());
    }
}