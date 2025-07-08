package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {

    private static final int DELAY_MS = 3000; // 500 milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LogoActivity.this, SplashScreen1.class);
            startActivity(intent);
            finish(); // Optional: close logo screen
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, DELAY_MS);
    }
}
