package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityMainBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    public ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        EventTracking.logEvent(MainActivity.this, "home_view");

        SoundDatabaseHelper dbHelper = new SoundDatabaseHelper(this);
        if (!dbHelper.areSoundsPresent()) {
            dbHelper.addSounds(getAnimalSounds());
            dbHelper.addSounds(getFartSounds());
            dbHelper.addSounds(getBreakingSounds());
            dbHelper.addSounds(getGunSounds());
            dbHelper.addSounds(getClappingSounds());
            dbHelper.addSounds(getDoorSounds());
            dbHelper.addSounds(getToiletSounds());
            dbHelper.addSounds(getHairSounds());
            dbHelper.addSounds(getScarySounds());
            dbHelper.addSounds(getLaughingSounds());
        }

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), getLifecycle());
        binding.viewPager.setAdapter(mainAdapter);
        binding.viewPager.setUserInputEnabled(false);

    }

    @Override
    public void bindView() {

        binding.btnSound.setOnClickListener(view -> {
            EventTracking.logEvent(MainActivity.this, "home_sound_click");

            setPage(0);
        });
        binding.btnTrending.setOnClickListener(view -> {
            EventTracking.logEvent(MainActivity.this, "home_trending_click");

            setPage(1);
        });
        binding.btnFavorite.setOnClickListener(view -> {
            EventTracking.logEvent(MainActivity.this, "home_favorite_click");

            setPage(2);
        });
        binding.btnSetting.setOnClickListener(view -> {
            EventTracking.logEvent(MainActivity.this, "home_setting_click");

            setPage(3);
        });

        binding.txtFavorite.setSelected(true);
        binding.txtTrending.setSelected(true);
        binding.txtSound.setSelected(true);
        binding.txtSetting.setSelected(true);
        binding.title.setSelected(true);
        binding.bgTitle.setSelected(true);
    }

    private boolean isShow = false;

    @Override
    public void onBack() {
        if (!isShow) {
            confirmQuitApp();
        }
    }

    private void confirmQuitApp() {
        isShow = true;
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_exit_app);

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RelativeLayout cancel = dialog.findViewById(R.id.btn_cancel_quit_app);
        RelativeLayout quit = dialog.findViewById(R.id.btn_quit_app);
        quit.setOnClickListener(v -> {
            finishAffinity();
            dialog.dismiss();
            isShow = false;
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            isShow = false;
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    @SuppressLint("SetTextI18n")
    public void setPage(int pos) {
        binding.viewPager.setCurrentItem(pos, false);

        switch (pos) {
            case 0:
                binding.bgTitle.setVisibility(View.INVISIBLE);
                binding.title.setVisibility(View.INVISIBLE);
                binding.ivFrankSound.setVisibility(View.VISIBLE);
                binding.imgFavorite.setImageResource(R.drawable.ic_favorite_ns);
                binding.imgSound.setImageResource(R.drawable.ic_sound_s);
                binding.imgSetting.setImageResource(R.drawable.ic_setting_ns);
                binding.imgTrending.setImageResource(R.drawable.ic_trending_ns);
                break;
            case 1:
                binding.title.setText(R.string.sound_trending);
                binding.bgTitle.setText(R.string.sound_trending);
                binding.bgTitle.setVisibility(View.VISIBLE);
                binding.title.setVisibility(View.VISIBLE);
                binding.ivFrankSound.setVisibility(View.GONE);
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite_ns);
                binding.imgSound.setImageResource(R.drawable.ic_sound_ns);
                binding.imgSetting.setImageResource(R.drawable.ic_setting_ns);
                binding.imgTrending.setImageResource(R.drawable.ic_trending_s);
                break;
            case 2:
                binding.title.setText(R.string.favorite_sound);
                binding.bgTitle.setText(R.string.favorite_sound);
                binding.ivFrankSound.setVisibility(View.GONE);
                binding.bgTitle.setVisibility(View.VISIBLE);
                binding.title.setVisibility(View.VISIBLE);
                binding.imgFavorite.setImageResource(R.drawable.ic_favorite_s);
                binding.imgSound.setImageResource(R.drawable.ic_sound_ns);
                binding.imgSetting.setImageResource(R.drawable.ic_setting_ns);
                binding.imgTrending.setImageResource(R.drawable.ic_trending_ns);
                break;
            case 3:
                binding.title.setText(getString(R.string.settings));
                binding.bgTitle.setText(getString(R.string.settings));
                binding.ivFrankSound.setVisibility(View.GONE);
                binding.bgTitle.setVisibility(View.VISIBLE);
                binding.title.setVisibility(View.VISIBLE);
                binding.imgFavorite.setImageResource(R.drawable.ic_favorite_ns);
                binding.imgSound.setImageResource(R.drawable.ic_sound_ns);
                binding.imgSetting.setImageResource(R.drawable.ic_setting_s);
                binding.imgTrending.setImageResource(R.drawable.ic_trending_ns);
                break;
        }

    }

    private List<SoundModel> getAnimalSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(1, R.drawable.img_bird,     R.drawable.img_animal_bird,    R.drawable.img_bird,       R.string.bird, R.raw.animal_bird, false, "Animal"));
        sounds.add(new SoundModel(2, R.drawable.img_sheep,    R.drawable.img_animal_sheep,   R.drawable.img_sheep,        R.string.sheep  , R.raw.animal_sheep, false, "Animal"));
        sounds.add(new SoundModel(3, R.drawable.img_tiger,    R.drawable.img_animal_tiger,   R.drawable.img_tiger,          R.string.tiger  , R.raw.animal_tiger, false, "Animal"));
        sounds.add(new SoundModel(4, R.drawable.img_cat,      R.drawable.img_animal_cat,     R.drawable.img_cat,            R.string.cat  , R.raw.animal_cat, false, "Animal"));
        sounds.add(new SoundModel(5, R.drawable.img_dog,      R.drawable.img_animal_dog,     R.drawable.img_dog,          R.string.dog  , R.raw.animal_dog, false, "Animal"));
        sounds.add(new SoundModel(6, R.drawable.img_elephant, R.drawable.img_animal_elephant,R.drawable.img_elephant,   R.string.elephant  , R.raw.animal_elephant, false, "Animal"));
        sounds.add(new SoundModel(7, R.drawable.img_lion,     R.drawable.img_annimal_lion,   R.drawable.img_lion,       R.string.lion  , R.raw.aniaml_lion, false, "Animal"));
        sounds.add(new SoundModel(8, R.drawable.img_wolf,     R.drawable.img_animal_wolf,    R.drawable.img_wolf,        R.string.wolf  , R.raw.animal_wolf, false, "Animal"));
        sounds.add(new SoundModel(9, R.drawable.img_rooster,  R.drawable.img_animal_rooster, R.drawable.img_rooster,   R.string.rooster  , R.raw.animal_rooster, false, "Animal"));
        sounds.add(new SoundModel(10, R.drawable.img_duck,    R.drawable.img_animal_duck,     R.drawable.img_duck,          R.string.duck  , R.raw.animal_duck, false, "Animal"));
        sounds.add(new SoundModel(11, R.drawable.img_pig,     R.drawable.img_animal_pig,      R.drawable.img_pig,        R.string.pig  , R.raw.animal_pig, false, "Animal"));
        sounds.add(new SoundModel(12, R.drawable.img_frog,    R.drawable.img_animal_frog,     R.drawable.img_frog,    R.string.frog  , R.raw.animal_frog, false, "Animal"));
        return sounds;
    }

    private List<SoundModel> getFartSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(13, R.drawable.img_fart, R.drawable.img_fart_1, R.drawable.img_fart, R.string.fart_1  , R.raw.fart_1, false, "Fart"));
        sounds.add(new SoundModel(14, R.drawable.img_fart, R.drawable.img_fart_2, R.drawable.img_fart, R.string.fart_2  , R.raw.fart_2, false, "Fart"));
        sounds.add(new SoundModel(15, R.drawable.img_fart, R.drawable.img_fart_3, R.drawable.img_fart, R.string.fart_3  , R.raw.fart_3, false, "Fart"));
        sounds.add(new SoundModel(16, R.drawable.img_fart, R.drawable.img_fart_4, R.drawable.img_fart, R.string.fart_4  , R.raw.fart_4, false, "Fart"));
        sounds.add(new SoundModel(17, R.drawable.img_fart, R.drawable.img_fart_5, R.drawable.img_fart, R.string.fart_5  , R.raw.fart_5, false, "Fart"));
        sounds.add(new SoundModel(18, R.drawable.img_fart, R.drawable.img_fart_6, R.drawable.img_fart, R.string.fart_6  , R.raw.fart_6, false, "Fart"));
        sounds.add(new SoundModel(19, R.drawable.img_fart, R.drawable.img_fart_7, R.drawable.img_fart, R.string.fart_7  , R.raw.fart_7, false, "Fart"));
        sounds.add(new SoundModel(20, R.drawable.img_fart, R.drawable.img_fart_8, R.drawable.img_fart, R.string.fart_8  , R.raw.fart_8, false, "Fart"));
        sounds.add(new SoundModel(21, R.drawable.img_fart, R.drawable.img_fart_9, R.drawable.img_fart, R.string.fart_9  , R.raw.fart_9, false, "Fart"));
        return sounds;
    }

    private List<SoundModel> getBreakingSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(22, R.drawable.img_breaking, R.drawable.img_breaking_1, R.drawable.img_breaking, R.string.breaking_1  , R.raw.glass_1, false, "Breaking"));
        sounds.add(new SoundModel(23, R.drawable.img_breaking, R.drawable.img_breaking_2, R.drawable.img_breaking, R.string.breaking_2  , R.raw.glass_2, false, "Breaking"));
        sounds.add(new SoundModel(24, R.drawable.img_breaking, R.drawable.img_breaking_3, R.drawable.img_breaking, R.string.breaking_3  , R.raw.glass_3, false, "Breaking"));
        sounds.add(new SoundModel(25, R.drawable.img_breaking, R.drawable.img_breaking_4, R.drawable.img_breaking, R.string.breaking_4  , R.raw.glass_4, false, "Breaking"));
        sounds.add(new SoundModel(26, R.drawable.img_breaking, R.drawable.img_breaking_5, R.drawable.img_breaking, R.string.breaking_5  , R.raw.glass_5, false, "Breaking"));
        sounds.add(new SoundModel(27, R.drawable.img_breaking, R.drawable.img_breaking_6, R.drawable.img_breaking, R.string.breaking_6  , R.raw.glass_6, false, "Breaking"));
        sounds.add(new SoundModel(28, R.drawable.img_breaking, R.drawable.img_breaking_7, R.drawable.img_breaking, R.string.breaking_7  , R.raw.glass_7, false, "Breaking"));
        sounds.add(new SoundModel(29, R.drawable.img_breaking, R.drawable.img_breaking_8, R.drawable.img_breaking, R.string.breaking_8  , R.raw.glass_8, false, "Breaking"));
        sounds.add(new SoundModel(30, R.drawable.img_breaking, R.drawable.img_breaking_9, R.drawable.img_breaking, R.string.breaking_9  , R.raw.glass_9, false, "Breaking"));
        return sounds;
    }

    private List<SoundModel> getGunSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(31, R.drawable.img_gun, R.drawable.img_gun_1,R.drawable.img_gun,  R.string.gun_1  , R.raw.gun_1, false, "Gun"));
        sounds.add(new SoundModel(32, R.drawable.img_gun, R.drawable.img_gun_2,R.drawable.img_gun,  R.string.gun_2  , R.raw.gun_2, false, "Gun"));
        sounds.add(new SoundModel(33, R.drawable.img_gun, R.drawable.img_gun_3,R.drawable.img_gun,  R.string.gun_3  , R.raw.gun_3, false, "Gun"));
        sounds.add(new SoundModel(34, R.drawable.img_gun, R.drawable.img_gun_4,R.drawable.img_gun,  R.string.gun_4  , R.raw.gun_4, false, "Gun"));
        sounds.add(new SoundModel(35, R.drawable.img_gun, R.drawable.img_gun_5,R.drawable.img_gun,  R.string.gun_5  , R.raw.gun_5, false, "Gun"));
        sounds.add(new SoundModel(36, R.drawable.img_gun, R.drawable.img_gun_6,R.drawable.img_gun,  R.string.gun_6  , R.raw.gun_6, false, "Gun"));
        sounds.add(new SoundModel(37, R.drawable.img_gun, R.drawable.img_gun_7,R.drawable.img_gun,  R.string.gun_7  , R.raw.gun_7, false, "Gun"));
        sounds.add(new SoundModel(38, R.drawable.img_gun, R.drawable.img_gun_8,R.drawable.img_gun,  R.string.gun_8  , R.raw.gun_8, false, "Gun"));
        sounds.add(new SoundModel(39, R.drawable.img_gun, R.drawable.img_gun_9,R.drawable.img_gun,  R.string.gun_9  , R.raw.gun_9, false, "Gun"));
        return sounds;
    }

    private List<SoundModel> getClappingSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(40, R.drawable.img_clapping_play, R.drawable.img_clapping_1,R.drawable.img_clapping, R.string.clapping_1  , R.raw.clapp_1, false, "Clapping"));
        sounds.add(new SoundModel(41, R.drawable.img_clapping_play, R.drawable.img_clapping_2,R.drawable.img_clapping, R.string.clapping_2  , R.raw.clapp_2, false, "Clapping"));
        sounds.add(new SoundModel(42, R.drawable.img_clapping_play, R.drawable.img_clapping_3,R.drawable.img_clapping, R.string.clapping_3  , R.raw.clapp_3, false, "Clapping"));
        sounds.add(new SoundModel(43, R.drawable.img_clapping_play, R.drawable.img_clapping_4,R.drawable.img_clapping, R.string.clapping_4  , R.raw.clapp_4, false, "Clapping"));
        sounds.add(new SoundModel(44, R.drawable.img_clapping_play, R.drawable.img_clapping_5,R.drawable.img_clapping, R.string.clapping_5  , R.raw.clapp_5, false, "Clapping"));
        sounds.add(new SoundModel(45, R.drawable.img_clapping_play, R.drawable.img_clapping_6,R.drawable.img_clapping, R.string.clapping_6  , R.raw.clapp_6, false, "Clapping"));
        sounds.add(new SoundModel(46, R.drawable.img_clapping_play, R.drawable.img_clapping_7,R.drawable.img_clapping, R.string.clapping_7  , R.raw.clapp_7, false, "Clapping"));
        sounds.add(new SoundModel(47, R.drawable.img_clapping_play, R.drawable.img_clapping_1,R.drawable.img_clapping, R.string.clapping_8  , R.raw.clapp_8, false, "Clapping"));
        sounds.add(new SoundModel(48, R.drawable.img_clapping_play, R.drawable.img_clapping_9,R.drawable.img_clapping, R.string.clapping_9  , R.raw.clapp_9, false, "Clapping"));
        return sounds;
    }

    private List<SoundModel> getDoorSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(49, R.drawable.img_bell_play, R.drawable.img_door_1, R.drawable.img_bell, R.string.door_bell_1  , R.raw.door_1, false, "Door"));
        sounds.add(new SoundModel(50, R.drawable.img_bell_play, R.drawable.img_door_2, R.drawable.img_bell, R.string.door_bell_2  , R.raw.door_2, false, "Door"));
        sounds.add(new SoundModel(51, R.drawable.img_bell_play, R.drawable.img_door_3, R.drawable.img_bell, R.string.door_bell_3  , R.raw.door_3, false, "Door"));
        sounds.add(new SoundModel(52, R.drawable.img_bell_play, R.drawable.img_door_4, R.drawable.img_bell, R.string.door_bell_4  , R.raw.door_4, false, "Door"));
        sounds.add(new SoundModel(52, R.drawable.img_bell_play, R.drawable.img_door_5, R.drawable.img_bell, R.string.door_bell_5  , R.raw.door_5, false, "Door"));
        sounds.add(new SoundModel(54, R.drawable.img_bell_play, R.drawable.img_door_6, R.drawable.img_bell, R.string.door_bell_6  , R.raw.door_6, false, "Door"));
        sounds.add(new SoundModel(55, R.drawable.img_bell_play, R.drawable.img_door_7, R.drawable.img_bell, R.string.door_bell_7  , R.raw.door_7, false, "Door"));
        sounds.add(new SoundModel(56, R.drawable.img_bell_play, R.drawable.img_door_1, R.drawable.img_bell, R.string.door_bell_8  , R.raw.door_8, false, "Door"));
        sounds.add(new SoundModel(57, R.drawable.img_bell_play, R.drawable.img_door_9, R.drawable.img_bell, R.string.door_bell_9  , R.raw.door_9, false, "Door"));
        return sounds;
    }

    private List<SoundModel> getToiletSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(58, R.drawable.img_toilet, R.drawable.img_toilet_1, R.drawable.img_toilet, R.string.toilet_flushing_1  , R.raw.toilet_1, false, "Toilet"));
        sounds.add(new SoundModel(59, R.drawable.img_toilet, R.drawable.img_toilet_2, R.drawable.img_toilet, R.string.toilet_flushing_2  , R.raw.toilet_2, false, "Toilet"));
        sounds.add(new SoundModel(60, R.drawable.img_toilet, R.drawable.img_toilet_3, R.drawable.img_toilet, R.string.toilet_flushing_3  , R.raw.toilet_3, false, "Toilet"));
        sounds.add(new SoundModel(61, R.drawable.img_toilet, R.drawable.img_toilet_4, R.drawable.img_toilet, R.string.toilet_flushing_4  , R.raw.toilet_4, false, "Toilet"));
        sounds.add(new SoundModel(62, R.drawable.img_toilet, R.drawable.img_toilet_5, R.drawable.img_toilet, R.string.toilet_flushing_5  , R.raw.toilet_5, false, "Toilet"));
        sounds.add(new SoundModel(63, R.drawable.img_toilet, R.drawable.img_toilet_6, R.drawable.img_toilet, R.string.toilet_flushing_6  , R.raw.toilet_6, false, "Toilet"));
        sounds.add(new SoundModel(64, R.drawable.img_toilet, R.drawable.img_toilet_7, R.drawable.img_toilet, R.string.toilet_flushing_7  , R.raw.toilet_7, false, "Toilet"));
        sounds.add(new SoundModel(65, R.drawable.img_toilet, R.drawable.img_toilet_1, R.drawable.img_toilet, R.string.toilet_flushing_8  , R.raw.toilet_8, false, "Toilet"));
        sounds.add(new SoundModel(66, R.drawable.img_toilet, R.drawable.img_toilet_9, R.drawable.img_toilet, R.string.toilet_flushing_9  , R.raw.toilet_9, false, "Toilet"));
        return sounds;
    }

    private List<SoundModel> getHairSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(67, R.drawable.img_hair, R.drawable.img_hair_1, R.drawable.img_hair,  R.string.hair_clipper_1  , R.raw.hair_1, false, "Hair"));
        sounds.add(new SoundModel(68, R.drawable.img_hair, R.drawable.img_hair_2, R.drawable.img_hair,  R.string.hair_clipper_2  , R.raw.hair_2, false, "Hair"));
        sounds.add(new SoundModel(69, R.drawable.img_hair, R.drawable.img_hair_3, R.drawable.img_hair,  R.string.hair_clipper_3  , R.raw.hair_3, false, "Hair"));
        sounds.add(new SoundModel(70, R.drawable.img_hair, R.drawable.img_hair_4, R.drawable.img_hair,  R.string.hair_clipper_4  , R.raw.hair_4, false, "Hair"));
        sounds.add(new SoundModel(71, R.drawable.img_hair, R.drawable.img_hair_5, R.drawable.img_hair,  R.string.hair_clipper_5  , R.raw.hair_5, false, "Hair"));
        sounds.add(new SoundModel(72, R.drawable.img_hair, R.drawable.img_hair_6, R.drawable.img_hair,  R.string.hair_clipper_6  , R.raw.hair_6, false, "Hair"));
        sounds.add(new SoundModel(73, R.drawable.img_hair, R.drawable.img_hair_7, R.drawable.img_hair,  R.string.hair_clipper_7  , R.raw.hair_7, false, "Hair"));
        sounds.add(new SoundModel(74, R.drawable.img_hair, R.drawable.img_hair_1, R.drawable.img_hair,  R.string.hair_clipper_8  , R.raw.hair_8, false, "Hair"));
        sounds.add(new SoundModel(75, R.drawable.img_hair, R.drawable.img_hair_9, R.drawable.img_hair,  R.string.hair_clipper_9  , R.raw.hair_9, false, "Hair"));
        return sounds;
    }

    private List<SoundModel> getScarySounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(76, R.drawable.img_scary, R.drawable.img_scary_1,R.drawable.img_scary, R.string.scary_1  , R.raw.scary_1, false, "Scary"));
        sounds.add(new SoundModel(77, R.drawable.img_scary, R.drawable.img_scary_2,R.drawable.img_scary, R.string.scary_2  , R.raw.scary_2, false, "Scary"));
        sounds.add(new SoundModel(78, R.drawable.img_scary, R.drawable.img_scary_3,R.drawable.img_scary, R.string.scary_3  , R.raw.scary_3, false, "Scary"));
        sounds.add(new SoundModel(79, R.drawable.img_scary, R.drawable.img_scary_4,R.drawable.img_scary, R.string.scary_4  , R.raw.scary_4, false, "Scary"));
        sounds.add(new SoundModel(80, R.drawable.img_scary, R.drawable.img_scary_5,R.drawable.img_scary, R.string.scary_5  , R.raw.scary_5, false, "Scary"));
        sounds.add(new SoundModel(81, R.drawable.img_scary, R.drawable.img_scary_6,R.drawable.img_scary, R.string.scary_6  , R.raw.scary_6, false, "Scary"));
        sounds.add(new SoundModel(82, R.drawable.img_scary, R.drawable.img_scary_7,R.drawable.img_scary, R.string.scary_7  , R.raw.scary_7, false, "Scary"));
        sounds.add(new SoundModel(83, R.drawable.img_scary, R.drawable.img_scary_1,R.drawable.img_scary, R.string.scary_8  , R.raw.scary_8, false, "Scary"));
        sounds.add(new SoundModel(84, R.drawable.img_scary, R.drawable.img_scary_9,R.drawable.img_scary, R.string.scary_9  , R.raw.scary_9, false, "Scary"));
        return sounds;
    }

    private List<SoundModel> getLaughingSounds() {
        List<SoundModel> sounds = new ArrayList<>();
        sounds.add(new SoundModel(85, R.drawable.img_laughing, R.drawable.img_laughing_1, R.drawable.img_laughing, R.string.laughing_1  , R.raw.laughing_1, false, "Laughing"));
        sounds.add(new SoundModel(86, R.drawable.img_laughing, R.drawable.img_laughing_2, R.drawable.img_laughing, R.string.laughing_2  , R.raw.laughing_2, false, "Laughing"));
        sounds.add(new SoundModel(87, R.drawable.img_laughing, R.drawable.img_laughing_3, R.drawable.img_laughing, R.string.laughing_3  , R.raw.laughing_3, false, "Laughing"));
        sounds.add(new SoundModel(88, R.drawable.img_laughing, R.drawable.img_laughing_4, R.drawable.img_laughing, R.string.laughing_4  , R.raw.laughing_4, false, "Laughing"));
        sounds.add(new SoundModel(89, R.drawable.img_laughing, R.drawable.img_laughing_5, R.drawable.img_laughing, R.string.laughing_5  , R.raw.laughing_5, false, "Laughing"));
        sounds.add(new SoundModel(90, R.drawable.img_laughing, R.drawable.img_laughing_6, R.drawable.img_laughing, R.string.laughing_6  , R.raw.laughing_6, false, "Laughing"));
        sounds.add(new SoundModel(91, R.drawable.img_laughing, R.drawable.img_laughing_7, R.drawable.img_laughing, R.string.laughing_7  , R.raw.laughing_7, false, "Laughing"));
        sounds.add(new SoundModel(92, R.drawable.img_laughing, R.drawable.img_laughing_1, R.drawable.img_laughing, R.string.laughing_8  , R.raw.laughing_8, false, "Laughing"));
        sounds.add(new SoundModel(93, R.drawable.img_laughing, R.drawable.img_laughing_9, R.drawable.img_laughing, R.string.laughing_9  , R.raw.laughing_9, false, "Laughing"));
        return sounds;
    }
}
