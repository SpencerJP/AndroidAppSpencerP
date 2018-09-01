package rmit.s3539519.madassignment1.view.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTracking;
import rmit.s3539519.madassignment1.view.TrackableViewHolder;
import rmit.s3539519.madassignment1.view.TrackingViewHolder;

public class ListOnClickListener implements View.OnClickListener {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private Context context;
    private TrackableViewHolder trackableViewHolder;
    private TrackingViewHolder trackingViewHolder;
    public ListOnClickListener(Context context, TrackableViewHolder trackableViewHolder) {
        this.context = context;
        this.trackableViewHolder = trackableViewHolder;
    }

    public ListOnClickListener(Context context, TrackingViewHolder trackingViewHolder) {
        this.context = context;
        this.trackingViewHolder = trackingViewHolder;
    }
    @Override
    public void onClick(View v) {
            Intent addTracking = new Intent(context, EditTracking.class);
            if (trackableViewHolder != null) {
                addTracking.putExtra(EXTRA_TRACKABLE_ID, trackableViewHolder.getId().getText());
            } else {

            }
            context.startActivity(addTracking);
    }
}
