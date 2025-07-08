package com.cscorner.healsphere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    private final int[] moodImages;
    private final OnMoodSelectedListener listener;
    private int selectedPosition = -1;

    // Interface to handle mood selection
    public interface OnMoodSelectedListener {
        void onMoodSelected(int moodResId);
    }

    public MoodAdapter(int[] moodImages, OnMoodSelectedListener listener) {
        this.moodImages = moodImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mood_slider_item, parent, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        holder.moodImage.setImageResource(moodImages[position]);

        // Animate scale based on selected position
        if (position == selectedPosition) {
            holder.moodImage.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).start();
        } else {
            holder.moodImage.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
        }

        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition(); // safer
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onMoodSelected(moodImages[selectedPosition]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moodImages.length;
    }

    static class MoodViewHolder extends RecyclerView.ViewHolder {
        ImageView moodImage;

        MoodViewHolder(@NonNull View itemView) {
            super(itemView);
            moodImage = itemView.findViewById(R.id.moodImage);
        }
    }
}
