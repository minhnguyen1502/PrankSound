package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.FragmentSoundBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound.SoundActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

public class SoundFragment extends BaseFragment<FragmentSoundBinding> {
    Context context;
    boolean check = false;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public FragmentSoundBinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentSoundBinding.inflate(inflater, container, false);

    }

    @Override
    public void initView() {
        EventTracking.logEvent(context, "sound_view");

        binding.btnAnimal.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_animal_click");

            onClick("animal");
        });
        binding.btnFart.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_fart_click");

            onClick("fart");
        });
        binding.btnLaughing.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_view");

            onClick("laughing");
        });
        binding.btnScary.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_scary_click");

            onClick("scary");
        });
        binding.btnHair.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_hair_clipper_click");

            onClick("hair");
        });
        binding.btnToilet.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_toilet_click");

            onClick("toilet");
        });
        binding.btnDoor.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_door_click");

            onClick("door");
        });
        binding.btnClapping.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_clapping_click");

            onClick("clapping");
        });
        binding.btnGun.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_gun_click");

            onClick("gun");
        });
        binding.btnBreaking.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_breaking_click");

            onClick("breaking");
        });

        binding.txtAnimal.setSelected(true);
        binding.txtBreaking.setSelected(true);
        binding.txtClapping.setSelected(true);
        binding.txtDoor.setSelected(true);
        binding.txtFart.setSelected(true);
        binding.txtScary.setSelected(true);
        binding.txtToilet.setSelected(true);
        binding.txtGun.setSelected(true);
        binding.txtHair.setSelected(true);
        binding.txtLaughing.setSelected(true);
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        check = false;
    }

    private void onClick(String type) {
        if (!check){
            check = true;
            Intent i = new Intent(context, SoundActivity.class);
            i.putExtra("type", type);
            startActivity(i);
        }

    }
}