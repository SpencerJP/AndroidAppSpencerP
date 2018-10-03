package rmit.s3539519.madassignment1.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.services.TrackingService;
import rmit.s3539519.madassignment1.view.EditTrackingActivity;
import rmit.s3539519.madassignment1.model.exceptions.TrackingNotValidException;
import rmit.s3539519.madassignment1.view.viewmodels.EditTrackableModel;

public class EditTrackingButtonListener implements View.OnClickListener {

    private EditTrackingActivity context;
    private EditTrackableModel editView;
    public EditTrackingButtonListener(Context context, EditTrackableModel editView) {
        this.context = (EditTrackingActivity) context;
        this.editView = editView;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_tracking_cancel_button) {
            context.finish();
        }
        if (v.getId() == R.id.edit_tracking_save_button) {
            try {
                String title = editView.getTitle().getText().toString();
                Date meetingTime = validateTime(editView.getMeetingTime().getText().toString());

                try {
                    addTracking(title, meetingTime, context.getId());

                    context.finish();
                }
                catch(TrackingNotValidException e) {
                    Toast.makeText(context,e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
            catch(ParseException e) {

            }
        }
    }

    private Date validateTime(String s) throws ParseException {
            // Set the date part to the current date
            Date currentDate = new Date();
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mma");
            s = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + s;
            Date finalTime = formatter.parse(s);
            return finalTime;
    }

    private void addTracking(String title, Date meetingTime, String id) throws TrackingNotValidException {

        if ((title.equals(null)) || (title.equals("")) ) {
            throw new TrackingNotValidException("Nothing in the 'Title' field.");
        }
        TrackingInfo period = TrackingService.getSingletonInstance(context).meetingTimeWithinStoppingTime(Integer.parseInt(id), meetingTime);
        if (period == null) {
            throw new TrackingNotValidException("The meeting time is not a valid stopping time for the trackable.");
        }
        Date startTime = period.date;
        Date endTime = new Date(period.date.getTime() + (60000 * period.stopTime));
        Tracking tracking = new Tracking(Integer.parseInt(id), title, startTime, endTime, meetingTime);
        Observer.getSingletonInstance(context).addTracking(tracking);
    }
}
