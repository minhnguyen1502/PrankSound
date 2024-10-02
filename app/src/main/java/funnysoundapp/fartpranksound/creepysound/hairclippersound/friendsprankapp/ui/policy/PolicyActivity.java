package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.policy;

import android.annotation.SuppressLint;
import android.view.View;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ads.IsNetWork;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityPolicyBinding;

public class PolicyActivity extends BaseActivity<ActivityPolicyBinding> {

    String linkPolicy = "https://firebasestorage.googleapis.com/v0/b/asy095--prank-sound.appspot.com/o/Privacy%20Policy%20.html?alt=media&token=8495196c-710d-42bb-9817-96ebf190eefd";

    @Override
    public ActivityPolicyBinding getBinding() {
        return ActivityPolicyBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(linkPolicy);
        if (IsNetWork.haveNetworkConnection(this)) {
            binding.webView.setVisibility(View.VISIBLE);
            binding.noInternet.setVisibility(View.GONE);
            binding.tvNoInternet.setVisibility(View.GONE);

            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.loadUrl(linkPolicy);
        } else {
            binding.webView.setVisibility(View.GONE);
            binding.noInternet.setVisibility(View.VISIBLE);
            binding.tvNoInternet.setVisibility(View.VISIBLE);

            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.loadUrl(linkPolicy);
            binding.ivBack.setOnClickListener(v -> onBack());
        }
        binding.ivBack.setOnClickListener(v -> onBack());
    }
    @Override
    public void bindView() {
    }

    @Override
    public void onBack() {
        finish();
    }
}
