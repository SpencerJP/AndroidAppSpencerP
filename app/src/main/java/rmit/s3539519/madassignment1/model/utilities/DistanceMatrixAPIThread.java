package rmit.s3539519.madassignment1.model.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.DistanceMatrixModel;
import rmit.s3539519.madassignment1.model.services.DistanceMatrixService;

public class DistanceMatrixAPIThread implements Runnable {

    private DistanceMatrixModel returnValue;
    private double sourceLat;
    private double sourceLong;
    private double destLat;
    private double destLong;
    private Context context;
    private AbstractTrackable trackable;
    private boolean finished = false;

    public DistanceMatrixAPIThread(Context context, AbstractTrackable trackable, double sourceLat, double sourceLong, double destLat, double destLong) {
        this.trackable = trackable;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.destLat = destLat;
        this.destLong = destLong;
        this.context = context;
    }

    @Override
    public void run() {
        returnValue = DistanceMatrixService.getSingletonInstance(context).makeAPIRequest(trackable, sourceLat, sourceLong, destLat, destLong);
        finished = true;
    }

    public DistanceMatrixModel getReturnValue() {
        return returnValue;
    }

    public boolean isFinished() {
        return finished;
    }
}
