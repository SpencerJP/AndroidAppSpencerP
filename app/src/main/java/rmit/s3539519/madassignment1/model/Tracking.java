package rmit.s3539519.madassignment1.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Tracking implements Comparable<Tracking> {
    private String trackingId;
    private int trackableId;
    private String title;
    private Date startTime;
    private Date endTime;
    private Date meetTime;
    private double longitude;
    private double latitude;

    public Tracking(int trackableId, String title, Date startTime, Date endTime, Date meetTime) {
        trackingId = Integer.toString(trackableId);
        this.trackableId = trackableId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetTime = meetTime;
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
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
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
}
