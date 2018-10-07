package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

// class to receive the reminder request and sets an alarm for the notification to pop up
public class NotificationReminder extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 1406;
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = NOTIFICATION_ID;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationId);
        NotificationReminderAlarm alarm = new NotificationReminderAlarm();
        SharedPreferences prefs = context.getSharedPreferences(
                "rmit.s3539519.madassignment1", Context.MODE_PRIVATE);
        int reminderMinutes = prefs.getInt("rmit.s3539519.madassignment1.notification_period", 5); // this is in minutes
        alarm.setAlarm(context, reminderMinutes);
    }

}
