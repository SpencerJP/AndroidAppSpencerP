package rmit.s3539519.madassignment1.model;

import android.content.Context;

import java.util.Date;

import rmit.s3539519.madassignment1.model.services.TrackingService;

public abstract class AbstractTrackable implements Trackable {
    protected String id;
    protected String name;
    protected String description;
    protected String url;
    protected String category;
    protected String photo;

    public AbstractTrackable(String id, String name, String description, String url, String category, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return this.id + "|" + this.name + "|" + this.description + "|" + this.url + "|" + this.category;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    public double getCurrentLatitude(Context context) {
        return TrackingService.getSingletonInstance(context).getCurrentLatitudeOfTrackable(Integer.parseInt(this.id));
    }

    public double getCurrentLongitude(Context context) {
        return TrackingService.getSingletonInstance(context).getCurrentLongitudeOfTrackable(Integer.parseInt(this.id));
    }

    /*
     * @param duration that it would take to get to the trackable from the user's position
     * @returns a Date object of what time they would get there if they would get there in time, or null if they would not get there in time
     */
    public Date getToTrackableInTimeForAStop(long seconds) {
        return null;
    }
}
