package com.cscorner.healsphere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dashboard2 extends AppCompatActivity {

    private VideoView videoBackground;
    private RecyclerView moodSlider, medicineRecycler;
    private ImageView circleImage1, circleImage2;

    private SharedPreferences preferences;
    private List<Medicine> medicineList;
    private MedicineAdapter medicineAdapter;

    private static final int[] moodImages = {
            R.drawable.mood_very_happy_card,
            R.drawable.mood_happy_card,
            R.drawable.mood_neutral_card,
            R.drawable.mood_sad_card,
            R.drawable.mood_very_sad_card
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        preferences = getSharedPreferences("MoodPrefs", MODE_PRIVATE);

        videoBackground = findViewById(R.id.videoBackground);
        Uri bgVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cloud_background);
        videoBackground.setVideoURI(bgVideoUri);
        videoBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f);
            videoBackground.start();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        scaleSelectedIcon(bottomNav, R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            scaleSelectedIcon(bottomNav, itemId);

            if (itemId == R.id.nav_home) {
                return true;
            }
//            else if (itemId == R.id.nav_appointment) {
//                startActivity(new Intent(this, AppointmentActivity.class));
//                return true;
//            }
            else if (itemId == R.id.nav_mood) {
                startActivity(new Intent(this, MoodTrackerActivity.class));
                return true;
            }
//            else if (itemId == R.id.nav_profile) {
//                startActivity(new Intent(this, ProfileActivity.class));
//                return true;
//            }
            return false;
        });

        moodSlider = findViewById(R.id.moodSlider);
        LinearLayoutManager moodLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        moodSlider.setLayoutManager(moodLayoutManager);

        MoodAdapter moodAdapter = new MoodAdapter(moodImages, selectedMoodResId -> {
            saveMoodToPrefs(selectedMoodResId);
            Toast.makeText(this, "Mood saved!", Toast.LENGTH_SHORT).show();
        });
        moodSlider.setAdapter(moodAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(moodSlider);

        moodSlider.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                scaleCenterCard();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scaleCenterCard();
                }
            }

            private void scaleCenterCard() {
                int centerX = (moodSlider.getLeft() + moodSlider.getRight()) / 2;
                for (int i = 0; i < moodSlider.getChildCount(); i++) {
                    View child = moodSlider.getChildAt(i);
                    if (child != null) {
                        float childCenter = (child.getLeft() + child.getRight()) / 2f;
                        float distanceToCenter = Math.abs(centerX - childCenter);
                        float scale = 1f - (distanceToCenter / (float) moodSlider.getWidth());
                        scale = Math.max(0.85f, Math.min(scale, 1.2f));
                        child.setScaleX(scale);
                        child.setScaleY(scale);
                    }
                }
            }
        });

        medicineRecycler = findViewById(R.id.medicineRecycler);
        medicineList = new ArrayList<>();
        medicineAdapter = new MedicineAdapter(medicineList);
        medicineRecycler.setLayoutManager(new LinearLayoutManager(this));
        medicineRecycler.setAdapter(medicineAdapter);

        TextView addMoreMeds = findViewById(R.id.addMoreMedsText);
        addMoreMeds.setOnClickListener(v -> showAddMedicineDialog());

        TextView bookAppointments = findViewById(R.id.bookAppointmentsText);
        bookAppointments.setOnClickListener(v ->
                Toast.makeText(this, "Book appointment clicked", Toast.LENGTH_SHORT).show()
        );

        circleImage1 = findViewById(R.id.circleImage1);
        circleImage2 = findViewById(R.id.circleImage2);

        circleImage1.setImageResource(R.drawable.ai);
        circleImage2.setImageResource(R.drawable.scanner);

        circleImage1.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard2.this, AI_assistant.class);
            startActivity(intent);
        });
    }

    private void scaleSelectedIcon(BottomNavigationView navView, int selectedItemId) {
        for (int i = 0; i < navView.getMenu().size(); i++) {
            MenuItem item = navView.getMenu().getItem(i);
            View view = navView.findViewById(item.getItemId());
            if (view != null) {
                if (item.getItemId() == selectedItemId) {
                    view.animate().scaleX(1.3f).scaleY(1.3f).setDuration(150).start();
                } else {
                    view.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                }
            }
        }
    }

    private void showAddMedicineDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_medicine, null);
        EditText editMedName = dialogView.findViewById(R.id.editMedName);
        EditText editMedTime = dialogView.findViewById(R.id.editMedTime);

        new AlertDialog.Builder(this)
                .setTitle("Add Medicine")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String medName = editMedName.getText().toString().trim();
                    String medTime = editMedTime.getText().toString().trim();

                    if (!medName.isEmpty() && !medTime.isEmpty()) {
                        medicineList.add(new Medicine(medName, medTime));
                        medicineAdapter.notifyItemInserted(medicineList.size() - 1);
                        Toast.makeText(this, "Added: " + medName + " at " + medTime, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void saveMoodToPrefs(int moodResId) {
        SharedPreferences moodPrefs = getSharedPreferences("MoodData", MODE_PRIVATE);
        SharedPreferences.Editor editor = moodPrefs.edit();

        long timestamp = System.currentTimeMillis();
        int moodValue = convertResToMoodValue(moodResId);
        editor.putInt(String.valueOf(timestamp), moodValue);
        editor.apply();
    }

    private int convertResToMoodValue(int resId) {
        if (resId == R.drawable.mood_very_happy_card) return 4;
        else if (resId == R.drawable.mood_happy_card) return 3;
        else if (resId == R.drawable.mood_neutral_card) return 2;
        else if (resId == R.drawable.mood_sad_card) return 1;
        else if (resId == R.drawable.mood_very_sad_card) return 0;
        else return 2;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoBackground != null && !videoBackground.isPlaying()) {
            videoBackground.start();
        }
    }

    @Override
    protected void onPause() {
        if (videoBackground != null && videoBackground.isPlaying()) {
            videoBackground.pause();
        }
        super.onPause();
    }
}