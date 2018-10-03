package rmit.s3539519.madassignment1.model.utilities;

import android.app.Activity;
import android.widget.Toast;

import rmit.s3539519.madassignment1.model.DistanceMatrixModel;
import rmit.s3539519.madassignment1.model.services.DistanceMatrixService;

public class DistanceMatrixAPIThread implements Runnable {

    private DistanceMatrixModel returnValue;
    private double sourceLat;
    private double sourceLong;
    private double destLat;
    private double destLong;
    private Activity context;

    public DistanceMatrixAPIThread(Activity context, double sourceLat, double sourceLong, double destLat, double destLong) {
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.destLat = destLat;
        this.destLong = destLong;
        this.context = context;
    }

    @Override
    public void run() {
        returnValue = DistanceMatrixService.getSingletonInstance(context).makeAPIRequest(sourceLat, sourceLong, destLat, destLong);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(returnValue != null) {
                    Toast.makeText(context, "Distance: " + Long.toString(returnValue.getDistanceInMetres()) + ", seconds: " + Long.toString(returnValue.getTimeDifference()),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
