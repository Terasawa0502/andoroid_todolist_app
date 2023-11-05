package com.example.todolistapp.ui.calendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolistapp.R;

public class NewCalenderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        CalendarView calendarView = findViewById(R.id.calender);
        calendarView.setOnDateChangeListener((calendar, year, month, date) -> {
            String message = year + "/" + (month + 1) + "/" + date;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });

        // 戻るボタンを押したときは戻る
        findViewById(R.id.calender_back_btn).setOnClickListener(view -> {
            finish();
        });

    }
}
