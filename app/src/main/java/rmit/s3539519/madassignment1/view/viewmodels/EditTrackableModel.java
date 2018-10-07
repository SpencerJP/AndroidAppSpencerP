package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTrackingButtonListener;

public class EditTrackableModel
{


    private TextView suggestionIdHidden;
    private TextView validTimeList;
    private TextView title;
    private EditText meetingTime;
    private Button cancel;
    private Button save;
    private Context context;
    private EditTrackingButtonListener listener;

    public EditTrackableModel(AppCompatActivity context) {

        this.context = context;
        title = context.findViewById(R.id.edit_tracking_title);
        meetingTime = context.findViewById(R.id.meetingTimeField);
        validTimeList = context.findViewById(R.id.validTimeList);
        cancel = context.findViewById(R.id.edit_tracking_cancel_button);
        save = context.findViewById(R.id.edit_tracking_save_button);
        suggestionIdHidden = context.findViewById(R.id.suggestionIdHidden);

        listener = new EditTrackingButtonListener(context, this);
        cancel.setOnClickListener(listener);
        save.setOnClickListener(listener);
    }

    public TextView getTitle() {
        return title;
    }

    public EditText getMeetingTime() {
        return meetingTime;
    }

    public TextView getValidTimeList() {
        return validTimeList;
    }

    public void setValidTimeList(TextView validTimeList) {
        this.validTimeList = validTimeList;
    }

    public TextView getSuggestionIdHidden() {
        return suggestionIdHidden;
    }
}
