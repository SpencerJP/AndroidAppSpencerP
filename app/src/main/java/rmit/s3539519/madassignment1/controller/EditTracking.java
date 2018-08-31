package rmit.s3539519.madassignment1.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.EditTrackable;

public class EditTracking extends AppCompatActivity {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_TrackableViewHolder";
    private String trackableId;
    private EditTrackable editTrackableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        // get parsed ID from intent, the selected trackable
        Intent intent = getIntent();
        trackableId = intent.getStringExtra(EXTRA_TRACKABLE_ID);
        editTrackableView = new EditTrackable(this);

    }

    private boolean addTracking(String title, String startTime, String endTime, String meetingTime) {

        return false;
    }
}
