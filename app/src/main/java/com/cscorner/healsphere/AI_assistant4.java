package com.cscorner.healsphere;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AI_assistant4 extends AppCompatActivity {

    private VideoView aiVideoBackground;
    private EditText chatInput;
    private TextView aiResponses;
    private ImageView uploadIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant4);

        aiVideoBackground = findViewById(R.id.aiVideoBackground);
        chatInput = findViewById(R.id.chatInput);
        aiResponses = findViewById(R.id.aiResponses);
        uploadIcon = findViewById(R.id.uploadIcon);

        playLoopingVideo();

        uploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = chatInput.getText().toString().trim();
                if (!userMessage.isEmpty()) {
                    getAIResponse(userMessage);
                    chatInput.setText("");
                }
            }
        });
    }

    private void playLoopingVideo() {
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura1); // Put your video in res/raw
        aiVideoBackground.setVideoURI(videoUri);
        aiVideoBackground.start();

        aiVideoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0f, 0f); // optional: mute video
            }
        });
    }

    private void getAIResponse(String prompt) {
        // TODO: Replace this stub with your actual AI backend integration (e.g. Flask, Node.js, Gemini API)
        // This is where you'd call your API via Retrofit, Volley, or OkHttp.

        // Example stub:
        aiResponses.setText("Thinking...\n");

        // Fake delay and response:
        aiResponses.postDelayed(() -> {
            String fakeResponse = "AI says: " + prompt;
            aiResponses.setText(fakeResponse);
        }, 1000);
    }
}
