package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.services.SuggestTrackingService;
import rmit.s3539519.madassignment1.view.SuggestionListActivity;

public class NotificationAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId = "s3539519_geotracker";
        createNotificationChannel(context, channelId);
        sendNotification(context, channelId, Observer.getSingletonInstance(context).getSuggestions().size());

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


        Intent suggestionList = new Intent(context, SuggestionListActivity.class);
        PendingIntent suggestionListPending = PendingIntent.getActivity(context, 0, suggestionList, 0);
        Intent reminderIntent = new Intent(context, NotificationReminder.class);
        PendingIntent reminderPendingIntent =
                PendingIntent.getBroadcast(context, 0, reminderIntent, 0);
        int reminderMinutes = 5;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);
        mBuilder.setSmallIcon(R.drawable.ic_directions_car_black_24dp);
        mBuilder.setContentTitle("Geotracker");
        mBuilder.setContentText(String.format("There are %d pending suggestions!", suggestionCount));
        mBuilder.setContentIntent(suggestionListPending);
        mBuilder.addAction(R.drawable.ic_snooze_black_24dp, "remind", reminderPendingIntent);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(Observer.getSingletonInstance(context).getNextNotificationId(), mBuilder.build());
    }
}
