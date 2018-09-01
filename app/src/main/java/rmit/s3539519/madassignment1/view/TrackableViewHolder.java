package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTracking;
import rmit.s3539519.madassignment1.view.listeners.ListOnClickListener;

public class TrackableViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView name;
    private TextView description;
    private TextView website;
    private TextView category;
    private TextView id;
    public TrackableViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        v.setOnClickListener(new ListOnClickListener(context, this));
        name =  v.findViewById(R.id.name);
        description =  v.findViewById(R.id.description);
        website = v.findViewById(R.id.website);
        category = v.findViewById(R.id.category);
        id = v.findViewById(R.id.trackable_id);
    }

    public TextView getName() {
        return this.name;
    }
    public TextView getDescription() {
        return this.description;
    }
    public TextView getWebsite() {
        return this.website;
    }
    public TextView getCategory() {
        return this.category;
    }
    public TextView getId() {
        return this.id;
    }
}