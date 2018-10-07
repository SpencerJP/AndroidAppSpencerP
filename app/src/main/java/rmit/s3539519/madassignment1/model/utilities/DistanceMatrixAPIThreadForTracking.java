package rmit.s3539519.madassignment1.model.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.services.DistanceMatrixService;
import rmit.s3539519.madassignment1.model.services.Observer;

public class DistanceMatrixAPIThreadForTracking extends DistanceMatrixAPIThread {
    private Tracking tracking;

    public DistanceMatrixAPIThreadForTracking(Context context, AbstractTrackable trackable, double sourceLat, double sourceLong, double destLat, double destLong, Tracking tracking) {
        super(context, trackable, sourceLat, sourceLong, destLat, destLong);
        this.tracking = tracking;
    }

    @Override
    public void run() {
        if  ((sourceLong == 0) || sourceLat == 0) {
            Log.i("DistanceMatrixAPIThread", "failure due to missing location");

            // NOTE TO MARKER:
            // hardcoding for demo because I have no idea why the location is not working at this point in time
            sourceLat  = -37.807425;
            sourceLong = 144.963814;
            returnValue = DistanceMatrixService.getSingletonInstance(context).makeAPIRequest(trackable, sourceLat, sourceLong, destLat, destLong);
            if(returnValue != null) {
                Observer.getSingletonInstance(context).addMissingDetailsToTracking(tracking, returnValue);
            }
        }
        else {
            returnValue = DistanceMatrixService.getSingletonInstance(context).makeAPIRequest(trackable, sourceLat, sourceLong, destLat, destLong);
            if(returnValue != null) {
                Observer.getSingletonInstance(context).addMissingDetailsToTracking(tracking, returnValue);
            }
        }
    }
}
