package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen11 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen11);

        ImageView doctorGif = findViewById(R.id.doctorGif);
        ImageView nextArrow = findViewById(R.id.nextArrow);

        // Load doctor GIF
        Glide.with(this)
                .asGif()
                .load(R.drawable.doctor_gif)
                .into(doctorGif);

        // Navigate to SplashScreen12
        nextArrow.setOnClickListener(v -> {
            startActivity(new Intent(SplashScreen11.this, Dashboard1.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
