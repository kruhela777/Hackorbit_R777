package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen8 extends AppCompatActivity {

    private static final int DELAY_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen8);

        ImageView boyMeditating = findViewById(R.id.boyMeditating);
        ImageView planetsGif = findViewById(R.id.planetsGif);


        Glide.with(this)
                .load(R.drawable.meditate)
                .into(boyMeditating);


        Glide.with(this)
                .asGif()
                .load(R.drawable.planets)
                .into(planetsGif);

        // Delay before navigating to SplashScreen9
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen8.this, SplashScreen9.class));
            finish(); // Finish current screen
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, DELAY_MS);
    }
}
