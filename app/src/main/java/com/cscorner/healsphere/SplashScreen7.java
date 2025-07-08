package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SplashScreen7 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen7);

        ImageView boyGif = findViewById(R.id.boyGif);
        ImageView chatGif = findViewById(R.id.chatGif);
        ImageView micGif = findViewById(R.id.micGif);
        ImageView nextArrow = findViewById(R.id.nextArrow);

        // Load visuals
        Glide.with(this).load(R.drawable.speaking).into(boyGif);
        Glide.with(this).asGif().load(R.drawable.chat).into(chatGif);
        Glide.with(this).asGif().load(R.drawable.mic).into(micGif);

        nextArrow.setOnClickListener(v -> {
            startActivity(new Intent(SplashScreen7.this, SplashScreen8.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
