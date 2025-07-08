package com.cscorner.healsphere;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AI_assistant4 extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private VideoView aiVideoBackground;
    private EditText chatInput;
    private TextView aiResponses;
    private ImageView uploadIcon, backButton, micIcon, galleryIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant4);

        // Request microphone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

        // Bind views
        aiVideoBackground = findViewById(R.id.aiVideoBackground);
        chatInput = findViewById(R.id.chatInput);
        aiResponses = findViewById(R.id.aiResponses);
        uploadIcon = findViewById(R.id.uploadIcon);
        backButton = findViewById(R.id.backButton);
        micIcon = findViewById(R.id.micIcon);
        galleryIcon = findViewById(R.id.gallery); // Make sure this ID exists in XML

        playLoopingVideo();

        // Text input send
        uploadIcon.setOnClickListener(v -> {
            String userMessage = chatInput.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                getAIResponse(userMessage);
                chatInput.setText("");
            } else {
                Toast.makeText(this, "Please type something", Toast.LENGTH_SHORT).show();
            }
        });

        // Back navigation
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(AI_assistant4.this, AI_assistant3.class));
            finish();
        });

        // Mic input
        micIcon.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
            } catch (Exception e) {
                Toast.makeText(this, "Voice input not supported", Toast.LENGTH_SHORT).show();
            }
        });

        // Gallery image picker
        galleryIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });
    }

    private void playLoopingVideo() {
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aura1);
        aiVideoBackground.setVideoURI(videoUri);
        aiVideoBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f);
            aiVideoBackground.start();
        });
    }

    private void getAIResponse(String prompt) {
        aiResponses.setText("Thinking...");

        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);

            jsonBody.put("messages", messages);
        } catch (Exception e) {
            aiResponses.setText("Error preparing AI request.");
            return;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer YOUR_API_KEY") // 🔁 Replace with your API key
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> aiResponses.setText("Failed to connect to AI."));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String resStr = response.body().string();
                        JSONObject json = new JSONObject(resStr);
                        String aiReply = json.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");

                        runOnUiThread(() -> aiResponses.setText(aiReply.trim()));
                    } catch (Exception e) {
                        runOnUiThread(() -> aiResponses.setText("Error parsing AI response."));
                    }
                } else {
                    runOnUiThread(() -> aiResponses.setText("API Error: " + response.message()));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                chatInput.setText(results.get(0));
            } else {
                Toast.makeText(this, "Didn't catch that, try again", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Toast.makeText(this, "Image selected: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
                // Optionally upload or display the image
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aiVideoBackground != null && !aiVideoBackground.isPlaying()) {
            aiVideoBackground.start();
        }
    }

    @Override
    protected void onPause() {
        if (aiVideoBackground != null && aiVideoBackground.isPlaying()) {
            aiVideoBackground.pause();
        }
        super.onPause();
    }
}
