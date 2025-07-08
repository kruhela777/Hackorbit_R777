package com.cscorner.healsphere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AI_assistant2 extends AppCompatActivity {

    private VideoView aurVideo;
    private LinearLayout getStartedSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant2);

        // Fullscreen immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        // Bind the video view
        aurVideo = findViewById(R.id.auraVideo);

        // Bind Get Started section
        getStartedSection = findViewById(R.id.getStartedSection);

        // Start background video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura);
        aurVideo.setVideoURI(videoUri);
        aurVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f); // mute
            aurVideo.start();
        });

        // 👉 Set click listener on "Get Started"
        getStartedSection.setOnClickListener(v -> {
            Intent intent = new Intent(AI_assistant2.this, AI_assistant3.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // optional
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aurVideo != null && !aurVideo.isPlaying()) {
            aurVideo.start();
        }
    }

    @Override
    protected void onPause() {
        if (aurVideo != null && aurVideo.isPlaying()) {
            aurVideo.pause();
        }
        super.onPause();
    }
}
