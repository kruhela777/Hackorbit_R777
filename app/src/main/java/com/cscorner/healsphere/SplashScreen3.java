package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen3 extends AppCompatActivity {

    private static final int DELAY_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen3);

        ImageView circle = findViewById(R.id.circleImage);

        new Handler().postDelayed(() -> {
            circle.setVisibility(View.GONE); // Instantly hide

            startActivity(new Intent(SplashScreen3.this, SplashScreen4.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }, DELAY_MS);
    }
}
