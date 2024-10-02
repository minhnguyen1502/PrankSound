package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivitySoundBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

import java.util.List;

public class SoundActivity extends BaseActivity<ActivitySoundBinding> {
    SoundAdapter adapter;
    String type;
    List<SoundModel> animal;
    SoundDatabaseHelper dbHelper;
    @Override
    public ActivitySoundBinding getBinding() {
        return ActivitySoundBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        EventTracking.logEvent(SoundActivity.this, "sound_list_view");

        dbHelper = new SoundDatabaseHelper(this);
        Intent i = getIntent();
        type = i.getStringExtra("type");
        if (type!=null){
            loadSoundsByType(type);
        }else {
            Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
        }
        binding.ivBack.setOnClickListener(v -> {
            EventTracking.logEvent(SoundActivity.this, "sound_list_back_click");

            onBack();
        });
        adapter = new SoundAdapter(this, animal); // Use the same adapter

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvSound.setLayoutManager(layoutManager);
        binding.rcvSound.setAdapter(adapter);

        }

    @Override
    public void bindView() {
    }

    @Override
    public void onBack() {
        finish();
    }
    private void loadSoundsByType(String type) {
        switch (type) {
            case "animal":
                animal = dbHelper.getSoundsByType("Animal");
                binding.bgTitle.setText(R.string.animal);
                binding.title.setText(R.string.animal);
                break;
            case "fart":
                animal = dbHelper.getSoundsByType("Fart");
                binding.bgTitle.setText(R.string.fart);
                binding.title.setText(R.string.fart);
                break;
            case "laughing":
                animal = dbHelper.getSoundsByType("Laughing");
                binding.bgTitle.setText(R.string.laughing);
                binding.title.setText(R.string.laughing);
                break;
            case "scary":
                animal = dbHelper.getSoundsByType("Scary");
                binding.bgTitle.setText(R.string.scary);
                binding.title.setText(R.string.scary);
                break;
            case "door":
                animal = dbHelper.getSoundsByType("Door");
                binding.bgTitle.setText(R.string.door_bell);
                binding.title.setText(R.string.door_bell);
                break;
            case "clapping":
                animal = dbHelper.getSoundsByType("Clapping");
                binding.bgTitle.setText(R.string.clapping);
                binding.title.setText(R.string.clapping);
                break;
            case "gun":
                animal = dbHelper.getSoundsByType("Gun");
                binding.bgTitle.setText(R.string.gun);
                binding.title.setText(R.string.gun);
                break;
            case "breaking":
                animal = dbHelper.getSoundsByType("Breaking");
                binding.bgTitle.setText(R.string.breaking);
                binding.title.setText(R.string.breaking);
                break;
            case "toilet":
                animal = dbHelper.getSoundsByType("Toilet");
                binding.bgTitle.setText(R.string.toilet_flushing);
                binding.title.setText(R.string.toilet_flushing);
                break;
            case "hair":
                animal = dbHelper.getSoundsByType("Hair");
                binding.bgTitle.setText(R.string.hair_clipper);
                binding.title.setText(R.string.hair_clipper);
                break;
        }

        adapter = new SoundAdapter(this, animal); // Use the same adapter
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvSound.setLayoutManager(layoutManager);
        binding.rcvSound.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
       loadSoundsByType(type);
    }
}