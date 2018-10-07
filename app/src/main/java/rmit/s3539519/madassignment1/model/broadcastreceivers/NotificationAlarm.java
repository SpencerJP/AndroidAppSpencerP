package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.view.activities.SuggestionListActivity;

public class NotificationAlarm extends BroadcastReceiver {
    public static final int PENDING_SUGGESTION_LIST_CODE = 0451;
    public static final int PENDING_NOTIFICATION_REMINDER_CODE = 5919;
    public static final int NOTIFICATION_ID = 1406;

    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId = "s3539519_geotracker";
        createNotificationChannel(context, channelId);
        if (Observer.getSingletonInstance(context).getSuggestions().size() > 0 ) {
            sendNotification(context, channelId, Observer.getSingletonInstance(context).getSuggestions().size());
        }

    }

    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int currentNotificationPeriod = prefs.getInt("rmit.s3539519.madassignment1.notification_period", 5); // this is in minutes
        //(currentNotificationPeriod * 60000)

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (currentNotificationPeriod * 60000), pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NotificationAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void createNotificationChannel(Context context, String  channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(Context context, String channelId, int suggestionCount) {

        int id = NOTIFICATION_ID;
        Intent suggestionList = new Intent(context, SuggestionListActivity.class);
        PendingIntent suggestionListPending = PendingIntent.getActivity(context, PENDING_SUGGESTION_LIST_CODE, suggestionList, 0);


        Intent reminderIntent = new Intent(context, NotificationReminder.class);
        reminderIntent.putExtra("s3539519_notification_id", id);
        PendingIntent reminderPendingIntent =
                PendingIntent.getBroadcast(context, PENDING_NOTIFICATION_REMINDER_CODE, reminderIntent, 0);

        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int reminderMinutes = prefs.getInt("rmit.s3539519.madassignment1.notification_period", 5); // this is in minutes

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_snooze_black_24dp, String.format("Remind in %d minutes", reminderMinutes), reminderPendingIntent).build();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);
        mBuilder.setSmallIcon(R.drawable.ic_directions_car_black_24dp);
        mBuilder.setContentTitle("Geotracker");
        mBuilder.setContentText(String.format("There are %d pending suggestions!", suggestionCount));
        mBuilder.setContentIntent(suggestionListPending);
        mBuilder.addAction(action);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());
    }
}
