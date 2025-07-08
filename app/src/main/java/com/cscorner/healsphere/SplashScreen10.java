package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen10 extends AppCompatActivity {

    private static final int DELAY_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen10);

        ImageView doctorGif = findViewById(R.id.doctorGif);

        Glide.with(this)
                .asGif()
                .load(R.drawable.doctor_gif) // Replace with your actual GIF
                .into(doctorGif);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen10.this, SplashScreen11.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, DELAY_MS);
    }
}
