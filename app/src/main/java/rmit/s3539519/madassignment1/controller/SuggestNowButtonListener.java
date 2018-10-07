package rmit.s3539519.madassignment1.controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.broadcastreceivers.NotificationAlarm;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.services.SuggestTrackingService;
import rmit.s3539519.madassignment1.view.viewmodels.SuggestionAdapter;

public class SuggestNowButtonListener implements View.OnClickListener {

    private Context context;
    private SuggestionAdapter editView;
    private boolean lock = false;
    public SuggestNowButtonListener(Context context, SuggestionAdapter editView) {
        this.context =  context;
        this.editView = editView;
    }
    @Override
    public void onClick(View v) {
        if (lock) {
            try {

                Activity uiThread = (Activity) context;
                uiThread.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context, R.string.please_wait, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch(ClassCastException e) { /* No toast */ }

        }
        else {
            lock = true;
            SuggestTrackingService service = new SuggestTrackingService(context);
            service.suggestTracking();
            try {

                Activity uiThread = (Activity) context;
                uiThread.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context, R.string.new_suggestion_available_from_suggest_now, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch(ClassCastException e) { /* No toast */ }
            lock = false;
        }

    }

}
