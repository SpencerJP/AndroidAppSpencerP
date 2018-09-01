package rmit.s3539519.madassignment1.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rmit.s3539519.madassignment1.R;

public class Observer {
    private Context context;
    private Map<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
    private Map<Integer, Tracking> trackings = new HashMap<Integer, Tracking>();
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
    public Map<Integer, Tracking> getTrackings() { return trackings;}

    public List<String> extractCategoryList(Map<Integer, AbstractTrackable> trackableList) {
        // using a set to ensure there are no duplicate categories
        Set<String> categorySet = new HashSet<String>();
        List<String> categories = new ArrayList<String>();
        categorySet.add("All");// add "All" to our spinner, make sure it cannot be duplicated
        for(Map.Entry<Integer, AbstractTrackable> entry : trackableList.entrySet()) {
            categorySet.add(entry.getValue().getCategory());
        }
        for(String s : categorySet) {
            categories.add(s);
        }
        return categories;
    }


    public void addTracking(Tracking tracking) {
            trackings.put(Integer.parseInt(tracking.getTrackingId()), tracking);
    }


}
