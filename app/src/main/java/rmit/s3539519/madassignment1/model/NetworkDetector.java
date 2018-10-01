package rmit.s3539519.madassignment1.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkDetector extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("spencer4", "TEST");
        if (checkConnectivity(context)) {
            Log.i("spencer4", "CONNECTED!");
        }
        else {
            Log.i("spencer4", "DISCONNECTED!");
        }
    }

    private boolean checkConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected());
        } else {
            return false;
        }
    }
}
