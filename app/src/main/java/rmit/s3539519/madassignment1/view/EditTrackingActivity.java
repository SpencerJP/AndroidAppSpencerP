package rmit.s3539519.madassignment1.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.services.TrackingService;
import rmit.s3539519.madassignment1.view.viewmodels.EditTrackableModel;

public class EditTrackingActivity extends AppCompatActivity {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private static final String EXTRA_EDIT_BOOL = "s3539519_EditMode";
    private static final long MILLISECONDS_IN_A_MINUTE = 60000;
    private String id;
    private EditTrackableModel editTrackableModelView;
    private EditText meetingTimeField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        // get parsed ID from intent, the selected trackable
        Intent intent = getIntent();
        id = intent.getStringExtra(EXTRA_TRACKABLE_ID);

        editTrackableModelView = new EditTrackableModel(this);

        meetingTimeField = findViewById(R.id.meetingTimeField);
        // check if we're editing
        if(intent.getBooleanExtra(EXTRA_EDIT_BOOL, false)) {
            Tracking t = Observer.getSingletonInstance(this).getTrackingById(Integer.parseInt(getId()));
            // set title to current title
            editTrackableModelView.getTitle().setText(t.getTitle());
            // set field to editable current meeting time
            meetingTimeField.setText(t.getMeetTimeTwelveHourFormat(), TextView.BufferType.EDITABLE);
        }

        // populate the valid time info at the bottom
        Integer.parseInt(getId());
        editTrackableModelView.getValidTimeList().setText(TrackingService.getSingletonInstance(this).getValidMeetingTimesAsString(Integer.parseInt(getId())));
    }

    public String getId() {
        return id;
    }
}
