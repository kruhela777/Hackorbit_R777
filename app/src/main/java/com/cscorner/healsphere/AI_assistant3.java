package com.cscorner.healsphere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AI_assistant3 extends AppCompatActivity {

    private VideoView aiVideoBackground;
    private ImageView micButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant3);

        // Fullscreen immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        // Bind views
        aiVideoBackground = findViewById(R.id.aiVideoBackground);
        micButton = findViewById(R.id.micButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Play the looping muted video background
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura1);
        aiVideoBackground.setVideoURI(videoUri);
        aiVideoBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f);
            aiVideoBackground.start();
        });

        // Optional: mic button action (to start voice input or next screen)
        micButton.setOnClickListener(v -> {
            // TODO: Implement voice recording or navigation
        });

        // Cancel button action (e.g. go back)
        cancelButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aiVideoBackground != null && !aiVideoBackground.isPlaying()) {
            aiVideoBackground.start();
        }
    }

    @Override
    protected void onPause() {
        if (aiVideoBackground != null && aiVideoBackground.isPlaying()) {
            aiVideoBackground.pause();
        }
        super.onPause();
    }
}
