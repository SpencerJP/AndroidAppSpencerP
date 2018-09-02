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

    public Tracking(int trackableId, String title, Date startTime, Date endTime, Date meetTime, List<TrackingInfo> trackingInfos) throws TrackingNotValidException {
        trackingId = Integer.toString(trackableId);
        this.trackableId = trackableId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetTime = meetTime;

        // Throws TrackingNotValidException if not valid
        this.validate(trackingInfos);
    }

    public void validate(List<TrackingInfo> trackingInfos) throws TrackingNotValidException {
        boolean meetingTimeCorrect = false;
        boolean startTimeCorrect = false;
        boolean endTimeCorrect = false;

        Iterator<TrackingInfo> iter = trackingInfos.iterator();
        while(iter.hasNext()) {
            TrackingInfo current = iter.next();
            if(current.trackableId != getTrackableId()) {
                iter.remove();
            }
        }

        // find the meeting time, check that it is stopped.
        iter = trackingInfos.iterator();
        while(iter.hasNext()) {
            TrackingInfo current = iter.next();
            if (current.date.getTime() == getMeetTime().getTime()) {
                if(current.stopTime > 0) {
                    this.latitude = current.latitude;
                    this.longitude = current.latitude;
                    meetingTimeCorrect = true;
                    break;
                }
            }
        }

        iter = trackingInfos.iterator();
        while(iter.hasNext()) {
            TrackingInfo current = iter.next();
            if (current.date.getTime() == getStartTime().getTime()) {
                startTimeCorrect = true;
            }
            if (current.date.getTime() == getEndTime().getTime()) {
                endTimeCorrect = true;
            }
        }
        if (!startTimeCorrect) {
            throw new TrackingNotValidException("Start time isn't a registered time.");
        }
        if (!endTimeCorrect) {
            throw new TrackingNotValidException("End time isn't a registered time.");
        }
        if (!meetingTimeCorrect) {
            throw new TrackingNotValidException("Meeting time isn't a registered time.");
        }
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
