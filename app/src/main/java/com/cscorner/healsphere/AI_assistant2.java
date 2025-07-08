package com.cscorner.healsphere;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AI_assistant2 extends AppCompatActivity {

    private VideoView aurVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant2); // ✅ Correct layout file!

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

        // Set and play aura background video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura);
        aurVideo.setVideoURI(videoUri);
        aurVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f); // mute video
            aurVideo.start();
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
