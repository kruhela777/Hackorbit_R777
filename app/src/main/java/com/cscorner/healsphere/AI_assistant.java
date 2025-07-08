package com.cscorner.healsphere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AI_assistant extends AppCompatActivity {

    private VideoView aurVideo;
    private ImageView aiImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant);

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
        aurVideo = findViewById(R.id.aurVideo);
        aiImage = findViewById(R.id.aiImage); // Still bind if needed for image visuals

        // Play aura video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura);
        aurVideo.setVideoURI(videoUri);
        aurVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f); // mute video
            aurVideo.start();
        });

        // Automatically move to next screen after 1000ms
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(AI_assistant.this, AI_assistant2.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 1500);
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
