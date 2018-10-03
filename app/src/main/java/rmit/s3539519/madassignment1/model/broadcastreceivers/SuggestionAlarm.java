package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;

import java.util.Map;

import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.services.SuggestTrackingService;

public class SuggestionAlarm extends BroadcastReceiver {
    private Map<Integer, AbstractTrackable> trackables;

    public SuggestionAlarm() {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            SuggestTrackingService service = new SuggestTrackingService(context);
            service.suggestTracking();
        }
        catch(SecurityException e) {
            Log.i("Security Exception: ", e.getMessage());
        }
    }

    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, SuggestionAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int currentNotificationPeriod = prefs.getInt("rmit.s3539519.madassignment1.suggestion_frequency", 5); // this is in minutes
        //(currentNotificationPeriod * 60000)

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, SuggestionAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
