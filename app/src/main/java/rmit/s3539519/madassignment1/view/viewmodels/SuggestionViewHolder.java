package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.ListOnClickListener;

class SuggestionViewHolder extends TrackRecyclerViewHolder {
    private TextView suggestionTimeTo;
    private TextView trackableName;
    private TextView date;
    private TextView distance;
    private ListOnClickListener listener;

    public SuggestionViewHolder(Context context, @NonNull View itemView) {
        super(itemView, context);
        trackableName =  itemView.findViewById(R.id.suggestion_trackable_name);
        suggestionTimeTo =  itemView.findViewById(R.id.suggestion_time_to);
        date = itemView.findViewById(R.id.suggestion_date);
        distance = itemView.findViewById(R.id.suggestion_distance);
        id = itemView.findViewById(R.id.suggestion_id);
        listener = new ListOnClickListener(context, this);
        itemView.setOnClickListener(listener);
        itemView.setOnLongClickListener(listener);
    }

    public TextView getSuggestionTimeTo() {
        return suggestionTimeTo;
    }

    public TextView getTrackableName() {
        return trackableName;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getDistance() {
        return distance;
    }
}
