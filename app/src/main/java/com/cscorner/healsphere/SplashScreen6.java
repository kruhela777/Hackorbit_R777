package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen6 extends AppCompatActivity {

    private static final int DELAY_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen6);

        ImageView boyGif = findViewById(R.id.boyGif);
        ImageView chatGif = findViewById(R.id.chatGif);
        ImageView micGif = findViewById(R.id.micGif);

        Glide.with(this)
                .load(R.drawable.speaking)
                .into(boyGif);


        Glide.with(this)
                .asGif()
                .load(R.drawable.chat)
                .into(chatGif);

        Glide.with(this)
                .load(R.drawable.mic)
                .into(micGif);

        // Delay and move to SplashScreen7
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen6.this, SplashScreen7.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, DELAY_MS);
    }
}
