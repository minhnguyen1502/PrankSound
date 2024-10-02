package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.FragmentTrendingBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound.PlaySoundActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

public class TrendingFragment extends BaseFragment<FragmentTrendingBinding> {

    Context context;
    SoundDatabaseHelper dbHelper;
    boolean check = false;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public FragmentTrendingBinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentTrendingBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        EventTracking.logEvent(context, "trending_view");

        dbHelper = new SoundDatabaseHelper(context);

        binding.btnFart.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_fart3_click");

            onclick(15);
        });
        binding.btnToilet.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_toilet5_click");

            onclick(62);
        });
        binding.btnSheep.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_sheep_click");

            onclick(2);
        });
        binding.btnLaughing.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_laughing4_click");

            onclick(88);
        });
        binding.btnHair.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_clipper1_click");

            onclick(67);
        });
        binding.btnClapping.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_clapping2_click");

            onclick(41);
        });
        binding.btnDoor.setOnClickListener(v -> {
            EventTracking.logEvent(context, "trending_bell7_click");

            onclick(55);
        });
    }

    private void onclick(int i) {
        if (!check){
            check = true;
            SoundModel sound = dbHelper.getSoundById(i);
            if (sound != null) {
                Intent intent = new Intent(context, PlaySoundActivity.class);
                intent.putExtra("check", 2);
                intent.putExtra("soundId", sound.getId());
                intent.putExtra("title", sound.getName());
                intent.putExtra("img", sound.getImg());
                intent.putExtra("sound", sound.getSound());
                intent.putExtra("favorite", sound.isFavorite());
                startActivity(intent);
            }
        }

    }

    @Override
    public void bindView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        check = false;
    }
}