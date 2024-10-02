package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundDatabaseHelper;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound.PlaySoundActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.SoundViewHolder> {

    private List<SoundModel> soundList;
    private final LayoutInflater inflater;
    private final Notification notificationListener;
    Context context;

    public FavoriteAdapter(Context context, List<SoundModel> soundList, Notification notificationListener) {
        this.context = context;
        this.soundList = soundList;
        this.inflater = LayoutInflater.from(context);
        this.notificationListener = notificationListener;
    }

    @NonNull
    @Override
    public FavoriteAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        return new FavoriteAdapter.SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.SoundViewHolder holder, int position) {
        SoundDatabaseHelper dbHelper = new SoundDatabaseHelper(holder.itemView.getContext());

        SoundModel sound = soundList.get(position);
        holder.imgView.setImageResource(sound.getImgFavorite());
        holder.name.setText(context.getString(sound.getName()));
        holder.favorite.setImageResource(sound.isFavorite() ? R.drawable.ic_favorite_true : R.drawable.ic_favorite_false);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, PlaySoundActivity.class);
            i.putExtra("check", 3);
            i.putExtra("soundId", sound.getId());
            i.putExtra("title", sound.getName());
            i.putExtra("img", sound.getImg());
            i.putExtra("sound", sound.getSound());
            i.putExtra("favorite", sound.isFavorite());
            context.startActivity(i);
        });
        holder.favorite.setOnClickListener(v -> {
            boolean newFavoriteStatus = !sound.isFavorite();
            sound.setFavorite(newFavoriteStatus);

            dbHelper.updateFavorite(sound.getId(), newFavoriteStatus);

            if (!newFavoriteStatus) {
                soundList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, soundList.size());
                if (notificationListener != null) {
                    notificationListener.unFavorite();
                }
            } else {
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (soundList !=null){
            return soundList.size();

        }return 0;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<SoundModel> newSounds) {
        this.soundList = newSounds;
        notifyDataSetChanged();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        ImageView favorite;
        TextView name;

        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.iv_image);
            favorite = itemView.findViewById(R.id.iv_favorite);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface Notification{
        void unFavorite();
    }
}