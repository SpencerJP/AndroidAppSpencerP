package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;


public class TrackableAdapter extends RecyclerView.Adapter<TrackableViewHolder> implements Filterable {
    private Context context;
    HashMap<Integer, AbstractTrackable> content;
    private HashMap<Integer, AbstractTrackable> filteredContent;
    private CategoryFilter categoryFilter;


    public TrackableAdapter(Context context, Map<Integer, AbstractTrackable> content) {
        this.context = context;
        this.content = new HashMap<Integer, AbstractTrackable>(content);
        this.filteredContent = new HashMap<Integer, AbstractTrackable>(content);
    }

    @Override
    public Filter getFilter() {
        if (categoryFilter == null) {
            categoryFilter = new CategoryFilter(this.content, this);
        }
        return categoryFilter;
    }
    @Override
    public TrackableViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trackable_text_view, parent, false);
        TrackableViewHolder vh = new TrackableViewHolder(context, v);


        return vh;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    @Override
    public void onBindViewHolder(final TrackableViewHolder holder, final int listPosition) {
            if (content.get(listPosition) != null) {
                String name = content.get(listPosition).getName();
                String description = content.get(listPosition).getDescription();
                String website = content.get(listPosition).getUrl();
                String category = content.get(listPosition).getCategory();
                String id = content.get(listPosition).getId();
                holder.getName().setText(name);
                holder.getDescription().setText(description);
                holder.getWebsite().setText(website);
                holder.getCategory().setText(category);
                holder.getId().setText(id);
            }
    }

    public void updateTrackables(Map<Integer, AbstractTrackable> trackables) {
            this.content = new HashMap<Integer, AbstractTrackable>(trackables);
            notifyDataSetChanged();
    }
}
