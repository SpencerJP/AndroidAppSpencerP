package rmit.s3539519.madassignment1.model;

import android.util.Pair;

import java.util.Date;

public class Tracking {
    private String trackingId;
    private int trackableId;
    private String Title;
    private Date startTime;
    private Date endTime;
    private Date meetTime;
    private Pair<Double, Double> meetLocation;

    public Pair<Double, Double> getCurrentLocation() {
        return null;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public int getTrackableId() {
        return trackableId;
    }

    public void setTrackableId(int trackableId) {
        this.trackableId = trackableId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }

    public Pair<Double, Double> getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(Pair<Double, Double> meetLocation) {
        this.meetLocation = meetLocation;
    }

    public boolean isValid() {
        return false;
    }
}
