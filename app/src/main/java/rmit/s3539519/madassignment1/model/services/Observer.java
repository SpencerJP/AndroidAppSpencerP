package rmit.s3539519.madassignment1.model.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.DistanceMatrixModel;
import rmit.s3539519.madassignment1.model.broadcastreceivers.ConnectivityDetector;
import rmit.s3539519.madassignment1.model.broadcastreceivers.NotificationAlarm;
import rmit.s3539519.madassignment1.model.utilities.DistanceMatrixAPIThreadForTracking;
import rmit.s3539519.madassignment1.model.utilities.Importer;
import rmit.s3539519.madassignment1.model.Suggestion;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.broadcastreceivers.SuggestionAlarm;
import rmit.s3539519.madassignment1.model.utilities.LocationTracker;
import rmit.s3539519.madassignment1.model.utilities.SQLiteConnection;
import rmit.s3539519.madassignment1.model.utilities.TrackingDatabaseThread;
import rmit.s3539519.madassignment1.view.viewmodels.SuggestionAdapter;
import rmit.s3539519.madassignment1.view.viewmodels.TrackableAdapter;
import rmit.s3539519.madassignment1.view.viewmodels.TrackingAdapter;

public class Observer {
    private static final long MILLISECONDS_IN_A_MINUTE = 60000;
    private boolean initializationOccurred = false;
    private Context context;
    private Map<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
    private Map<Integer, Tracking> trackings = new HashMap<Integer, Tracking>();
    private Map<Integer, Suggestion> suggestions = new HashMap<Integer, Suggestion>();
    private Importer importer;
    private TrackingAdapter trackingAdapter;
    private TrackableAdapter trackableAdapter;
    private SuggestionAdapter suggestionAdapter;
    private SuggestionAlarm suggestionAlarm;
    private NotificationAlarm notificationAlarm;
    private LocationManager locationManager;
    private LocationTracker locationTracker;
    private int nextNotificationId = 0;

    // empty constructor
    private Observer() {
    }

    // a bit of a pseudo constructor
    public void initialize() {
        if (initializationOccurred) { // prevent this from happening more than once
            return;
        }
        else {

            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ((Activity) context).requestPermissions(perms, 5919);
            initializeLocationObjects();
            createSQLTables();
            importData();

            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(new ConnectivityDetector(), intentFilter);

            SuggestionAlarm suggestionAlarm = new SuggestionAlarm();
            setSuggestionAlarm(suggestionAlarm);
            suggestionAlarm.setAlarm(context);

            NotificationAlarm notificationAlarm = new NotificationAlarm();
            setNotificationAlarm(notificationAlarm);
            notificationAlarm.setAlarm(context);
            initializationOccurred = true;
        }
    }

    public void initializeLocationObjects() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationTracker = new LocationTracker(context, locationManager);
    }
    public void createSQLTables() {
        SQLiteConnection conn = new SQLiteConnection(context);
        conn.createTablesIfTheyDontExist();
    }

    public void setSuggestionAdapter(SuggestionAdapter suggestionAdapter) {
        this.suggestionAdapter = suggestionAdapter;
    }

    public void setSuggestionAlarm(SuggestionAlarm suggestionAlarm) {
        this.suggestionAlarm = suggestionAlarm;
    }

    public SuggestionAlarm getSuggestionAlarm() {
        return suggestionAlarm;
    }

    public void setNotificationAlarm(NotificationAlarm notificationAlarm) {
        this.notificationAlarm = notificationAlarm;
    }

    public NotificationAlarm getNotificationAlarm() {
        return notificationAlarm;
    }

    public int getNextNotificationId() {
        return nextNotificationId++;
    }

    // adds DistanceMatrix stuff back to tracking after load, since it can't really be stored
    public void addMissingDetailsToTracking(Tracking tracking, DistanceMatrixModel returnValue) {
        tracking = getTrackingById(Integer.parseInt(tracking.getTrackingId()));
        tracking.setDistance(returnValue.getDistanceInMetres());
        tracking.setTimeUntilLocation(returnValue.getTimeDifference());
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Observer.getSingletonInstance().updateViews();
            }
        });
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public LocationTracker getLocationTracker() {
        return locationTracker;
    }

    // cloned singleton methods from Casper's TrackingService.java
    private static class ObserverSingleton
    {
        static final Observer INSTANCE = new Observer();
    }

    public static Observer getSingletonInstance(Context context) {
        Observer.ObserverSingleton.INSTANCE.context = context;
        return Observer.ObserverSingleton.INSTANCE;
    }

    // only for special circumstances
    public static Observer getSingletonInstance() {
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

    public void importData() {
        importer = new Importer((Activity) context);
        importer.loadDatabase(importer.readFile(context, R.raw.food_truck_data), trackables, trackings);
    }

    public Map<Integer, AbstractTrackable> getTrackables() { return trackables;}
    public Map<Integer, Tracking> getTrackings() { return trackings;}
    public Map<Integer, Suggestion> getSuggestions() { return suggestions; }

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
            requestMissingDetails(tracking);
            trackings.put(Integer.parseInt(tracking.getTrackingId()), tracking);
            tracking.save((Activity) context); // add it to the database
            if (trackingAdapter != null) {
                trackingAdapter.updateTrackings(trackings);
            }
    }

    private void requestMissingDetails(Tracking tracking) {

        double sourceLat = locationTracker.getLatitude();
        double sourceLong = locationTracker.getLongitude();
        double destLat = tracking.getLatitude(context);
        double destLong = tracking.getLongitude(context);
        AbstractTrackable trackable = getTrackableById(tracking.getTrackableId());

        DistanceMatrixAPIThreadForTracking thread = new DistanceMatrixAPIThreadForTracking(context,trackable, sourceLat, sourceLong, destLat, destLong, tracking);
        Thread t = new Thread(thread);
        t.start();
    }

    public void requestMissingDetailsForAllTrackings() {
        for(Map.Entry<Integer, Tracking> entry : trackings.entrySet()) {
            double sourceLat = locationTracker.getLatitude();
            double sourceLong = locationTracker.getLongitude();
            double destLat = entry.getValue().getLatitude(context);
            double destLong = entry.getValue().getLongitude(context);
            AbstractTrackable trackable = getTrackableById(entry.getValue().getTrackableId());

            DistanceMatrixAPIThreadForTracking thread = new DistanceMatrixAPIThreadForTracking(context,trackable, sourceLat, sourceLong, destLat, destLong, entry.getValue());
            Thread t = new Thread(thread);
            t.start();
        }
    }

    public void addSuggestion(Suggestion suggestion) {
        suggestions.put(suggestion.getId(), suggestion);
        if (suggestionAdapter != null) {
            suggestionAdapter.updateSuggestions(suggestions);
        }
    }

    public void removeSuggestion(int id) {
        if (suggestions.containsKey(id)) {
            suggestions.remove(id);
            if (suggestionAdapter != null) {
                suggestionAdapter.updateSuggestions(suggestions);
            }
        }
        if (suggestionAdapter != null) {
            suggestionAdapter.updateSuggestions(suggestions);
        }
    }

    public Suggestion getSuggestionById(int id) {
        return suggestions.get(id);
    }

    public Tracking getTrackingById(int id) {
        return trackings.get(id);
    }

    public void removeTracking(int id) {
        if (trackings.containsKey(id)) {
            TrackingDatabaseThread tdt = new TrackingDatabaseThread("destroy", id, context);
            Thread t = new Thread(tdt);
            t.start();
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
