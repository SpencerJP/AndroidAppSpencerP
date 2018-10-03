package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;

public class TrackingViewHolder extends TrackRecyclerViewHolder {

    private TextView title;
    private TextView meetTime;
    private TextView betweenTimes;
    private TextView currentLocation;
    public TrackingViewHolder(Context context, View v) {
        super(v, context);
        title =  v.findViewById(R.id.title);
        meetTime =  v.findViewById(R.id.meetingTime);
        betweenTimes = v.findViewById(R.id.betweenTimes);
        currentLocation = v.findViewById(R.id.currentLocation);
        id = v.findViewById(R.id.tracking_id);
    }

    public TextView getId() {
        return id;
    }
    public TextView getTitle() {
        return title;
    }

    public TextView getMeetTime() {
        return meetTime;
    }

    public TextView getBetweenTimes() {
        return betweenTimes;
    }

    public TextView getCurrentLocation() {
        return currentLocation;
    }
}