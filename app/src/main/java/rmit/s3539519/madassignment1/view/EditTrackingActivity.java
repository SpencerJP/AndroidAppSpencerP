package rmit.s3539519.madassignment1.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.TrackingNotValidException;
import rmit.s3539519.madassignment1.model.TrackingService;
import rmit.s3539519.madassignment1.view.viewmodels.EditTrackable;

public class EditTrackingActivity extends AppCompatActivity {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private static final String EXTRA_EDIT_BOOL = "s3539519_EditMode";
    private static final long MILLISECONDS_IN_A_MINUTE = 60000;
    private String id;
    private EditTrackable editTrackableView;
    private EditText meetingTimeField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        // get parsed ID from intent, the selected trackable
        Intent intent = getIntent();
        id = intent.getStringExtra(EXTRA_TRACKABLE_ID);

        editTrackableView = new EditTrackable(this);

        meetingTimeField = findViewById(R.id.meetingTimeField);
        // check if we're editing
        if(intent.getBooleanExtra(EXTRA_EDIT_BOOL, false)) {
            Tracking t = Observer.getSingletonInstance(this).getTrackingById(Integer.parseInt(id));
            // set title to current title
            editTrackableView.getTitle().setText(t.getTitle());
            // set field to editable current meeting time
            meetingTimeField.setText(t.getMeetTimeTwelveHourFormat(), TextView.BufferType.EDITABLE);
        }

        // populate the valid time info at the bottom
        Integer.parseInt(id);
        editTrackableView.getValidTimeList().setText(TrackingService.getSingletonInstance(this).getValidMeetingTimesAsString(Integer.parseInt(id)));
    }

    public void addTracking(String title, Date meetingTime) throws TrackingNotValidException {

        if ((title.equals(null)) || (title.equals("")) ) {
            throw new TrackingNotValidException("Nothing in the 'Title' field.");
        }
        TrackingInfo period = TrackingService.getSingletonInstance(this).meetingTimeWithinStoppingTime(Integer.parseInt(id), meetingTime);
        if (period == null) {
            throw new TrackingNotValidException("The meeting time is not a valid stopping time for the trackable.");
        }
        Date startTime = period.date;
        Date endTime = new Date(period.date.getTime() + (60000 * period.stopTime));
        Tracking tracking = new Tracking(Integer.parseInt(id), title, startTime, endTime, meetingTime);
        Observer.getSingletonInstance(this).addTracking(tracking);
    }
}
