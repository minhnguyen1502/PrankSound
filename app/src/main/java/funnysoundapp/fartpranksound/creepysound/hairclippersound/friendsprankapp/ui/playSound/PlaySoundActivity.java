package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseActivity;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.ActivityPlaySoundBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

import java.util.ArrayList;
import java.util.List;

public class PlaySoundActivity extends BaseActivity<ActivityPlaySoundBinding> implements TimeAdapter.OnItemClickListener {
    int title;
    int img;
    int check;
    boolean isFavorite;
    private int soundId;
    private SoundDatabaseHelper dbHelper;
    private VolumeObserver volumeObserver;
    private boolean isLoop = false;
    private TimeAdapter adapter;

    @Override
    public ActivityPlaySoundBinding getBinding() {
        return ActivityPlaySoundBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        EventTracking.logEvent(PlaySoundActivity.this, "sound_play_view");

        dbHelper = new SoundDatabaseHelper(this);

        Intent i = getIntent();
        soundId = i.getIntExtra("soundId", -1);
        title = i.getIntExtra("title", -1);
        img = i.getIntExtra("img", -1);
        check = i.getIntExtra("check", -1);
        int sound = i.getIntExtra("sound", -1);
        isFavorite = i.getBooleanExtra("favorite", false);

        if (img != -1) {
            binding.ivImg.setImageResource(img);
        } else {
            binding.ivImg.setVisibility(View.INVISIBLE);
        }

        if (title != -1) {
            binding.title.setText(title);
            binding.bgTitle.setText(title);

        } else {
            binding.title.setText("");
            binding.bgTitle.setText("");

        }

        if (sound != -1) {
            mediaPlayer = MediaPlayer.create(this, sound);
            mediaPlayer.setOnCompletionListener(mp -> {
                if (!isLoop) {
                    isPlaying = false;
                    binding.ivPlayPause.setImageResource(R.drawable.ic_play);
                    binding.tvPlayPause.setText(R.string.play);
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.no_sound), Toast.LENGTH_SHORT).show();
        }

        if (isFavorite) {
            binding.favorite.setImageResource(R.drawable.ic_favorite_true);
        } else {
            binding.favorite.setImageResource(R.drawable.ic_favorite_false);
        }

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        binding.volumeSeekBar.setMax(maxVolume);
        binding.volumeSeekBar.setProgress(currVolume);

        volumeObserver = new VolumeObserver(new Handler(Looper.getMainLooper()));
        getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, volumeObserver);

        binding.rcvDropdown.setLayoutManager(new LinearLayoutManager(this));
        List<TimeModel> timeList = getList();
        adapter = new TimeAdapter(timeList, this);
        binding.rcvDropdown.setAdapter(adapter);
    }

    private boolean isPlaying = false;

