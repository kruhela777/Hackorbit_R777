package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen9); // ✅ Make sure this XML exists

        ImageView boyMeditating = findViewById(R.id.boyMeditating);
        ImageView planetsGif = findViewById(R.id.planetsGif);
        ImageView nextArrow = findViewById(R.id.nextArrow);

        // Load static image
        Glide.with(this)
                .load(R.drawable.meditate)
                .into(boyMeditating);

        // Load animated GIF
        Glide.with(this)
                .asGif()
                .load(R.drawable.planets)
                .into(planetsGif);

        // Navigate to SplashScreen10
        nextArrow.setOnClickListener(v -> {
            startActivity(new Intent(SplashScreen9.this, SplashScreen10.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
