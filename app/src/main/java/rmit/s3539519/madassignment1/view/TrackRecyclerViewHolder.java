package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.listeners.ListOnClickListener;


// superclass for the two view holders I use
public class TrackRecyclerViewHolder extends RecyclerView.ViewHolder {
    protected Context context;
    protected TextView id;
    private ListOnClickListener listener;

    public TrackRecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        listener = new ListOnClickListener(context, this);
        itemView.setOnClickListener(listener);
        itemView.setOnLongClickListener(listener);
    }

    public TextView getId() {
        return this.id;
    }
}
