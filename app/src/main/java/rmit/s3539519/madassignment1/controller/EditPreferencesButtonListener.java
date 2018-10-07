package rmit.s3539519.madassignment1.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.view.activities.PreferencesActivity;
import rmit.s3539519.madassignment1.view.viewmodels.EditPreferencesModel;

public class EditPreferencesButtonListener implements View.OnClickListener {

    private PreferencesActivity context;
    private EditPreferencesModel editView;
    public EditPreferencesButtonListener(Context context, EditPreferencesModel editView) {
        this.context = (PreferencesActivity) context;
        this.editView = editView;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_preferences_cancel_button) {
            context.finish();
        }
        if (v.getId() == R.id.edit_preferences_save_button) {
            SharedPreferences prefs = context.getSharedPreferences(
                    "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
            int notificationPeriod = Integer.parseInt(editView.getNotificationPeriod().getText().toString());
            int suggestionFrequency = Integer.parseInt(editView.getSuggestionFrequency().getText().toString());
            if((notificationPeriod < 1) || (suggestionFrequency < 1)) {
                Toast.makeText(context,"Can't be less than one minute.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putInt("rmit.s3539519.madassignment1.notification_period", notificationPeriod).apply();
            prefs.edit().putInt("rmit.s3539519.madassignment1.suggestion_frequency", suggestionFrequency).apply();
            // restart Alarm
            Observer.getSingletonInstance().getSuggestionAlarm().cancelAlarm(context);
            Observer.getSingletonInstance().getSuggestionAlarm().setAlarm(context);
            context.finish();
        }
    }

}
