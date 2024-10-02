package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound;

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
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database.SoundModel;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.util.EventTracking;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private final List<SoundModel> soundList;
    private final LayoutInflater inflater;
    Context context;

    public SoundAdapter(Context context, List<SoundModel> soundList) {
        this.context = context;
        this.soundList = soundList;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public SoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new  SoundAdapter.SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundAdapter.SoundViewHolder holder, int position) {
        SoundModel sound = soundList.get(position); 
        holder.imgView.setImageResource(sound.getImgHome());
        holder.name.setText(context.getString(sound.getName()));
        holder.name.setSelected(true);
        holder.itemView.setOnClickListener(v -> {
            EventTracking.logEvent(context, "sound_list_select_sound_click");
            Intent i = new Intent(context, PlaySoundActivity.class);
            i.putExtra("check", 1);
            i.putExtra("soundId", sound.getId());
            i.putExtra("title", sound.getName());
            i.putExtra("img", sound.getImg());
            i.putExtra("sound", sound.getSound());
            i.putExtra("favorite", sound.isFavorite());
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        if (soundList !=null){
            return soundList.size();
        }return 0;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView name;

        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }
}