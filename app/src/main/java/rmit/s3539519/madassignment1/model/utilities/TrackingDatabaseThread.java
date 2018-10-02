package rmit.s3539519.madassignment1.model.utilities;

import android.content.Context;

import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.utilities.SQLiteConnection;

public class TrackingDatabaseThread implements Runnable {
    private String mode;
    private Tracking tracking;
    private int trackingId;
    private Context context;
    public Boolean success = false;
    public TrackingDatabaseThread(String mode, Tracking tracking, Context context) {
        this.mode = mode;
        this.tracking = tracking;
        this.context = context;
    }

    public TrackingDatabaseThread(String mode, int trackingId, Context context) {
        this.mode = mode;
        this.trackingId = trackingId;
        this.context = context;
    }
    @Override
    public void run() {
        if(mode.equals("save")) {
            boolean success = false;
            SQLiteConnection conn = new SQLiteConnection(context);
            Tracking t = conn.readTracking(tracking.getTrackableId());
            if (t == null) {
                // create one
                success = conn.createTracking(tracking);
            }
            else {
                // update
                success = conn.updateTracking(tracking);
            }
            this.success = success;
        }
        else if (mode.equals("destroy")) {
            boolean success = false;
            SQLiteConnection conn = new SQLiteConnection(context);
            if (tracking == null) {
                success = conn.deleteTracking(trackingId);
            }
            else {
                success = conn.deleteTracking(tracking.getTrackableId());
            }
            this.success = success;
        }
    }
}
