package com.cscorner.healsphere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    Context context;
    List<DoctorModel> doctorList;

    public DoctorAdapter(Context context, List<DoctorModel> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_card, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        DoctorModel doctor = doctorList.get(position);

        holder.tvName.setText(doctor.getName());
        holder.tvSpecialty.setText(doctor.getSpecialty());
        holder.tvFee.setText(doctor.getFee());
        holder.tvRating.setText("★ " + doctor.getRating());
        holder.imgDoctor.setImageResource(doctor.getImageResId());

        // Set click listener to navigate to booking_doctor2
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, doctor_booking2.class);
            intent.putExtra("name", doctor.getName());
            intent.putExtra("specialty", doctor.getSpecialty());
            intent.putExtra("fee", doctor.getFee());
            intent.putExtra("rating", doctor.getRating());
            intent.putExtra("imageResId", doctor.getImageResId());
            context.startActivity(intent);
        });

        // Load slot views (7 days)
        holder.layoutSlots.removeAllViews();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.ENGLISH);

        for (int i = 0; i < 7; i++) {
            LinearLayout slotContainer = new LinearLayout(context);
            slotContainer.setOrientation(LinearLayout.VERTICAL);
            slotContainer.setGravity(Gravity.CENTER);
            slotContainer.setBackgroundResource(R.drawable.bg_slot_day);

            int sizeInDp = 48;
            int sizeInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, sizeInDp, context.getResources().getDisplayMetrics());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 12, 0);
            slotContainer.setLayoutParams(params);

            TextView dayView = new TextView(context);
            dayView.setText(dayFormat.format(calendar.getTime()).toUpperCase());
            dayView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            dayView.setTextSize(10f);
            dayView.setTypeface(null, Typeface.BOLD);
            dayView.setGravity(Gravity.CENTER);

            TextView dateView = new TextView(context);
            dateView.setText(dateFormat.format(calendar.getTime()));
            dateView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            dateView.setTextSize(14f);
            dateView.setTypeface(null, Typeface.BOLD);
            dateView.setGravity(Gravity.CENTER);

            slotContainer.addView(dayView);
            slotContainer.addView(dateView);
            holder.layoutSlots.addView(slotContainer);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDoctor, ivLike;
        TextView tvName, tvSpecialty, tvFee, tvRating;
        LinearLayout layoutSlots;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpecialty = itemView.findViewById(R.id.tvSpecialty);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvRating = itemView.findViewById(R.id.tvRating);
            layoutSlots = itemView.findViewById(R.id.layoutSlots);
        }
    }
}
