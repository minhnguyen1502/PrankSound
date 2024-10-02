package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.FragmentSettingBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.dialog.rate.IClickDialogRate;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.dialog.rate.RatingDialog;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.LanguageActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.setting.AboutActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.SharePrefUtils;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.SystemUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class SettingFragment extends BaseFragment<FragmentSettingBinding> {
    Context context;
    private Activity activity;
    boolean isRate = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = requireActivity();

    }

    @Override
    public FragmentSettingBinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentSettingBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        EventTracking.logEvent(context, "setting_view");

        binding.btnAbout.setOnClickListener(v -> {
            EventTracking.logEvent(context, "setting_about_click");

            startActivity(new Intent(context, AboutActivity.class));
        });

        if (SharePrefUtils.isRated(context)) {
            binding.btnRate.setVisibility(View.GONE);
            binding.shadowRate.setVisibility(View.GONE);
        }

        binding.btnLanguage.setOnClickListener(v -> {
            EventTracking.logEvent(context, "setting_language_click");

            startActivity(new Intent(context, LanguageActivity.class));
        });

        binding.btnRate.setOnClickListener(v -> {
            EventTracking.logEvent(context, "setting_rate_click");
            if (!isRate){
                isRate = true;
                rate();
            }
        });

        binding.btnShare.setOnClickListener(v -> {
            EventTracking.logEvent(context, "setting_share_click");
            binding.btnShare.setEnabled(false);
            share();
        });
        String currentLanguageCode = SystemUtil.getPreLanguage(context);
        String currentLanguage = getLanguageNameByCode(currentLanguageCode);
        binding.tvLanguage.setText(currentLanguage);
    }

    @Override
    public void bindView() {

    }
    private String getLanguageNameByCode(String code) {
        switch (code) {
            case "en":
                return "English";
            case "zh":
                return "China";
            case "fr":
                return "French";
            case "de":
                return "German";
            case "hi":
                return "Hindi";
            case "in":
                return "Indonesia";
            case "pt":
                return "Portuguese";
            case "es":
                return "Spanish";
            default:
                return "Unknown";
        }
    }

    private void share() {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intentShare.putExtra(Intent.EXTRA_TEXT, "Download application :" + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        startActivity(Intent.createChooser(intentShare, "Share with"));
        binding.btnShare.postDelayed(() -> binding.btnShare.setEnabled(true), 500);

    }
    ReviewInfo reviewInfo;
    ReviewManager manager;

    private void rate() {
        isRate = true;
        EventTracking.logEvent(context, "rate_show");

        RatingDialog ratingDialog = new RatingDialog(context, true);
        ratingDialog.init(new IClickDialogRate() {
            @Override
            public void send() {
                isRate = false;
                binding.btnRate.setVisibility(View.GONE);
                ratingDialog.dismiss();
                String uriText = "mailto:" + SharePrefUtils.email + "?subject=" + "Review for " + SharePrefUtils.subject + "&body=" + SharePrefUtils.subject + "\nRate : " + ratingDialog.getRating() + "\nContent: ";
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                try {
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.Send_Email)));
                    SharePrefUtils.forceRated(context);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, getString(R.string.There_is_no), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void rate() {
                isRate = false;
                manager = ReviewManagerFactory.create(context);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reviewInfo = task.getResult();
                        Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                        flow.addOnSuccessListener(result -> {
                            binding.btnRate.setVisibility(View.GONE);
                            SharePrefUtils.forceRated(context);
                            ratingDialog.dismiss();
                        });
                    } else {

                        ratingDialog.dismiss();
                    }
                });
            }

            @Override
            public void later() {
                isRate = false;
                ratingDialog.dismiss();
            }

        });
        try {
            ratingDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }
}