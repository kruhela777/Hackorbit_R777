package com.cscorner.healsphere;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MoodTrackerActivity extends AppCompatActivity {

    private LineChart moodChart;
    private static final String MOOD_HISTORY_PREF = "MoodHistoryPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        moodChart = findViewById(R.id.moodChart);

        List<Entry> moodEntries = getMoodEntriesFromPrefs();

        LineDataSet dataSet = new LineDataSet(moodEntries, "Mood Over Time");
        dataSet.setColor(Color.parseColor("#2196F3"));
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setCircleColor(Color.YELLOW);
        dataSet.setCircleRadius(6f);
        dataSet.setLineWidth(3f);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        moodChart.setData(lineData);
        moodChart.getDescription().setEnabled(false);
        moodChart.invalidate();

        Button backButton = findViewById(R.id.btnBackToDashboard);
        backButton.setOnClickListener(v -> finish());
    }

    private List<Entry> getMoodEntriesFromPrefs() {
        SharedPreferences prefs = getSharedPreferences(MOOD_HISTORY_PREF, MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        List<Long> sortedTimestamps = new ArrayList<>();
        for (String key : allEntries.keySet()) {
            try {
                sortedTimestamps.add(Long.parseLong(key));
            } catch (NumberFormatException ignored) {}
        }

        Collections.sort(sortedTimestamps);

        List<Entry> entries = new ArrayList<>();
        int x = 1;
        for (Long timestamp : sortedTimestamps) {
            int moodValue = prefs.getInt(String.valueOf(timestamp), -1);
            if (moodValue >= 0) {
                entries.add(new Entry(x++, moodValue));
            }
        }

        return entries;
    }
}
