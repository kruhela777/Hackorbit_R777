package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class doctor_booking3 extends AppCompatActivity {

    ImageView btnBack, doctorImage;
    TextView doctorName, doctorSpecialty, doctorRating, doctorFee;
    TextView dateValue, timeValue, patientName, patientGender, patientAge;
    TextView btnBookDoctor; // This was missing in your code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_booking3);

        // Bind Views
        btnBack = findViewById(R.id.btnBack);
        doctorImage = findViewById(R.id.doctorImage);
        doctorName = findViewById(R.id.doctorName);
        doctorSpecialty = findViewById(R.id.doctorSpecialty);
        doctorRating = findViewById(R.id.doctorRating);
        doctorFee = findViewById(R.id.doctorFee);

        dateValue = findViewById(R.id.textDateValue);
        timeValue = findViewById(R.id.textTimeValue);
        patientName = findViewById(R.id.textPatientName);
        patientGender = findViewById(R.id.textPatientGender);
        patientAge = findViewById(R.id.textPatientAge);

        btnBookDoctor = findViewById(R.id.btnPayNow); // Assuming Pay Now is your action button

        // Get Intent Data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String specialty = intent.getStringExtra("specialty");
        String fee = intent.getStringExtra("fee");
        float rating = intent.getFloatExtra("rating", 0f);
        int imageResId = intent.getIntExtra("imageResId", R.drawable.doctor1);

        String appointmentDate = intent.getStringExtra("appointment_date");
        String appointmentTime = intent.getStringExtra("appointment_time");
        String patientNameStr = intent.getStringExtra("patient_name");
        String patientGenderStr = intent.getStringExtra("patient_gender");
        int patientAgeVal = intent.getIntExtra("patient_age", 0);

        // Set data to views
        doctorName.setText(name);
        doctorSpecialty.setText(specialty);
        doctorFee.setText(fee);
        doctorRating.setText("★ " + rating);
        doctorImage.setImageResource(imageResId);

        dateValue.setText(appointmentDate);
        timeValue.setText(appointmentTime);
        patientName.setText(patientNameStr);
        patientGender.setText(patientGenderStr);
        patientAge.setText(String.valueOf(patientAgeVal));

        btnBookDoctor.setOnClickListener(v -> {
            Intent payIntent = new Intent(doctor_booking3.this, PaymentActivity.class);
            payIntent.putExtra("name", name);
            payIntent.putExtra("specialty", specialty);
            payIntent.putExtra("fee", fee);
            payIntent.putExtra("rating", rating);
            payIntent.putExtra("imageResId", imageResId);
            payIntent.putExtra("appointment_date", appointmentDate);
            payIntent.putExtra("appointment_time", appointmentTime);
            payIntent.putExtra("patient_name", patientNameStr);
            payIntent.putExtra("patient_gender", patientGenderStr);
            payIntent.putExtra("patient_age", patientAgeVal);
            startActivity(payIntent);
        });

        // Back Button
        btnBack.setOnClickListener(v -> finish());

        // Book Doctor button action
        btnBookDoctor.setOnClickListener(v -> {
            Intent payIntent = new Intent(doctor_booking3.this, doctor_booking2.class);
            payIntent.putExtra("name", name);
            payIntent.putExtra("specialty", specialty);
            payIntent.putExtra("fee", fee);
            payIntent.putExtra("rating", rating);
            payIntent.putExtra("imageResId", imageResId);
            payIntent.putExtra("appointment_date", appointmentDate);
            payIntent.putExtra("appointment_time", appointmentTime);
            payIntent.putExtra("patient_name", patientNameStr);
            payIntent.putExtra("patient_gender", patientGenderStr);
            payIntent.putExtra("patient_age", patientAgeVal);
            startActivity(payIntent);
        });
    }
}
