package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.view.activities.SuggestionListActivity;

// class the issues the reminder notification
public class NotificationReminderAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String channelId = "s3539519_geotracker";
        sendNotification(context, channelId, Observer.getSingletonInstance(context).getSuggestions().size());
        this.cancelAlarm(context);
    }

    public void setAlarm(Context context, int timeInMinutesUntilReminder)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationReminderAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (timeInMinutesUntilReminder * 60000), pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NotificationReminderAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void sendNotification(Context context, String channelId, int suggestionCount) {

        Intent suggestionList = new Intent(context, SuggestionListActivity.class);
        PendingIntent suggestionListPending = PendingIntent.getActivity(context, 0, suggestionList, 0);
        Intent reminderIntent = new Intent(context, NotificationReminder.class);
        PendingIntent reminderPendingIntent =
                PendingIntent.getBroadcast(context, 0, reminderIntent, 0);
        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int reminderMinutes = prefs.getInt("rmit.s3539519.madassignment1.notification_period", 5); // this is in minutes
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
                        .setContentTitle("Geotracker")
                        .setContentText(String.format("Reminder! There are %d pending suggestions!", suggestionCount))
                        .setContentIntent(suggestionListPending)
                        .addAction(R.drawable.ic_snooze_black_24dp, String.format(context.getString(R.string.reminder), reminderMinutes),
                                reminderPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(Observer.getSingletonInstance(context).getNextNotificationId(), mBuilder.build());
    }
}
