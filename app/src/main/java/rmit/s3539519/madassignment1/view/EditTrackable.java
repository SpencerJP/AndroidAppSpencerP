package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;

public class EditTrackable
{
    private TextView title;
    private TextView startTime;
    private TextView endTime;
    private TextView meetingTime;
    private Button cancel;
    private Button save;
    private Context context;
    private EditTrackingButtonListener listener;

    public EditTrackable(AppCompatActivity context) {

        this.context = context;
        title = context.findViewById(R.id.edit_tracking_title);
        startTime = context.findViewById(R.id.edit_tracking_start_time);
        endTime = context.findViewById(R.id.edit_tracking_end_time);
        meetingTime = context.findViewById(R.id.edit_tracking_meeting_time);
        cancel = context.findViewById(R.id.edit_tracking_cancel_button);
        save = context.findViewById(R.id.edit_tracking_save_button);

        listener = new EditTrackingButtonListener(context, this);
        cancel.setOnClickListener(listener);
        save.setOnClickListener(listener);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getStartTime() {
        return startTime;
    }

    public TextView getEndTime() {
        return endTime;
    }

    public TextView getMeetingTime() {
        return meetingTime;
    }
}
