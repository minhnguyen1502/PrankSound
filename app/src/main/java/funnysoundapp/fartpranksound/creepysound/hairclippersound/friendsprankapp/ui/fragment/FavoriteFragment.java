package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.FragmentFavoriteBinding;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.favorite.FavoriteAdapter;

import java.util.List;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding> implements FavoriteAdapter.Notification {
    Context context;
    FavoriteAdapter adapter;
    SoundDatabaseHelper dbHelper;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public FragmentFavoriteBinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentFavoriteBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        dbHelper= new SoundDatabaseHelper(context);

        binding.rcvFavorite.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FavoriteAdapter(context, null, this);
        binding.rcvFavorite.setAdapter(adapter);

        loadFavoriteSounds();

    }
    private void loadFavoriteSounds() {
        List<SoundModel> favoriteSounds = dbHelper.getFavoriteSounds();

        if (favoriteSounds == null || favoriteSounds.isEmpty()) {
            binding.ctlNoFavorite.setVisibility(View.VISIBLE);
        } else {
            binding.ctlNoFavorite.setVisibility(View.GONE);
        }

        adapter.updateData(favoriteSounds);
    }
    @Override
    public void bindView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteSounds();

    }

    @Override
    public void unFavorite() {
        // Show a toast message
        new CountDownTimer(1000,100){

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
        // Reload favorite sounds
        loadFavoriteSounds();
    }
}