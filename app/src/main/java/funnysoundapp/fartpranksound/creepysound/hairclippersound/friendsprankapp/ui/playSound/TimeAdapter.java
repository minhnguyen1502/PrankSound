package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.playSound;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    private final List<TimeModel> timeList;
    private final OnItemClickListener onItemClickListener;
    private int selectedPosition = 0;

    public TimeAdapter(List<TimeModel> timeList, OnItemClickListener onItemClickListener) {
        this.timeList = timeList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new TimeViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void resetSelection() {
        selectedPosition = 0;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TimeModel timeModel = timeList.get(position);
        holder.tvTime.setText(timeModel.getTime());
        holder.ivTick.setVisibility(position == selectedPosition ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();

            onItemClickListener.onItemClick(timeModel.getTime());
        });

    }
    public interface OnItemClickListener {
        void onItemClick(String time);
    }
    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ImageView ivTick;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivTick = itemView.findViewById(R.id.img);
        }
    }
}