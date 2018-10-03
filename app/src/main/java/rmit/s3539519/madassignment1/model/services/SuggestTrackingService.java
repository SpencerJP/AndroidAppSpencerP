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
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.DistanceMatrixModel;
import rmit.s3539519.madassignment1.model.Suggestion;
import rmit.s3539519.madassignment1.model.utilities.DistanceMatrixAPIThread;
import rmit.s3539519.madassignment1.model.utilities.LocationTracker;

import static rmit.s3539519.madassignment1.model.services.Observer.getSingletonInstance;

public class SuggestTrackingService {
    private Context context;
    private LocationManager locationManager;
    private LocationTracker locationTracker;
    private ArrayList<DistanceMatrixAPIThread> dmThreads;
    private Suggestion potentialSuggestion;
    private Map<Integer, AbstractTrackable> trackables;

    public SuggestTrackingService(Context context, Map<Integer, AbstractTrackable> trackables) {
        this.context = context;
        this.trackables = trackables;
        if (trackables == null) {
            Log.i("suggestTracking", "UHHH");
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationTracker = new LocationTracker();
    }

    public void suggestTracking() throws SecurityException {
        Log.i("suggestTracking", "TEST1");

        if (!canAccessLocation(context)) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationTracker);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationTracker);
        double longitude = locationTracker.getLongitude();
        double latitude = locationTracker.getLatitude();
        dmThreads = new ArrayList<DistanceMatrixAPIThread>();
        Log.i("suggestTracking", "TEST2");
        for(Map.Entry<Integer, AbstractTrackable> trackableEntry : trackables.entrySet()) {

            Log.i("suggestTracking", "TEST7");
            DistanceMatrixAPIThread newThread = new DistanceMatrixAPIThread(context, trackableEntry.getValue(), latitude, longitude, trackableEntry.getValue().getCurrentLatitude(context), trackableEntry.getValue().getCurrentLongitude(context));
            Thread t = new Thread(newThread);
            dmThreads.add(newThread);
            t.start();
        }

        Log.i("suggestTracking", "TEST3");
        DistanceMatrixModel closestTrackable = null;
        while(dmThreads.size() != 0) {

            Iterator<DistanceMatrixAPIThread> iter = dmThreads.iterator();
            while(iter.hasNext()) {
                Log.i("suggestTracking", "TEST6");
                if (iter.next().isFinished() == true) {
                    DistanceMatrixModel model = iter.next().getReturnValue();
                    iter.remove();
                    if (closestTrackable != null) {
                        if(model != null) {
                            if(model.getTimeDifference() < closestTrackable.getTimeDifference()) {
                                closestTrackable = model;
                            }
                        }

                    }
                    else {
                        closestTrackable = model;
                    }
                }
            }
        }
        if(closestTrackable != null) {
            Log.i("suggestTracking", "TEST4");
            Suggestion suggestion = new Suggestion(closestTrackable);
            Observer.getSingletonInstance(context).addSuggestion(suggestion);
        }


    }

    public boolean canAccessLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


}
