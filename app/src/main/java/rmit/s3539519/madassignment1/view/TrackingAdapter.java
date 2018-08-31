package rmit.s3539519.madassignment1.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.TrackingViewHolder> implements Filterable {

    public class TrackingViewHolder extends RecyclerView.ViewHolder {


        // each data item is just a string in this case
        private TextView name;
        private TextView description;
        private TextView website;
        private TextView category;
        public TrackingViewHolder(View v) {
            super(v);
            name =  v.findViewById(R.id.name);
            description =  v.findViewById(R.id.description);
            website = v.findViewById(R.id.website);
            category = v.findViewById(R.id.category);
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
    @NonNull
    @Override
    public TrackingAdapter.TrackingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingAdapter.TrackingViewHolder trackingViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }



}
