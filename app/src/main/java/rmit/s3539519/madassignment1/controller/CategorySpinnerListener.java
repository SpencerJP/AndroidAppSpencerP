package rmit.s3539519.madassignment1.controller;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;

import rmit.s3539519.madassignment1.view.viewmodels.GeoTrackerSpinnerAdapter;
import rmit.s3539519.madassignment1.view.viewmodels.TrackableAdapter;

public class CategorySpinnerListener implements AdapterView.OnItemSelectedListener {

    GeoTrackerSpinnerAdapter categorySpinnerAdapter;
    TrackableAdapter trackableAdapter;
    Context context;
    public CategorySpinnerListener(Context context, GeoTrackerSpinnerAdapter categorySpinnerAdapter, TrackableAdapter trackableAdapter) {
        this.context = context;
        this.categorySpinnerAdapter = categorySpinnerAdapter;
        this.trackableAdapter = trackableAdapter;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = categorySpinnerAdapter.getItem(position);

        trackableAdapter.getFilter().filter(category,new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
