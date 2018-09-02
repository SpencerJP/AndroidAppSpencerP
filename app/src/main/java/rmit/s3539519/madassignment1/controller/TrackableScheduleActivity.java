package rmit.s3539519.madassignment1.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;

public class TrackableScheduleActivity extends AppCompatActivity {
    private static final String EXTRA_SCHEDULE_INFORMATION = "s3539519_ScheduleInformation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackable_schedule);
        Intent intent = getIntent();
        String schedule = intent.getStringExtra(EXTRA_SCHEDULE_INFORMATION);
        TextView scheduleView = findViewById(R.id.scheduleView);
        scheduleView.setText(schedule);
    }
}
