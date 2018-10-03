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
import rmit.s3539519.madassignment1.model.services.SuggestTrackingService;

public class SuggestionAlarm extends BroadcastReceiver {
    private Map<Integer, AbstractTrackable> trackables;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        try {
            if (this.trackables == null) {
                Log.i("suggestTracking", "UHHH2");
            }
            SuggestTrackingService service = new SuggestTrackingService(context, this.trackables);
            service.suggestTracking();
        }
        catch(SecurityException e) {
            Log.i("Security Exception: ", e.getMessage());
        }

        wl.release();
    }

    public void setAlarm(Context context, Map<Integer, AbstractTrackable> trackables)
    {
        this.trackables = trackables;
        if (trackables == null) {
            Log.i("suggestTracking", "UHHH1");
        }
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, SuggestionAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int currentNotificationPeriod = prefs.getInt("rmit.s3539519.madassignment1.suggestion_frequency", 5); // this is in minutes
        //(currentNotificationPeriod * 60000)
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pi);
    }

    public void cancelAlarm(Context context, Map<Integer, AbstractTrackable> trackables)
    {
        Intent intent = new Intent(context, SuggestionAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