    @Override
    public void bindView() {

        binding.btnSpinner.setOnClickListener(v -> {
            binding.rcvDropdown.setVisibility(View.VISIBLE);
            binding.ivDownUp.setImageResource(R.drawable.ic_dropdown_up);

        });


        binding.ivBack.setOnClickListener(v -> {
            if (check == 1) {
                EventTracking.logEvent(PlaySoundActivity.this, "sound_play_back_click");
            } else if (check == 2) {
                EventTracking.logEvent(PlaySoundActivity.this, "trending_back_click");
            } else if (check == 3) {
                EventTracking.logEvent(PlaySoundActivity.this, "favorite_back_click");
            }

            onBack();
        });

        binding.volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.btnPlayPause.setOnClickListener(v -> {
            if (check == 1) {
                EventTracking.logEvent(PlaySoundActivity.this, "sound_play_playing_click");
            } else if (check == 2) {
                EventTracking.logEvent(PlaySoundActivity.this, "trending_play_playing_click");

            } else if (check == 3) {
                EventTracking.logEvent(PlaySoundActivity.this, "favorite_play_playing_click");

            }
            isPlaying = !isPlaying;
            if (isPlaying) {
                playSound();
            } else {
                stopSound();
            }
        });

        binding.ivLoop.setOnClickListener(v -> {
            if (check == 1) {
                EventTracking.logEvent(PlaySoundActivity.this, "sound_play_loop_click");
            } else if (check == 2) {
                EventTracking.logEvent(PlaySoundActivity.this, "trending_play_loop_click");
            } else if (check == 3) {
                EventTracking.logEvent(PlaySoundActivity.this, "favorite_play_loop_click");
            }
            isLoop = !isLoop;
            if (isLoop) {
                new CountDownTimer(3000, 100) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.ctlToastTop.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        binding.ctlToastTop.setVisibility(View.GONE);
                        cancel();
                    }
                }.start();
                mediaPlayer.setLooping(true);
                binding.ivLoop.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_loop_on));
            } else {
                stopSound();
                isPlaying = false;
                mediaPlayer.setLooping(false);
                binding.ivLoop.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_loop_off));
            }
        });

        binding.favorite.setOnClickListener(v -> {
            if (check == 1) {
                EventTracking.logEvent(PlaySoundActivity.this, "sound_play_favorite_click");
            } else if (check == 2) {
                EventTracking.logEvent(PlaySoundActivity.this, "trending_play_favorite_click");
            } else if (check == 3) {
                EventTracking.logEvent(PlaySoundActivity.this, "favorite_play_favorite_click");
            }

            isFavorite = !isFavorite;
            dbHelper.updateFavorite(soundId, isFavorite);
            if (isFavorite) {
                binding.favorite.setImageResource(R.drawable.ic_favorite_true);
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.ctlToastBottom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        binding.ctlToastBottom.setVisibility(View.GONE);
                        cancel();
                    }
                }.start();
            } else {
                binding.favorite.setImageResource(R.drawable.ic_favorite_false);
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.ctlToast.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        binding.ctlToast.setVisibility(View.GONE);
                        cancel();
                    }
                }.start();
            }
        });

    }

    private MediaPlayer mediaPlayer;

    @SuppressLint("SetTextI18n")
    private void playSound() {

        binding.ivPlayPause.setImageResource(R.drawable.ic_pause);
        binding.tvPlayPause.setText(R.string.pause);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private void stopSound() {
        binding.ivPlayPause.setImageResource(R.drawable.ic_play);
        binding.tvPlayPause.setText(R.string.play);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    private CountDownTimer countDownTimer;
    private long timeRemaining = 0;

    private void startCountdown(long millis) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                if (isPlaying) {
                    stopSound();
                    isPlaying = false;
                }
                Countdown(millisUntilFinished);
                binding.btnPlayPause.setVisibility(View.INVISIBLE);
                binding.bgBtnPlayPause.setVisibility(View.INVISIBLE);
                binding.llCountdown.setVisibility(View.VISIBLE);
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                playSound();
                isPlaying = true;
                binding.tvTime.setText(getString(R.string.off));
                binding.btnPlayPause.setVisibility(View.VISIBLE);
                binding.bgBtnPlayPause.setVisibility(View.VISIBLE);
                binding.llCountdown.setVisibility(View.INVISIBLE);
                adapter.resetSelection();

            }
        }.start();
    }

    private void Countdown(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        @SuppressLint("DefaultLocale") String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        binding.tvCountdown.setText(timeFormatted);
    }

    private long convertToMillis(String time) {
        switch (time) {
            case "5s":
                return 5000;
            case "10s":
                return 10000;
            case "15s":
                return 15000;
            case "30s":
                return 30000;
            case "1m":
                return 60000;
            case "2m":
                return 120000;
            default:
                return 0;
        }
    }

    private List<TimeModel> getList() {
        List<TimeModel> list = new ArrayList<>();
        list.add(new TimeModel(getString(R.string.off)));
        list.add(new TimeModel("5s"));
        list.add(new TimeModel("10s"));
        list.add(new TimeModel("15s"));
        list.add(new TimeModel("30s"));
        list.add(new TimeModel("1m"));
        list.add(new TimeModel("2m"));
        return list;
    }

    int currVolume;
    private AudioManager audioManager;

    @Override
    public void onItemClick(String time) {
        binding.rcvDropdown.setVisibility(View.GONE);
        binding.ivDownUp.setImageResource(R.drawable.ic_dropdown_down);

        long millis = convertToMillis(time);
        if (millis > 0) {
            startCountdown(millis);
            binding.tvTime.setText(time);
        } else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            binding.tvTime.setText(getString(R.string.off));
            binding.llCountdown.setVisibility(View.INVISIBLE);
            binding.btnPlayPause.setVisibility(View.VISIBLE);
            binding.bgBtnPlayPause.setVisibility(View.VISIBLE);

        }
    }

    private class VolumeObserver extends ContentObserver {

        VolumeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            binding.volumeSeekBar.setProgress(currVolume);

        }
    }

    private boolean wasPlaying = false;
    private boolean isTimerPaused = false;

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                wasPlaying = true;
                mediaPlayer.pause();
            } else {
                wasPlaying = false;
            }
        }
        if (countDownTimer != null) {
            if (isTimerPaused) {
                // Resume the timer
                startCountdown(timeRemaining);
                isTimerPaused = false;
            } else {
                // Pause the timer
                countDownTimer.cancel();
                isTimerPaused = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && wasPlaying) {
            mediaPlayer.start();
        }
        if (countDownTimer != null) {
            if (isTimerPaused) {
                // Resume the timer
                startCountdown(timeRemaining);
                isTimerPaused = false;
            } else {
                // Pause the timer
                countDownTimer.cancel();
                isTimerPaused = true;
            }
        }
    }

    @Override
    public void onBack() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (volumeObserver != null) {
            getContentResolver().unregisterContentObserver(volumeObserver);
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}