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

        // Get Intent Data
        Intent intent = getIntent();
        doctorName.setText(intent.getStringExtra("name"));
        doctorSpecialty.setText(intent.getStringExtra("specialty"));
        doctorFee.setText(intent.getStringExtra("fee"));
        doctorRating.setText("★ " + intent.getFloatExtra("rating", 0f));
        doctorImage.setImageResource(intent.getIntExtra("imageResId", R.drawable.doctor1));

        dateValue.setText(intent.getStringExtra("appointment_date"));
        timeValue.setText(intent.getStringExtra("appointment_time"));
        patientName.setText(intent.getStringExtra("patient_name"));
        patientGender.setText(intent.getStringExtra("patient_gender"));
        patientAge.setText(String.valueOf(intent.getIntExtra("patient_age", 0)));

        // Back Button
//        btnBack.setOnClickListener(v -> finish());
//
//        btnBookDoctor.setOnClickListener(v -> {
//            Intent intent = new Intent(this, PaymentActivity.class);
//            intent.putExtra("name", name);
//            intent.putExtra("specialty", specialty);
//            intent.putExtra("fee", fee);
//            intent.putExtra("rating", rating);
//            intent.putExtra("imageResId", imageResId);
//            // Add static data for now, replace with user input later
//            intent.putExtra("appointment_date", "7 July 2025");
//            intent.putExtra("appointment_time", "11:30am");
//            intent.putExtra("patient_name", "Vibhu Ruhela");
//            intent.putExtra("patient_gender", "Male");
//            intent.putExtra("patient_age", 22);
//            startActivity(intent);
//        });

    }
}
