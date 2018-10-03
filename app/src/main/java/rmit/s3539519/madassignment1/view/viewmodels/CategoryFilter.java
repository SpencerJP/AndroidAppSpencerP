package rmit.s3539519.madassignment1.view.viewmodels;

import android.widget.Filter;

import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.model.AbstractTrackable;

public class CategoryFilter extends Filter {

    private final Map<Integer, AbstractTrackable> filterList;
    private TrackableAdapter adapter;


    public CategoryFilter(Map<Integer, AbstractTrackable> filterList, TrackableAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=new HashMap<Integer, AbstractTrackable>(filterList);

    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint != null && constraint.length() > 0 && !constraint.toString().equals("All"))
        {
            //Make it upper so that it is case-insensitive
            constraint=constraint.toString().toUpperCase();
            Map<Integer, AbstractTrackable> filteredTrackables =new HashMap<Integer, AbstractTrackable>();
            int i = 0;
            for(Map.Entry<Integer, AbstractTrackable> trackable : filterList.entrySet()) {
                // sort by category
                if(trackable.getValue().getCategory().toUpperCase().equals(constraint.toString()))
                {
                    filteredTrackables.put(i, trackable.getValue());
                    i++;
                }
            }
            for(Map.Entry<Integer, AbstractTrackable> trackable : filteredTrackables.entrySet()) {
            }
            results.count=filteredTrackables.size();
            results.values=filteredTrackables;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        // change the dataset to the filtered one
        Map<Integer, AbstractTrackable> test = new HashMap<Integer, AbstractTrackable>((HashMap<Integer, AbstractTrackable>) results.values);
        adapter.content = (HashMap<Integer, AbstractTrackable>) results.values;
        adapter.notifyDataSetChanged();
    }
}
