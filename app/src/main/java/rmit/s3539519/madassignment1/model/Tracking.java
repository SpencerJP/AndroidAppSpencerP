package rmit.s3539519.madassignment1.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.utilities.DistanceMatrixAPIThread;
import rmit.s3539519.madassignment1.model.utilities.TrackingDatabaseThread;

public class Tracking implements Comparable<Tracking> {
    private String trackingId;
    private int trackableId;
    private String title;
    private Date startTime;
    private Date endTime;
    private Date meetTime;
    private long distance;
    private long timeUntilLocation;

    public Tracking(int trackableId, String title, Date startTime, Date endTime, Date meetTime) {
        trackingId = Integer.toString(trackableId);
        this.trackableId = trackableId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetTime = meetTime;
    }

    public Tracking(int trackableId, String title, Date startTime, Date endTime, Date meetTime, String suggestionId) {
        trackingId = Integer.toString(trackableId);
        this.trackableId = trackableId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetTime = meetTime;

        Suggestion suggestion = Observer.getSingletonInstance().getSuggestionById(Integer.parseInt(suggestionId));
        this.timeUntilLocation = (suggestion.getTimeToTrackableInSeconds() / 60);
    }

    public String getTrackingId() {
        return trackingId;
    }

    public int getTrackableId() {
        return trackableId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimeTwelveHourFormat() {
        return new SimpleDateFormat("hh:mma").format(startTime);
    }

    public long getDistance() {
        return distance;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimeTwelveHourFormat() {
        return new SimpleDateFormat("hh:mma").format(endTime);
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public String getMeetTimeTwelveHourFormat() {
        return new SimpleDateFormat("hh:mma").format(meetTime);
    }
    public double getLongitude(Context context) {
        AbstractTrackable t = Observer.getSingletonInstance(context).getTrackableById(this.getTrackableId());
        return t.getCurrentLongitude(context);
    }

    public double getLatitude(Context context) {
        AbstractTrackable t = Observer.getSingletonInstance(context).getTrackableById(this.getTrackableId());
        return t.getCurrentLatitude(context);
    }

    @Override
    public int compareTo(@NonNull Tracking o) {
        if (this.getMeetTime().getTime() > o.getMeetTime().getTime()) {
            return 1;
        }
        if (this.getMeetTime().getTime() == o.getMeetTime().getTime()) {
            return 0;
        }
        if (this.getMeetTime().getTime() < o.getMeetTime().getTime()) {
            return -1;
        }
        return 0;
    }

    public boolean save(final Activity context) {
        TrackingDatabaseThread tdt = new TrackingDatabaseThread("save", this, context);
        Thread t = new Thread(tdt);
        t.start();
        return tdt.success;
    }

    public boolean destroy(final Activity context) {
        TrackingDatabaseThread tdt = new TrackingDatabaseThread("destroy", this, context);
        Thread t = new Thread(tdt);
        t.start();
        return tdt.success;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getTimeUntilLocation() {
        return timeUntilLocation;
    }

    public void setTimeUntilLocation(long timeUntilLocation) {
        this.timeUntilLocation =  timeUntilLocation;
    }
}
