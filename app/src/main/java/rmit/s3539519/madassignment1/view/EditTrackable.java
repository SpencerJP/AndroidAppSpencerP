package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.listeners.EditTrackingButtonListener;

public class EditTrackable
{
    private TextView title;
    private Spinner startTime;
    private Spinner endTime;
    private Spinner meetingTime;
    private Button cancel;
    private Button save;
    private Context context;
    private EditTrackingButtonListener listener;

    public EditTrackable(AppCompatActivity context) {

        this.context = context;
        title = context.findViewById(R.id.edit_tracking_title);
        startTime = context.findViewById(R.id.startTimeSpinner);
        endTime = context.findViewById(R.id.endTimeSpinner);
        meetingTime = context.findViewById(R.id.meetingTimeSpinner);
        cancel = context.findViewById(R.id.edit_tracking_cancel_button);
        save = context.findViewById(R.id.edit_tracking_save_button);

        listener = new EditTrackingButtonListener(context, this);
        cancel.setOnClickListener(listener);
        save.setOnClickListener(listener);
    }

    public TextView getTitle() {
        return title;
    }

    public Spinner getStartTime() {
        return startTime;
    }

    public Spinner getEndTime() {
        return endTime;
    }

    public Spinner getMeetingTime() {
        return meetingTime;
    }
}
