package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;  // if you're using Button
import androidx.appcompat.app.AppCompatActivity;

public class doctor_booking2 extends AppCompatActivity {

    ImageView doctorImage, btnBack;
    TextView doctorName, doctorSpecialty, doctorFee, doctorRating;
    TextView btnPayNow; // If using TextView as button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_booking2);

        // Bind views
        doctorImage = findViewById(R.id.doctorImage);
        btnBack = findViewById(R.id.btnBack);
        doctorName = findViewById(R.id.doctorName);
        doctorSpecialty = findViewById(R.id.doctorSpecialty);
        doctorFee = findViewById(R.id.doctorFee);
        doctorRating = findViewById(R.id.doctorRating);
        btnPayNow = findViewById(R.id.btnPayNow); // Make sure this ID exists

        // Get intent data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String specialty = intent.getStringExtra("specialty");
        String fee = intent.getStringExtra("fee");
        float rating = intent.getFloatExtra("rating", 0f);
        int imageResId = intent.getIntExtra("imageResId", R.drawable.doctor1);

        // Set data to views
        if (name != null) doctorName.setText(name);
        if (specialty != null) doctorSpecialty.setText(specialty);
        if (fee != null) doctorFee.setText(fee);
        doctorImage.setImageResource(imageResId);
        if (doctorRating != null) doctorRating.setText("★ " + rating);

        // Back button
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(doctor_booking2.this, doctor_booking1.class));
            finish();
        });

        // Book doctor / Pay Now button
        btnPayNow.setOnClickListener(v -> {
            Intent bookIntent = new Intent(doctor_booking2.this, doctor_booking3.class);
            // You can pass doctor info here too, if needed:
            bookIntent.putExtra("name", name);
            bookIntent.putExtra("specialty", specialty);
            bookIntent.putExtra("fee", fee);
            bookIntent.putExtra("rating", rating);
            bookIntent.putExtra("imageResId", imageResId);
            startActivity(bookIntent);
        });
    }
}
