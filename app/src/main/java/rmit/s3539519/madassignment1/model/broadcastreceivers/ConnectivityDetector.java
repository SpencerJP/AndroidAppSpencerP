package rmit.s3539519.madassignment1.model.broadcastreceivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.SuggestTrackingService;

public class ConnectivityDetector extends BroadcastReceiver {
    private boolean wasPreviouslyConnected = true;
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (isConnected(context)) {
            if (!wasPreviouslyConnected) {
                wasPreviouslyConnected = true;
                try {
                    SuggestTrackingService service = new SuggestTrackingService(context);
                    service.suggestTracking();
                    try {

                        Activity uiThread = (Activity) context;
                        uiThread.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(context, R.string.new_suggestion_available_from_connectivity_detector, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    catch(ClassCastException e) { /* No toast */ }
                }
                catch(SecurityException e) {
                    Log.i("Security Exception: ", e.getMessage());
                }
            }
        }
        else {
            wasPreviouslyConnected = false;
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
