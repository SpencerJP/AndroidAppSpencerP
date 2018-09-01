package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTracking;
import rmit.s3539519.madassignment1.view.listeners.ListOnClickListener;

public class TrackingViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView id;
    private TextView title;
    private TextView meetTime;
    private TextView startTime;
    private TextView endTime;
    private TextView coordinates;
    public TrackingViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        v.setOnClickListener(new ListOnClickListener(context, this));
        id = v.findViewById(R.id.tracking_id);
        title =  v.findViewById(R.id.title);
        meetTime =  v.findViewById(R.id.meetingTime);
        startTime = v.findViewById(R.id.startTime);
        endTime = v.findViewById(R.id.endTime);
        coordinates = v.findViewById(R.id.coordinates);
    }

    public TextView getId() {
        return title;
    }
    public TextView getTitle() {
        return title;
    }

    public TextView getMeetTime() {
        return meetTime;
    }

    public TextView getStartTime() {
        return startTime;
    }

    public TextView getEndTime() {
        return endTime;
    }

    public TextView getCoordinates() {
        return coordinates;
    }
}