package com.cscorner.healsphere;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class doctor_booking1 extends AppCompatActivity {

    private RecyclerView recyclerTopDoctors;
    private DoctorAdapter doctorAdapter;
    private List<DoctorModel> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_booking1);

        recyclerTopDoctors = findViewById(R.id.recyclerTopDoctors);
        recyclerTopDoctors.setLayoutManager(new LinearLayoutManager(this));

        doctorList = new ArrayList<>();
        loadDummyDoctors();

        doctorAdapter = new DoctorAdapter(this, doctorList);
        recyclerTopDoctors.setAdapter(doctorAdapter);
    }

    private void loadDummyDoctors() {
        doctorList.add(new DoctorModel("Dr. Kartik Verma", "Cardiologist", "₹1200/session", 4.8f, R.drawable.doctor1));
        doctorList.add(new DoctorModel("Dr. Vikas Mehta", "Dermatologist", "₹900/session", 4.6f, R.drawable.doctor2));
        doctorList.add(new DoctorModel("Dr. Simran Kaur", "Pulmonologist", "₹1100/session", 4.7f, R.drawable.doctor3));
//        doctorList.add(new DoctorModel("Dr. Ayesha Khan", "Neurologist", "₹1500/session", 4.9f, R.drawable.doctor4));
//        doctorList.add(new DoctorModel("Dr. Pooja Sharma", "Ophthalmologist", "₹950/session", 4.5f, R.drawable.doctor5));
//        doctorList.add(new DoctorModel("Dr. Anuj Patel", "Dentist", "₹800/session", 4.3f, R.drawable.doctor6));
    }
}