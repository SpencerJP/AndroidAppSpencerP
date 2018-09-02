package rmit.s3539519.madassignment1.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TrackingInfo
{
    // these are left public on purpose to preserve the code in TrackingService, I didn't want to edit it
    public Date date;
    public int trackableId;
    public int stopTime;
    public double latitude;
    public double longitude;

    @Override
    public String toString()
    {
        return String.format(Locale.getDefault(), "Date/Time=%s, trackableId=%d, stopTime=%d, lat=%.5f, long=%.5f", DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.MEDIUM).format(date), trackableId, stopTime, latitude, longitude);
    }
}