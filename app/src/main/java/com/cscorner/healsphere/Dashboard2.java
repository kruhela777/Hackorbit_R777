package com.cscorner.healsphere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
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

        // Immersive fullscreen mode
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

        // Mood slider setup
        moodSlider = findViewById(R.id.moodSlider);
        LinearLayoutManager moodLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        moodSlider.setLayoutManager(moodLayoutManager);

        MoodAdapter moodAdapter = new MoodAdapter(moodImages, selectedMoodResId -> {
            preferences.edit().putInt("todayMood", selectedMoodResId).apply();
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

        // Medicine Section
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

        // Bottom circular images
        circleImage1 = findViewById(R.id.circleImage1);
        circleImage2 = findViewById(R.id.circleImage2);

        circleImage1.setImageResource(R.drawable.ai);
        circleImage2.setImageResource(R.drawable.scanner);

        circleImage1.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard2.this, AI_assistant.class); // or VoiceScreen.class if renamed
            startActivity(intent);
        });
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
