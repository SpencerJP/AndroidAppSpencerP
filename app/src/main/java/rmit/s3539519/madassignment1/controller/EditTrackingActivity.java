package rmit.s3539519.madassignment1.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.TrackingNotValidException;
import rmit.s3539519.madassignment1.model.TrackingService;
import rmit.s3539519.madassignment1.view.EditTrackable;
import rmit.s3539519.madassignment1.view.GeoTrackerSpinnerAdapter;

public class EditTrackingActivity extends AppCompatActivity {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private static final String EXTRA_EDIT_BOOL = "s3539519_EditMode";
    private static final long MILLISECONDS_IN_A_MINUTE = 60000;
    private String id;
    private EditTrackable editTrackableView;
    private GeoTrackerSpinnerAdapter startTimeSpinnerAdapter;
    private GeoTrackerSpinnerAdapter endTimeSpinnerAdapter;
    private GeoTrackerSpinnerAdapter meetingTimeSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        // get parsed ID from intent, the selected trackable
        Intent intent = getIntent();
        id = intent.getStringExtra(EXTRA_TRACKABLE_ID);

        editTrackableView = new EditTrackable(this);

        // time spinners
        Spinner startTimeSpinner = findViewById(R.id.startTimeSpinner);
        Spinner endTimeSpinner = findViewById(R.id.endTimeSpinner);
        Spinner meetingTimeSpinner = findViewById(R.id.meetingTimeSpinner);
        // adapters
        startTimeSpinnerAdapter = new GeoTrackerSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, Observer.getSingletonInstance(this).extractTimeList());
        endTimeSpinnerAdapter = new GeoTrackerSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, Observer.getSingletonInstance(this).extractTimeList());
        meetingTimeSpinnerAdapter = new GeoTrackerSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, Observer.getSingletonInstance(this).extractTimeList());
        startTimeSpinner.setAdapter(startTimeSpinnerAdapter);
        endTimeSpinner.setAdapter(endTimeSpinnerAdapter);
        meetingTimeSpinner.setAdapter(meetingTimeSpinnerAdapter);
        // check if we're editing
        if(intent.getBooleanExtra(EXTRA_EDIT_BOOL, false)) {
            Tracking t = Observer.getSingletonInstance(this).getTrackingById(Integer.parseInt(id));
            // set title to current title
            editTrackableView.getTitle().setText(t.getTitle());

            // set spinners to their respective times too
            int spinnerPosition = startTimeSpinnerAdapter.getPosition(t.getStartTimeTwelveHourFormat());
            startTimeSpinner.setSelection(spinnerPosition);
            spinnerPosition = endTimeSpinnerAdapter.getPosition(t.getEndTimeTwelveHourFormat());
            endTimeSpinner.setSelection(spinnerPosition);
            spinnerPosition = meetingTimeSpinnerAdapter.getPosition(t.getMeetTimeTwelveHourFormat());
            meetingTimeSpinner.setSelection(spinnerPosition);
        }
    }

    public void addTracking(String title, Date startTime, Date endTime, Date meetingTime) throws TrackingNotValidException {
        // have to precheck a few things here before going to the model, because getTrackingInfoForTimeRange specifies strange parameters
        // check that startTime is before endTime and not equal
        if ((startTime.getTime() > endTime.getTime()) || (startTime.getTime() == endTime.getTime()) ) {
            throw new TrackingNotValidException("Start time isn't before the end time.");
        }
        // check that title is not empty
        if ((title.equals(null)) || (title.equals("")) ) {
            throw new TrackingNotValidException("Nothing in the 'Title' field.");
        }
        // check that meeting time is between the two times
        if ((meetingTime.getTime() < startTime.getTime()) || meetingTime.getTime() > endTime.getTime()) {
            throw new TrackingNotValidException("Meeting time isn't between the start and end times.");
        }
        long halfwayBetweenTimes = (startTime.getTime() + endTime.getTime()) / 2;
        int minutes = (int) ((endTime.getTime() - startTime.getTime() ) / MILLISECONDS_IN_A_MINUTE);

        List<TrackingInfo> trackingInfos = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(new Date(halfwayBetweenTimes), minutes , 0);

        // this constructor can throw a TrackingNotValidException as well
        Tracking tracking = new Tracking(Integer.parseInt(id), title, startTime, endTime, meetingTime, trackingInfos);
        Observer.getSingletonInstance(this).addTracking(tracking);
    }
}
