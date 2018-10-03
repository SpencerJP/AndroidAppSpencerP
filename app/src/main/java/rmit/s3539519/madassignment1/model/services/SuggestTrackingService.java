package rmit.s3539519.madassignment1.model.services;

import android.Manifest;
import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.DistanceMatrixModel;
import rmit.s3539519.madassignment1.model.Suggestion;
import rmit.s3539519.madassignment1.model.utilities.DistanceMatrixAPIThread;
import rmit.s3539519.madassignment1.model.utilities.LocationTracker;

import static rmit.s3539519.madassignment1.model.services.Observer.getSingletonInstance;

public class SuggestTrackingService extends JobService {
    private Context context;
    private LocationManager locationManager;
    private LocationTracker locationTracker;
    private ArrayList<DistanceMatrixAPIThread> dmThreads;
    private Suggestion potentialSuggestion;
    public SuggestTrackingService() {
        super();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationTracker = new LocationTracker();

    }

    @Override
    public boolean onStartJob(JobParameters params) {

        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void suggestTracking(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationTracker);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationTracker);
        double longitude = locationTracker.getLongitude();
        double latitude = locationTracker.getLatitude();
        dmThreads = new ArrayList<DistanceMatrixAPIThread>();
        for(Map.Entry<Integer, AbstractTrackable> trackableEntry : Observer.getSingletonInstance(context).getTrackables().entrySet()) {
            DistanceMatrixAPIThread newThread = new DistanceMatrixAPIThread((Activity)context, latitude, longitude, trackableEntry.getValue().getCurrentLatitude(context), trackableEntry.getValue().getCurrentLongitude(context));
            Thread t = new Thread(newThread);
            dmThreads.add(newThread);
            t.start();
        }
        while(dmThreads.size() != 0) {
            Iterator<DistanceMatrixAPIThread> iter = dmThreads.iterator();
            while(iter.hasNext()) {
                if (iter.next().isFinished() == true) {
                    DistanceMatrixModel model = iter.next().getReturnValue();
                    iter.remove();
                }
            }
            for(DistanceMatrixAPIThread t : dmThreads) {

            }
        }


    }


}
