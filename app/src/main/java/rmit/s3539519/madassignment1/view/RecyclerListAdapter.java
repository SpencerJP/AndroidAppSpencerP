package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

abstract class RecyclerListAdapter extends RecyclerView.Adapter<TrackingViewHolder> {
    protected Context context;

    RecyclerListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public abstract TrackingViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType);

    @Override
    public abstract int getItemCount();

    @Override
    public abstract void onBindViewHolder(TrackingViewHolder holder, int listPosition);
}
