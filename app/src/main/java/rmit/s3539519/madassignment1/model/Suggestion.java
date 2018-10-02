package rmit.s3539519.madassignment1.model;

import java.util.Date;

public class Suggestion {
    private static int nextAvailableId = 0;
    private int id;
    private AbstractTrackable suggestedTrackable;
    private Date suggestionTime;
    private long distanceToTrackableInMetres;
    private long timeToTrackableInSeconds;

    public Suggestion(AbstractTrackable trackable, long distanceToTrackableInMetres, long timeToTrackableInSeconds) {
        this.id = nextAvailableId; // prevent id collisions
        nextAvailableId++;
        this.suggestedTrackable = trackable;
        this.distanceToTrackableInMetres = distanceToTrackableInMetres;
        this.timeToTrackableInSeconds = timeToTrackableInSeconds;
        this.suggestionTime = new Date();
    }

    public int getId() { return id; }
    public AbstractTrackable getSuggestedTrackable() {
        return suggestedTrackable;
    }

    public Date getSuggestionTime() {
        return suggestionTime;
    }

    public long getDistanceToTrackableInMetres() {
        return distanceToTrackableInMetres;
    }

    public long getTimeToTrackableInSeconds() {
        return timeToTrackableInSeconds;
    }
}
