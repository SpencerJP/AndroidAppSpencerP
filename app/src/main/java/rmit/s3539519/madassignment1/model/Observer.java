package rmit.s3539519.madassignment1.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.TrackableAdapter;
import rmit.s3539519.madassignment1.view.TrackingAdapter;

public class Observer {
    private static final long MILLISECONDS_IN_A_MINUTE = 60000;
    private Context context;
    private Map<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
    private Map<Integer, Tracking> trackings = new HashMap<Integer, Tracking>();
    private Importer importer;
    private TrackingAdapter trackingAdapter;
    private TrackableAdapter trackableAdapter;

    // empty constructor
    private Observer() {
    }

    public void createSQLTables() {
        SQLiteConnection conn = new SQLiteConnection(context);
        conn.createTablesIfTheyDontExist();
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

    public void setTrackingAdapter(TrackingAdapter trackingAdapter) {
        this.trackingAdapter = trackingAdapter;
    }

    public void setTrackableAdapter(TrackableAdapter trackableAdapter) {
        this.trackableAdapter = trackableAdapter;
    }
    public TrackingAdapter getTrackingAdapter() {
        return trackingAdapter;
    }
    public TrackableAdapter getTrackableAdapter() {
        return trackableAdapter;
    }

    public void importTrackables() {
        importer = new Importer((Activity) context);
        importer.loadDatabase(importer.readFile(context, R.raw.food_truck_data), trackables, trackings);
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

    public List<String> extractTimeList() {
        Set<String> timeSet = new HashSet<String>();
        List<String> times = new ArrayList<String>();
        for(TrackingInfo t: TrackingService.getSingletonInstance(context).getTrackingInfo()) {
            timeSet.add(new SimpleDateFormat("hh:mma").format(t.date));
        }
        for(String s : timeSet) {
            times.add(s);
        }
        // It looks very wrong if you don't sort this list.
        Collections.sort(times);
        return times;
    }


    public void addTracking(Tracking tracking) {
            trackings.put(Integer.parseInt(tracking.getTrackingId()), tracking);
            if (trackingAdapter != null) {
                trackingAdapter.updateTrackings(trackings);
            }
    }

    public Tracking getTrackingById(int id) {
        return trackings.get(id);
    }

    public void removeTracking(int id) {
        if (trackings.containsKey(id)) {
            trackings.remove(id);
            if (trackingAdapter != null) {
                trackingAdapter.updateTrackings(trackings);
            }
        }
    }

    public void updateViews() {
        if (trackingAdapter != null) {
            trackingAdapter.updateTrackings(trackings);
        }
        if (getTrackableAdapter() != null) {
            getTrackableAdapter().updateTrackables(trackables);
        }
    }

    public AbstractTrackable getTrackableById(int id) {
        return trackables.get(id);
    }












    // generate 2 trackings for use in the demo
    public void seedTrackings() {
        try {
            String startTime = "1:00pm";
            String endTime = "1:30pm";
            String meetTime = "1:30pm";
            Date currentDate = new Date();
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mma");
            startTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + startTime;
            Date startTimeDate = formatter.parse(startTime);

            endTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + endTime;
            Date endTimeDate = formatter.parse(endTime);

            meetTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + meetTime;
            Date meetTimeDate = formatter.parse(meetTime);

            long halfwayBetweenTimes = (startTimeDate.getTime() + endTimeDate.getTime()) / 2;
            int minutes = (int) ((endTimeDate.getTime() - startTimeDate.getTime() ) / MILLISECONDS_IN_A_MINUTE);

            Tracking tracking1 = new Tracking(1, "Flagstaff Gardens", startTimeDate, endTimeDate, meetTimeDate);
            trackings.put(Integer.parseInt(tracking1.getTrackingId()), tracking1);
            if (trackingAdapter != null) {
                trackingAdapter.updateTrackings(trackings);
            }
        }
        catch(Exception e) {
            Log.i("seeding", "seeding failure on Tracking 1: " + e.getMessage());
        }

        try {
            String startTime = "1:30pm";
            String endTime = "1:55pm";
            String meetTime = "1:55pm";
            Date currentDate = new Date();
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mma");
            startTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + startTime;
            Date startTimeDate = formatter.parse(startTime);

            endTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + endTime;
            Date endTimeDate = formatter.parse(endTime);

            meetTime = new SimpleDateFormat("MM-dd-yyyy").format(currentDate) + " " + meetTime;
            Date meetTimeDate = formatter.parse(meetTime);

            long halfwayBetweenTimes = (startTimeDate.getTime() + endTimeDate.getTime()) / 2;
            int minutes = (int) ((endTimeDate.getTime() - startTimeDate.getTime() ) / MILLISECONDS_IN_A_MINUTE);

            Tracking tracking2 = new Tracking(3, "Festival Hall", startTimeDate, endTimeDate, meetTimeDate);
            trackings.put(Integer.parseInt(tracking2.getTrackingId()), tracking2);
            if (trackingAdapter != null) {
                trackingAdapter.updateTrackings(trackings);
            }
        }
        catch(Exception e){
            Log.i("seeding", "seeding failure on Tracking 2: " + e.getMessage());
        }
    }

}
