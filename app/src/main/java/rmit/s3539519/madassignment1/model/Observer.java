package rmit.s3539519.madassignment1.model;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.R;

public class Observer {
    private Context context;
    private Map<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
    private Map<String, Tracking> trackings = new HashMap<String, Tracking>();
    private TrackableImporter trackableImporter = new TrackableImporter();

    // empty constructor
    private Observer() {
    }
    // cloned singleton methods from Casper's TrackingService.java
    private static class ObserverSingleton
    {
        static final Observer INSTANCE = new Observer();
    }

    public static Observer getSingletonInstance(Context context)
    {
        Observer.ObserverSingleton.INSTANCE.context = context;
        return Observer.ObserverSingleton.INSTANCE;
    }

    public void importTrackables() {
        trackableImporter.stringToFoodTruckMap(trackableImporter.readFile(context, R.raw.food_truck_data), trackables);
    }

    public Map<Integer, AbstractTrackable> getTrackables() { return trackables;}
    public Map<String, Tracking> getTrackings() { return trackings;}

    public boolean addTracking(Tracking tracking) {
        if (tracking.isValid()) {
            trackings.put(tracking.getTrackingId(), tracking);
            return true;
        } else {
            return false;
        }
    }




}
