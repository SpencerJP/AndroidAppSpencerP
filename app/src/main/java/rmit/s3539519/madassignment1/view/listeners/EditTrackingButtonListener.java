package rmit.s3539519.madassignment1.view.listeners;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTracking;
import rmit.s3539519.madassignment1.model.TrackingNotValidException;
import rmit.s3539519.madassignment1.view.EditTrackable;

public class EditTrackingButtonListener implements View.OnClickListener {

    private static long millisecondsInADay = 86400000;
    private EditTracking context;
    private EditTrackable editView;
    public EditTrackingButtonListener(Context context, EditTrackable editView) {
        this.context = (EditTracking) context;
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
                Date startTime = validateTime(editView.getStartTime().getText().toString());
                Date endTime = validateTime(editView.getStartTime().getText().toString());
                Date meetingTime = validateTime(editView.getStartTime().getText().toString());
                try {
                    context.addTracking(title, startTime, endTime, meetingTime);
                    context.finish();
                }
                catch(TrackingNotValidException e) {
                    Toast.makeText(context,e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
            catch(ParseException e) {
                Toast.makeText(context,"A time field has the incorrect format!",
                        Toast.LENGTH_SHORT).show();
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
}
