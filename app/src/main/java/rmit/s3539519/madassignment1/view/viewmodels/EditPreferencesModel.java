package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditPreferencesButtonListener;

public class EditPreferencesModel
{


    private EditText notificationPeriod;
    private EditText suggestionFrequency;
    private Button cancel;
    private Button save;
    private Context context;
    private EditPreferencesButtonListener listener;

    public EditPreferencesModel(AppCompatActivity context) {

        this.context = context;
        notificationPeriod = context.findViewById(R.id.edit_preferences_notification_period);
        suggestionFrequency = context.findViewById(R.id.edit_tracking_suggestion_frequency);
        cancel = context.findViewById(R.id.edit_preferences_cancel_button);
        save = context.findViewById(R.id.edit_preferences_save_button);

        listener = new EditPreferencesButtonListener(context, this);
        getCancel().setOnClickListener(getListener());
        getSave().setOnClickListener(getListener());
    }

    public EditText getNotificationPeriod() {
        return notificationPeriod;
    }

    public EditText getSuggestionFrequency() {
        return suggestionFrequency;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getSave() {
        return save;
    }

    public EditPreferencesButtonListener getListener() {
        return listener;
    }

}
