package rmit.s3539519.madassignment1.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.FoodTruck;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.TrackableViewHolder>  {

    private Map<Integer, FoodTruck> content;

    public static class TrackableViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public TrackableViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView1);
        }
        public TextView getTextView() {
            return this.textView;
        }
    }

    public TrackableAdapter(Map<Integer, FoodTruck> content) {
        content = this.content;
    }


    @Override
    public TrackableAdapter.TrackableViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trackable_text_view, parent, false);
        TrackableViewHolder vh = new TrackableViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    @Override
    public void onBindViewHolder(final TrackableViewHolder holder, final int listPosition) {
        TextView item = holder.getTextView();
        item.setText(content.get(listPosition).toString());
    }
}
