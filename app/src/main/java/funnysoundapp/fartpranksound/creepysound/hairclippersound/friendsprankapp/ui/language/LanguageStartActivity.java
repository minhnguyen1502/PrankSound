package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityLanguageStartBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.intro.IntroActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.adapter.LanguageStartAdapter;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.model.LanguageModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.SystemUtil;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class LanguageStartActivity extends BaseActivity<ActivityLanguageStartBinding> {

    List<LanguageModel> listLanguage;
    String codeLang = "";

    @Override
    public ActivityLanguageStartBinding getBinding() {
        return ActivityLanguageStartBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        EventTracking.logEvent(this, "language_fo_open");
        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageStartAdapter languageStartAdapter = new LanguageStartAdapter(listLanguage, code -> codeLang = code, this);

        binding.rcvLangStart.setLayoutManager(linearLayoutManager);
        binding.rcvLangStart.setAdapter(languageStartAdapter);
    }

    @Override
    public void bindView() {
        binding.ivCheck.setOnClickListener(view -> {
            EventTracking.logEvent(this, "language_fo_save_click");

            if (codeLang.isEmpty()) {
                Toast.makeText(LanguageStartActivity.this, R.string.please_choose_language, Toast.LENGTH_SHORT).show();
            } else {
                SystemUtil.saveLocale(getBaseContext(), codeLang);
                Utils.setLanguageSelected(true);
                startNextActivity(IntroActivity.class, null);
                finishAffinity();
            }
        });

    }

    @Override
    public void onBack() {
        finishAffinity();
    }

    private void initData() {
        listLanguage = new ArrayList<>();
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));
        listLanguage.add(new LanguageModel("German", "de"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("China", "zh"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));
        listLanguage.add(new LanguageModel("Indonesia", "in"));

    }
}
