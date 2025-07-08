package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen2 extends AppCompatActivity {

    private static final int DELAY_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);

        ImageView square = findViewById(R.id.squareImage);

        // Rotate square to diamond shape (45 degrees)
        RotateAnimation rotate = new RotateAnimation(
                0, 45,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(DELAY_MS);
        rotate.setFillAfter(true); // keep rotated state
        square.startAnimation(rotate);

        // Move to SplashScreen3 after 300ms
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen2.this, SplashScreen3.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, DELAY_MS);
    }
}
