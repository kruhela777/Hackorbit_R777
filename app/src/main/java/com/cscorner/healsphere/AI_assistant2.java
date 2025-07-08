package com.cscorner.healsphere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AI_assistant2 extends AppCompatActivity {

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
        aiImage = findViewById(R.id.aiImage);

        // Set and play aura background video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura);
        aurVideo.setVideoURI(videoUri);
        aurVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f); // Mute video
            aurVideo.start();
        });

//        // Delay 1000ms → Move to next activity
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(AI_assistant.this, AI_assistant2.class); // Replace with actual target activity
//            startActivity(intent);
//            finish(); // Optional: finish this screen
//        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aurVideo != null) aurVideo.start();
    }

    @Override
    protected void onPause() {
        if (aurVideo != null && aurVideo.isPlaying()) aurVideo.pause();
        super.onPause();
    }
}
