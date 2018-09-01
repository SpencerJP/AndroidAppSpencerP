package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.Tracking;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingViewHolder> {
    private Context context;
    HashMap<Integer, Tracking> content;

    public TrackingAdapter(Context context, Map<Integer, Tracking> content) {
        this.context = context;
        this.content = new HashMap<Integer, Tracking>(content);
    }
    @Override
    public TrackingViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracking_text_view, parent, false);
        TrackingViewHolder vh = new TrackingViewHolder(context, v);


        return vh;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    @Override
    public void onBindViewHolder(final TrackingViewHolder holder, final int listPosition) {
        if (content.get(listPosition) != null) {
            String id = content.get(listPosition).getTrackingId();
            String title = content.get(listPosition).getTitle();
            String meetTime = new SimpleDateFormat("hh:mma").format(content.get(listPosition).getMeetTime());
            String startTime = new SimpleDateFormat("hh:mma").format(content.get(listPosition).getStartTime());
            String endTime = new SimpleDateFormat("hh:mma").format(content.get(listPosition).getEndTime());
            holder.getId().setText(id);
            holder.getTitle().setText(title);
            holder.getMeetTime().setText(meetTime);
            holder.getStartTime().setText(startTime);
            holder.getEndTime().setText(endTime);
        }
    }

}
