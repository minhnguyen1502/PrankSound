package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.main;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment.FavoriteFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment.SettingFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment.SoundFragment;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.fragment.TrendingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();

    public MainAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentList.add(new SoundFragment());
        fragmentList.add(new TrendingFragment());
        fragmentList.add(new FavoriteFragment());
        fragmentList.add(new SettingFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

}