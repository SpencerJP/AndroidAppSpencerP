package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;

public class TrackableViewHolder extends TrackRecyclerViewHolder {
    private TextView name;
    private TextView description;
    private TextView website;
    private TextView category;

    public TrackableViewHolder(Context context, View v) {
        super(v, context);
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
}