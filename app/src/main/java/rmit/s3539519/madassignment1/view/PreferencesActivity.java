package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.viewmodels.EditPreferencesModel;

public class PreferencesActivity extends AppCompatActivity {

    private EditPreferencesModel editPreferencesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        editPreferencesViewModel = new EditPreferencesModel(this);
        SharedPreferences prefs = this.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int currentNotificationPeriod = prefs.getInt("rmit.s3539519.madassignment1.notification_period", 5); // this is in minutes
        int currentSuggestionFrequency = prefs.getInt("rmit.s3539519.madassignment1.suggestion_frequency", 5); // this is in minutes
        editPreferencesViewModel.getSuggestionFrequency().setText(Integer.toString(currentSuggestionFrequency));
        editPreferencesViewModel.getNotificationPeriod().setText(Integer.toString(currentNotificationPeriod));
    }
}
