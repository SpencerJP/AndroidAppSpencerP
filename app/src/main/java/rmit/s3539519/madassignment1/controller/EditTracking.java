package rmit.s3539519.madassignment1.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.TrackingNotValidException;
import rmit.s3539519.madassignment1.model.TrackingService;
import rmit.s3539519.madassignment1.view.EditTrackable;

public class EditTracking extends AppCompatActivity {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private static final long millisecondsInASecond = 1000;
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

    public void addTracking(String title, Date startTime, Date endTime, Date meetingTime) throws TrackingNotValidException {
        // check that startTime is before endTime
        if (startTime.getTime() > endTime.getTime()) {
            throw new TrackingNotValidException("Start time isn't before the end time.");
        }
        // check that title is not empty
        if ((title == null) || (title == "") ) {
            throw new TrackingNotValidException("Nothing in the 'Title' field.");
        }
        // check that meeting time is between the two times
        if ((meetingTime.getTime() < startTime.getTime()) || meetingTime.getTime() > endTime.getTime()) {
            throw new TrackingNotValidException("Meeting time isn't between the start and end times.");
        }
        long halfwayBetweenTimes = (startTime.getTime() + endTime.getTime()) / 2;
        int rangeInSeconds = (int) ((halfwayBetweenTimes - startTime.getTime()) / millisecondsInASecond);

        List<TrackingInfo> trackingInfos = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(new Date(halfwayBetweenTimes), 0 , rangeInSeconds);
        Log.i("testTimes", "yeet " + Integer.toString(trackingInfos.size()));
        // this constructor can throw a TrackingNotValidException as well
        Tracking tracking = new Tracking(Integer.parseInt(trackableId), title, startTime, endTime, meetingTime, trackingInfos);
        Observer.getSingletonInstance(this).addTracking(tracking);
    }
}
