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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
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

    public SuggestTrackingService(Context context) {
        this.context = context;
        locationManager = Observer.getSingletonInstance(context).getLocationManager();
        locationTracker = Observer.getSingletonInstance(context).getLocationTracker();
    }

    // this method starts a bunch of DistanceMatrix calls with their own threads. It receives the results and picks the closest one to the user.
    // then it adds  it to the suggestion list.
    public boolean suggestTracking() throws SecurityException {

        if (!canAccessLocation(context)) {
            return false;
        }
        double longitude = locationTracker.getLongitude();
        double latitude = locationTracker.getLatitude();

        if  ((longitude == 0) || latitude == 0) {

            try {

                Activity uiThread = (Activity) context;
                uiThread.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context, "No location available!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch(ClassCastException e) { /* No toast */  }

            return false;
        }

        dmThreads = new ArrayList<DistanceMatrixAPIThread>();
        // make a new thread for each distance  matrix api call
        for(Map.Entry<Integer, AbstractTrackable> trackableEntry : Observer.getSingletonInstance().getTrackables().entrySet()) {

            if ((trackableEntry.getValue().getCurrentLatitude(context) == 0) || trackableEntry.getValue().getCurrentLongitude(context) ==  0) {
                continue;
            }
            DistanceMatrixAPIThread newThread = new DistanceMatrixAPIThread(context, trackableEntry.getValue(), latitude, longitude, trackableEntry.getValue().getCurrentLatitude(context), trackableEntry.getValue().getCurrentLongitude(context));
            Thread t = new Thread(newThread);
            dmThreads.add(newThread);
            t.start();
        }

        // process each thread, determining the closest trackable
        DistanceMatrixModel closestTrackable = null;
        while(dmThreads.size() != 0) {
            Iterator<DistanceMatrixAPIThread> iter = dmThreads.iterator();
            while(iter.hasNext()) {
                DistanceMatrixAPIThread current = iter.next();
                if (current != null) {
                    if(current.isFinished()) {
                        DistanceMatrixModel model = current.getReturnValue();
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
        }
        if(closestTrackable != null) {
            Suggestion suggestion = new Suggestion(closestTrackable);
            Observer.getSingletonInstance(context).addSuggestion(suggestion);

            return true;

        }
        return false;
    }

    public boolean canAccessLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


}
