package rmit.s3539519.madassignment1.view.viewmodels;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.SuggestNowButtonListener;
import rmit.s3539519.madassignment1.model.Suggestion;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {
    Map<Integer, Suggestion> content;
    private SuggestNowButtonListener listener;
    protected Context context;
    private Button suggestNow;

    public SuggestionAdapter(AppCompatActivity context, Map<Integer, Suggestion> content) {
        this.context = context;
        updateSuggestions(content);
        suggestNow = context.findViewById(R.id.suggest_now_button);

        listener = new SuggestNowButtonListener(context, this);
        suggestNow.setOnClickListener(listener);
    }
    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_text_view, parent, false);
        SuggestionViewHolder vh = new SuggestionViewHolder(context, v);


        return vh;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    @Override
    public void onBindViewHolder(final SuggestionViewHolder holder, final int listPosition) {
        if (content.get(listPosition) != null) {
            String id = Integer.toString(content.get(listPosition).getId());
            String trackableName = content.get(listPosition).getSuggestedTrackable().getName();
            String distanceTo = Long.toString(content.get(listPosition).getDistanceToTrackableInMetres()) + " metres";
            String date = new SimpleDateFormat("hh:mma").format(content.get(listPosition).getSuggestionTime());
            String timeTo = Long.toString((content.get(listPosition).getTimeToTrackableInSeconds()) / 60) + " minutes to arrival";
            holder.getId().setText(id);
            holder.getTrackableName().setText(trackableName);
            holder.getDate().setText(date);
            holder.getDistance().setText(distanceTo);
            holder.getSuggestionTimeTo().setText(timeTo);

        }
    }

    public void updateSuggestions(Map<Integer, Suggestion> suggestions) {
        this.content = suggestions;
        notifyDataSetChanged();
    }



}
