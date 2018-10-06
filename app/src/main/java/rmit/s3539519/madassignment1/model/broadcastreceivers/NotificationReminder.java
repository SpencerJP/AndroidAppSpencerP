package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class NotificationReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationReminderAlarm alarm = new NotificationReminderAlarm();
        int reminderMinutes = 5;
        alarm.setAlarm(context, reminderMinutes);
    }

}
