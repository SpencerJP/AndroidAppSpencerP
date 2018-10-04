package rmit.s3539519.madassignment1.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.EditTrackingActivity;
import rmit.s3539519.madassignment1.view.MapsActivity;
import rmit.s3539519.madassignment1.view.SuggestionListActivity;
import rmit.s3539519.madassignment1.view.TrackableListActivity;
import rmit.s3539519.madassignment1.view.TrackingListActivity;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.services.TrackingService;
import rmit.s3539519.madassignment1.view.viewmodels.TrackRecyclerViewHolder;

public class ListOnClickListener implements View.OnClickListener, View.OnLongClickListener {
    private static final String EXTRA_TRACKABLE_ID = "s3539519_PassTrackingID";
    private static final String EXTRA_EDIT_BOOL = "s3539519_EditMode";
    private static final String EXTRA_SCHEDULE_INFORMATION = "s3539519_ScheduleInformation";

    private Context context;
    private TrackRecyclerViewHolder viewHolder;
    public ListOnClickListener(Context context, TrackRecyclerViewHolder viewHolder) {
        this.context = context;
        this.viewHolder = viewHolder;
    }
    @Override
    public void onClick(View v) {
        // instanceof to check which activity we are currently on
        if( context instanceof TrackableListActivity) {
            Intent addTracking = new Intent(context, EditTrackingActivity.class);
            if (viewHolder != null) {
                addTracking.putExtra(EXTRA_TRACKABLE_ID, viewHolder.getId().getText());

                context.startActivity(addTracking);
            }
        }
        if( context instanceof TrackingListActivity) {
            Intent addTracking = new Intent(context, EditTrackingActivity.class);
            if (viewHolder != null) {
                addTracking.putExtra(EXTRA_TRACKABLE_ID, viewHolder.getId().getText());
                // tell the intent we're editing this one
                addTracking.putExtra(EXTRA_EDIT_BOOL, true);
                context.startActivity(addTracking);
            }
        }
        if( context instanceof SuggestionListActivity) {
            Intent addTracking = new Intent(context, EditTrackingActivity.class);
            if (viewHolder != null) {
                String suggestionId = (String) viewHolder.getId().getText();
                addTracking.putExtra(EXTRA_TRACKABLE_ID, Observer.getSingletonInstance(context).getSuggestionById(Integer.parseInt(suggestionId)).getSuggestedTrackable().getId());

                context.startActivity(addTracking);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if( context instanceof TrackingListActivity) {
            Intent addTracking = new Intent(context, EditTrackingActivity.class);
            if (viewHolder != null) {
                final int trackingId = Integer.parseInt(viewHolder.getId().getText().toString());
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.delete_tracking);
                alert.setMessage(R.string.are_you_sure_delete);
                alert.setPositiveButton(android.R.string.yes, new DialogBoxListener(context, trackingId, "positive"));
                alert.setNegativeButton(android.R.string.no, new DialogBoxListener(context, trackingId, "negative"));
                alert.show();
            }
        }

        if( context instanceof TrackableListActivity) {
            if (viewHolder != null) {
                final int trackableId = Integer.parseInt(viewHolder.getId().getText().toString());
                List<TrackingInfo> trackingInfos  = new ArrayList<TrackingInfo>(TrackingService.getSingletonInstance(context).getTrackingInfo());
                Iterator<TrackingInfo> iter = trackingInfos.iterator();
                while(iter.hasNext()) { // have to do this stuff here to avoid editing TrackingService
                    TrackingInfo next = iter.next();
                    if(next.trackableId != trackableId) {
                        iter.remove();
                    }
                }
                if (trackingInfos.size() != 0) {
                    String s  = "";
                    for(TrackingInfo t : trackingInfos) {
                        s = s + String.format(Locale.getDefault(), "Date/Time=%s, stopTime=%d, lat=%.5f, long=%.5f\n\n", DateFormat.getDateTimeInstance(
                                DateFormat.SHORT, DateFormat.MEDIUM).format(t.date), t.stopTime, t.latitude, t.longitude);
                    }

                    s = Observer.getSingletonInstance(context).getTrackableById(trackableId).getName() + " Route Information\n\n" + s;
                    Intent viewSchedule = new Intent(context, MapsActivity.class);
                    viewSchedule.putExtra(EXTRA_SCHEDULE_INFORMATION, trackableId);
                    context.startActivity(viewSchedule);
                }
                else {
                    Toast.makeText(context, R.string.no_schedule,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if( context instanceof SuggestionListActivity) {
                if (viewHolder != null) {
                    final int suggestionId = Integer.parseInt(viewHolder.getId().getText().toString());
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(R.string.delete_suggestion);
                    alert.setMessage(R.string.are_you_sure_delete);
                    alert.setPositiveButton(android.R.string.yes, new DialogBoxListener(context, suggestionId, "positive_suggestion_delete"));
                    alert.setNegativeButton(android.R.string.no, new DialogBoxListener(context, suggestionId, "negative_suggestion_delete"));
                    alert.show();
                }
        }
        return true;
    }
}
