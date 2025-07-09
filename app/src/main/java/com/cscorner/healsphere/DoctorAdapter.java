package com.cscorner.healsphere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        holder.imgDoctor.setImageResource(doctor.getImageRes());

        // Dynamic calendar slots for 7 days
        holder.layoutSlots.removeAllViews();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");

        for (int i = 0; i < 7; i++) {
            TextView dateView = new TextView(context);
            dateView.setText(sdf.format(calendar.getTime()));
            dateView.setTextColor(context.getResources().getColor(android.R.color.white));
            dateView.setBackgroundResource(R.drawable.bg_slot_day);
            dateView.setPadding(20, 10, 20, 10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 16, 0);
            dateView.setLayoutParams(params);

            holder.layoutSlots.addView(dateView);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {

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
