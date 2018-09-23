package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.FoodTruck;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.Tracking;

public class TrackingAdapter extends RecyclerListAdapter {
    HashMap<Integer, Tracking> content;

    public TrackingAdapter(Context context, Map<Integer, Tracking> content) {
        super(context);
        updateTrackings(content);
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
            holder.getTitle().setText(String.format("Title: %s, (%s)", title, Observer.getSingletonInstance(context).getTrackableById(Integer.parseInt(id)).getName()   ));
            holder.getMeetTime().setText("Meet time: " + meetTime);
            holder.getBetweenTimes().setText(String.format("Between: %s - %s", startTime, endTime));
            if(content.get(listPosition).hasLocation()) {
                holder.getCurrentLocation().setText(String.format("Location: %.6f, %.6f", Observer.getSingletonInstance(context).getTrackableById(Integer.parseInt(id)).getCurrentLatitude(), Observer.getSingletonInstance(context).getTrackableById(Integer.parseInt(id)).getCurrentLongitude()));
            }
        }
    }

    public void updateTrackings(Map<Integer, Tracking> trackings) {
        HashMap<Integer, Tracking> newContent = new HashMap<Integer, Tracking>();
        int i = 0;
        ArrayList<Tracking> listToSort = new ArrayList<Tracking>();
        for(Map.Entry<Integer, Tracking> entry : trackings.entrySet())   {
            listToSort.add(entry.getValue());
        }
        // Tracking implements Comparable, compareTo compares meeting times
        Collections.sort(listToSort);

        for(Tracking t : listToSort){
            newContent.put(i, t);
            i++;
        }
        this.content = newContent;

        notifyDataSetChanged();
    }



}
