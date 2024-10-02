package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language;

import androidx.recyclerview.widget.LinearLayoutManager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityLanguageBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.adapter.LanguageAdapter;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.language.model.LanguageModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.main.MainActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {

    List<LanguageModel> listLanguage;
    String codeLang;

    @Override
    public ActivityLanguageBinding getBinding() {
        return ActivityLanguageBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        initData();
        codeLang = Locale.getDefault().getLanguage();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageAdapter languageAdapter = new LanguageAdapter(listLanguage, code -> codeLang = code, this);


        languageAdapter.setCheck(SystemUtil.getPreLanguage(getBaseContext()));

        binding.rcvLang.setLayoutManager(linearLayoutManager);
        binding.rcvLang.setAdapter(languageAdapter);
    }

    @Override
    public void bindView() {
        binding.ivChoose.setOnClickListener(view -> {
            SystemUtil.saveLocale(getBaseContext(), codeLang);
            startNextActivity(MainActivity.class, null);
            finishAffinity();
        });

        binding.ivBack.setOnClickListener(v -> onBack());
    }

    @Override
    public void onBack() {
        finishThisActivity();
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
